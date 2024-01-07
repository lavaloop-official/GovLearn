package com.unimuenster.govlearnapi.core.globalExceptions;

public class UnauthorizedException  extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
