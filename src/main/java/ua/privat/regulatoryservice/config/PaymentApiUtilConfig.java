package ua.privat.regulatoryservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ua.privat.clientlib.http.RegularPaymentInstructionsBusinessClient;
import ua.privat.clientlib.http.WiringBusinessClient;

/**
 * Конфигурация клиентов для отправки запросов
 */
@Configuration
public class PaymentApiUtilConfig {
    // URL
    private final String url = "http://localhost:8081/api";

    /**
     * Получить конфигурацию WebClient
     *
     * @return WebClient конфигурация
     */
    private WebClient getWebClientConfig() {
        return WebClient.builder()
                .baseUrl(this.url)
                .build();
    }

    /**
     * Получить клиент для отправки запросов
     *
     * @return RegularPaymentInstructionsDataClient клиент для отправки запросов
     */
    @Bean
    public RegularPaymentInstructionsBusinessClient getRegularPaymentInstructionsClient() {
        return new RegularPaymentInstructionsBusinessClient(this.getWebClientConfig());
    }

    /**
     * Получить клиент для отправки запросов
     *
     * @return RegularPaymentInstructionsDataClient клиент для отправки запросов
     */
    @Bean
    public WiringBusinessClient getWiringClient() {
        return new WiringBusinessClient(this.getWebClientConfig());
    }
}
