package ua.privat.regulatoryservice;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import ua.privat.clientlib.http.RegularPaymentInstructionsBusinessClient;
import ua.privat.clientlib.http.response.data.RegularPaymentData;
import ua.privat.regulatoryservice.service.RegularPaymentServiceI;
import ua.privat.regulatoryservice.service.impl.RegularPaymentService;

import java.util.List;

public class RegularPaymentTest {

    @Test
    @Disabled
    public void fetchDataTest() {
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8081/api")
                .build();
        RegularPaymentInstructionsBusinessClient regularPaymentInstructionsBusinessClient = new RegularPaymentInstructionsBusinessClient(webClient);
        RegularPaymentServiceI regularPaymentService = new RegularPaymentService(regularPaymentInstructionsBusinessClient);
        RegularPaymentData regularPaymentData = regularPaymentService.updateWriteOffDate(5L);
        System.out.println(regularPaymentData.getId());
    }
}
