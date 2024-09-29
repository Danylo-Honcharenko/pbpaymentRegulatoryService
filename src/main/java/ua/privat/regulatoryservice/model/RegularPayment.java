package ua.privat.regulatoryservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegularPayment {
    private Integer id;
    private String payerFullName;
    private Long iin;
    private Long cardNumber;
    private String recipientsSettlementAccount;
    private Integer mfoRecipient;
    private Integer okpoRecipient;
    private String recipientName;
    private String writeOffPeriod;
    private Integer paymentAmount;
}
