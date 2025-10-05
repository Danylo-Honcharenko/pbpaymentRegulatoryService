package ua.privat.regulatoryservice.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.privat.clientlib.http.RegularPaymentInstructionsBusinessClient;
import ua.privat.clientlib.http.response.data.RegularPaymentData;
import ua.privat.regulatoryservice.service.RegularPaymentServiceI;

import java.util.List;

/**
 * Сервис по работе с регулярными платежами
 */
@Service
@AllArgsConstructor
public class RegularPaymentService implements RegularPaymentServiceI {
    // Клиент для взаимодействия с API
    private final RegularPaymentInstructionsBusinessClient regularPaymentInstructionsBusinessClient;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RegularPaymentData> getPaymentNeedToWriteOff() {
        return this.regularPaymentInstructionsBusinessClient.getPaymentNeedToWriteOff().getData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RegularPaymentData updateWriteOffDate(Long regularPaymentId) {
        return regularPaymentInstructionsBusinessClient.updateWriteOffDate(regularPaymentId).getData();
    }
}
