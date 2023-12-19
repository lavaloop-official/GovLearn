package com.unimuenster.govlearnapi.core.controllerAdvice;

import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.core.globalExceptions.NotFoundException;
import com.unimuenster.govlearnapi.core.globalExceptions.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleResourceNotFoundException(NotFoundException e) {
        return new ResponseEntity<Response>(Response.of(e.getMessage(), false), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Response> handleUnauthorizedException(UnauthorizedException e) {
        return new ResponseEntity<Response>(Response.of(e.getMessage(), false), HttpStatus.UNAUTHORIZED);
    }
}
