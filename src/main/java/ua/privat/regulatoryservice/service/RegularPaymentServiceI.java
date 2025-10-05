package ua.privat.regulatoryservice.service;

import ua.privat.clientlib.http.response.data.RegularPaymentData;

import java.util.List;

/**
 * Интерфейс сервиса для работы с регулярными платежами
 */
public interface RegularPaymentServiceI {
    /**
     * Получить платежи, которые нужно списать
     *
     * @return List<RegularPaymentData> платежи которые нужно списать
     */
    List<RegularPaymentData> getPaymentNeedToWriteOff();
    /**
     * Обновить дату списания
     *
     * @param regularPaymentId ID инструкции платежа
     * @return RegularPaymentData обновлённая инструкция платежа
     */
    RegularPaymentData updateWriteOffDate(Long regularPaymentId);
}
