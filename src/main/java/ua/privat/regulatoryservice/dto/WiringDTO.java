package ua.privat.regulatoryservice.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class WiringDTO {
    private Integer id;
    private String wiringTime;
    private Long paymentInstructionsId;
    private Integer paymentAmount;
    private String status;
}
