package ua.privat.regulatoryservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ua.privat.regulatoryservice.dto.RegularPaymentDTO;
import ua.privat.regulatoryservice.exceptions.ServiceErrorException;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class RegularPaymentService {

    private final WebClient webClientBusiness;

    public List<RegularPaymentDTO> checkPaymentsForTheNeedToWriteOff() {
        Iterable<RegularPaymentDTO> regularPaymentDTOS = webClientBusiness.get()
                .uri("/write-off-payment")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        error -> Mono.error(new ServiceErrorException("The service returned an error with status code 4xx. More details: status code " + error.statusCode().value() + " error URL " + error.request().getURI())))
                .bodyToFlux(RegularPaymentDTO.class)
                .toIterable();

        return StreamSupport.stream(regularPaymentDTOS.spliterator(), false)
                .toList();
    }
}
