package ua.privat.regulatoryservice.exceptions;

public class ServiceErrorException extends RuntimeException {
    public ServiceErrorException(String message) {
        super(message);
    }
}
