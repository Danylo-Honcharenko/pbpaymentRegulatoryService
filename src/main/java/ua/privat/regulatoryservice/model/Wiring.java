package ua.privat.regulatoryservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Wiring {
    private Integer id;
    private String wiringTime;
    private Long paymentInstructionsId;
    private Integer paymentAmount;
    private String status;
}
