package ua.privat.regulatoryservice.service;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ua.privat.regulatoryservice.dto.RegularPaymentDTO;
import ua.privat.regulatoryservice.dto.WiringDTO;
import ua.privat.regulatoryservice.exceptions.ServiceErrorException;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WiringService {

    private final WebClient webClientBusiness;
    private final WebClient webClientData;

    public void createWiring(List<RegularPaymentDTO> regularPayment) {
        regularPayment.forEach(regPayment -> {
            WiringDTO wiringDTO = WiringDTO.builder()
                    .wiringTime(regPayment.getWriteOffDate())
                    .paymentInstructionsId(Long.valueOf(regPayment.getId()))
                    .paymentAmount(regPayment.getPaymentAmount())
                    .status("A")
                    .build();

            ResponseEntity<WiringDTO> wiringDTOResponseEntity = webClientBusiness.post()
                    .uri("/create-wiring")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(Mono.just(wiringDTO), WiringDTO.class)
                    .retrieve()
                    .onStatus(HttpStatusCode::is5xxServerError,
                            error -> Mono.error(new ServiceErrorException("The service returned an error with status code 5xx. More details: status code " + error.statusCode().value() + " error URL " + error.request().getURI())))
                    .onStatus(HttpStatusCode::is4xxClientError,
                            error -> Mono.error(new ServiceErrorException("The service returned an error with status code 4xx. More details: status code " + error.statusCode().value() + " error URL " + error.request().getURI())))
                    .toEntity(WiringDTO.class)
                    .block();

            if (wiringDTOResponseEntity != null) {
                webClientData.patch()
                        .uri("/update-write-off-date/{id}", Objects.requireNonNull(wiringDTOResponseEntity.getBody()).getPaymentInstructionsId())
                        .retrieve()
                        .onStatus(HttpStatusCode::is4xxClientError,
                                error -> Mono.error(new ServiceErrorException("The service returned an error with status code 4xx. More details: status code " + error.statusCode().value() + " error URL " + error.request().getURI())))
                        .toEntity(WiringDTO.class)
                        .block();
            }
        });
    }
}
