package ua.privat.regulatoryservice.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ua.privat.PaymentApiUtil;
import ua.privat.PaymentApiUtilI;

@Configuration
public class PaymentApiUtilConfig {

    @Bean("business")
    public PaymentApiUtilI getPaymentApiUtilBusiness(@Qualifier("webClientBusiness") WebClient webClient) {
        return new PaymentApiUtil(webClient);
    }

    @Bean("client")
    public PaymentApiUtilI getPaymentApiUtilClient(@Qualifier("webClientData") WebClient webClient) {
        return new PaymentApiUtil(webClient);
    }
}
