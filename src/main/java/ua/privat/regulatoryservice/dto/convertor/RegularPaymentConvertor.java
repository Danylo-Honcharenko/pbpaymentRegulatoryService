package ua.privat.regulatoryservice.dto.convertor;

import org.springframework.stereotype.Component;
import ua.privat.regulatoryservice.dto.RegularPaymentDTO;
import ua.privat.regulatoryservice.model.RegularPayment;

@Component
public class RegularPaymentConvertor implements Converter<RegularPayment, RegularPaymentDTO> {

    @Override
    public RegularPayment convertToModel(RegularPaymentDTO regularPaymentDTO) {
        return RegularPayment.builder()
                .id(regularPaymentDTO.getId())
                .payerFullName(regularPaymentDTO.getPayerFullName())
                .iin(regularPaymentDTO.getIin())
                .cardNumber(regularPaymentDTO.getCardNumber())
                .recipientsSettlementAccount(regularPaymentDTO.getRecipientsSettlementAccount())
                .mfoRecipient(regularPaymentDTO.getMfoRecipient())
                .okpoRecipient(regularPaymentDTO.getOkpoRecipient())
                .recipientName(regularPaymentDTO.getRecipientName())
                .writeOffPeriod(regularPaymentDTO.getWriteOffPeriod())
                .paymentAmount(regularPaymentDTO.getPaymentAmount())
                .build();
    }

    @Override
    public RegularPaymentDTO convertToDTO(RegularPayment regularPayment) {
        return RegularPaymentDTO.builder()
                .id(regularPayment.getId())
                .payerFullName(regularPayment.getPayerFullName())
                .iin(regularPayment.getIin())
                .cardNumber(regularPayment.getCardNumber())
                .recipientsSettlementAccount(regularPayment.getRecipientsSettlementAccount())
                .mfoRecipient(regularPayment.getMfoRecipient())
                .okpoRecipient(regularPayment.getOkpoRecipient())
                .recipientName(regularPayment.getRecipientName())
                .writeOffPeriod(regularPayment.getWriteOffPeriod())
                .paymentAmount(regularPayment.getPaymentAmount())
                .build();
    }
}
