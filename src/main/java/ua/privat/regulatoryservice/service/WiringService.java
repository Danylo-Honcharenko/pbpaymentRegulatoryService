package ua.privat.regulatoryservice.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ua.privat.PaymentApiUtilI;
import ua.privat.regulatoryservice.exceptions.ServiceErrorException;
import ua.privat.utils.dto.RegularPaymentDTO;
import ua.privat.utils.dto.WiringDTO;

import java.util.List;
import java.util.Objects;

@Service
public class WiringService {

    private final PaymentApiUtilI apiUtilBusiness;
    private final PaymentApiUtilI apiUtilClient;

    public WiringService(@Qualifier("business") PaymentApiUtilI apiUtilBusiness,
                         @Qualifier("client") PaymentApiUtilI apiUtilClient) {
        Assert.notNull(apiUtilBusiness,
                "apiUtil must not be null");
        this.apiUtilBusiness = apiUtilBusiness;
        this.apiUtilClient = apiUtilClient;
    }

    public void createWiring(List<RegularPaymentDTO> regularPayment) {
        regularPayment.forEach(regPayment -> {
            WiringDTO wiringDTO = WiringDTO.builder()
                    .wiringTime(regPayment.getWriteOffDate())
                    .paymentInstructionsId(Long.valueOf(regPayment.getId()))
                    .paymentAmount(regPayment.getPaymentAmount())
                    .status("A")
                    .build();

            ResponseEntity<WiringDTO> wiringDTOResponseEntity = apiUtilBusiness.doPost("/create-wiring", WiringDTO.class, wiringDTO,
                    (mono) -> mono);

            if (wiringDTOResponseEntity != null) {
                apiUtilClient.doPatch(
                        uriBuilder -> uriBuilder.path("/update-write-off-date/{id}").queryParam("id", Objects.requireNonNull(wiringDTOResponseEntity.getBody()).getPaymentInstructionsId()).build(),
                        WiringDTO.class, (mono) -> mono);
            }
        });
    }
}
