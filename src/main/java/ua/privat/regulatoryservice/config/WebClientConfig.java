package ua.privat.regulatoryservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClientBusiness() {
        return WebClient.builder()
                .baseUrl("http://localhost:8081/api")
                .build();
    }

    @Bean
    public WebClient webClientData() {
        return WebClient.builder()
                .baseUrl("http://localhost:8080/api")
                .build();
    }
}

