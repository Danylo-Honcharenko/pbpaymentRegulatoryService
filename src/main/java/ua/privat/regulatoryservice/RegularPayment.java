package ua.privat.regulatoryservice;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import ua.privat.clientlib.http.request.WiringRequest;
import ua.privat.clientlib.http.response.data.RegularPaymentData;
import ua.privat.clientlib.http.response.data.WiringData;
import ua.privat.regulatoryservice.service.RegularPaymentServiceI;
import ua.privat.regulatoryservice.service.WiringServiceI;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.logging.Logger;

/**
 * Регулярные платежи
 */
@Component
@RequiredArgsConstructor
public class RegularPayment {
    // Сервис регулярных платежей
    private final RegularPaymentServiceI regularPaymentService;
    // Сервис проводок
    private final WiringServiceI wiringService;

    private final static Logger LOGGER = Logger.getLogger(RegularPayment.class.getName());

    /**
     * Выполнить событие
     */
    @Scheduled(fixedDelay = 120000)
    public void doEvent() {
        try {
            // Список задач для выполнения
            List<Callable<Map<String, Object>>> tasks = this.regularPaymentService.getPaymentNeedToWriteOff().stream()
                    .map(this::getTask)
                    .toList();
            LOGGER.info(String.format("Found %s payments need to writeoff", tasks.size()));
            // Проверяем наличие задач для выполнения
            if (!CollectionUtils.isEmpty(tasks)) {
                try (ExecutorService executor = Executors.newFixedThreadPool(tasks.size())) {
                    CompletionService<Map<String, Object>> completionService = new ExecutorCompletionService<>(executor);
                    tasks.forEach(completionService::submit);
                    for (int i = 0; i < tasks.size(); i++) {
                        Map<String, Object> taskResult = completionService.take().get();
                        WiringData wiring = (WiringData) taskResult.get("wiring");
                        LOGGER.info(String.format("Wiring %s created", wiring.getId()));
                        RegularPaymentData regularPaymentData = (RegularPaymentData) taskResult.get("regularPayment");
                        LOGGER.info(String.format("Regular payment %s writeoff date updated", regularPaymentData.getId()));
                    }
                }
                LOGGER.info("All payments writeoff completed!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.info(ex.getMessage());
        }
    }

    /**
     * Получить задачу для выполнения
     *
     * @param regularPaymentData инструкция регулярного платежа
     * @return Runnable задача для выполнения
     */
    private Callable<Map<String, Object>> getTask(RegularPaymentData regularPaymentData) {
        return () -> this.doTask(regularPaymentData);
    }

    /**
     * Выполнить задачу
     *
     * @param regularPaymentData инструкция регулярного платежа
     */
    private Map<String, Object> doTask(RegularPaymentData regularPaymentData) {
        try {
            WiringData wiringResponse = this.wiringService.createWiring(this.getPreparedWiringRequest(regularPaymentData));
            RegularPaymentData updatedRegularPaymentData = this.regularPaymentService.updateWriteOffDate(regularPaymentData.getId());
            return Map.of("wiring", wiringResponse, "regularPayment", updatedRegularPaymentData);
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            ex.printStackTrace();
        }
        return Map.of();
    }

    /**
     * Запрос на создание проводки по регулярному платежу
     *
     * @param regularPaymentData данные регулярного платежа
     * @return WiringRequest подготовленный запрос
     */
    private WiringRequest getPreparedWiringRequest(RegularPaymentData regularPaymentData) {
        return WiringRequest.builder()
                .wiringTime(regularPaymentData.getWriteoffdate())
                .paymentInstructionsId(regularPaymentData.getId())
                .paymentAmount(regularPaymentData.getPaymentAmount())
                .build();
    }
}
