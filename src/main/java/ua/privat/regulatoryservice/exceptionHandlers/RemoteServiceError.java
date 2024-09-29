package ua.privat.regulatoryservice.exceptionHandlers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import ua.privat.regulatoryservice.exceptions.ServiceErrorException;

@ControllerAdvice
public class RemoteServiceError {
    @ExceptionHandler(ServiceErrorException.class)
    public ResponseEntity<String> regularPaymentDataServiceReturnError(ServiceErrorException serviceErrorException) {
        return ResponseEntity.badRequest().body(serviceErrorException.getMessage());
    }

    @ExceptionHandler(WebClientRequestException.class)
    public ResponseEntity<String> webClientError(WebClientRequestException webClientRequestException) {
        return ResponseEntity.badRequest().body(webClientRequestException.getMessage());
    }
}
