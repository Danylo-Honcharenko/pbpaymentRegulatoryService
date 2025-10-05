package ua.privat.regulatoryservice.service;

import ua.privat.clientlib.http.request.WiringRequest;
import ua.privat.clientlib.http.response.data.WiringData;

/**
 * Интерфейс сервиса для работы с проводками
 */
public interface WiringServiceI {
    /**
     * Создать проводку
     *
     * @param wiringRequest запрос на создание проводки
     * @return WiringData данные созданной проводки
     */
    WiringData createWiring(WiringRequest wiringRequest);
}
