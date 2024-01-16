package com.unimuenster.govlearnapi.core.globalExceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
