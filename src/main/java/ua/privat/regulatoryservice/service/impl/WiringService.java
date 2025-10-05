package ua.privat.regulatoryservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.privat.clientlib.http.WiringBusinessClient;
import ua.privat.clientlib.http.request.WiringRequest;
import ua.privat.clientlib.http.response.data.WiringData;
import ua.privat.regulatoryservice.service.WiringServiceI;

/**
 * Сервис по работе с проводками
 */
@Service
@RequiredArgsConstructor
public class WiringService implements WiringServiceI {
    // Клиент для взаимодействия с API
    private final WiringBusinessClient wiringBusinessClient;

    /**
     * {@inheritDoc}
     */
    @Override
    public WiringData createWiring(WiringRequest wiringRequest) {
        return this.wiringBusinessClient.createWiring(wiringRequest).getData();
    }
}
