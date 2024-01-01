package com.unimuenster.govlearnapi.user.controllerAdvice;

import com.unimuenster.govlearnapi.common.responsewrapper.Message;
import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.user.exception.SamePasswordException;
import com.unimuenster.govlearnapi.user.exception.NothingChangedException;
import com.unimuenster.govlearnapi.user.exception.UserExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UserControllerAdvice {

    @ExceptionHandler(UserExistsException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Response> handleResourceNotFoundException(UserExistsException e) {
        return new ResponseEntity<Response>(Response.of(new Message("User exists already")), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleResourceNotFoundException(UsernameNotFoundException e) {
        return new ResponseEntity<Response>(Response.of(new Message("User not found")), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleResourceNotFoundException(BadCredentialsException e) {
        return new ResponseEntity<Response>(Response.of(new Message("Password wrong")), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NothingChangedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response> handleResourceNothingChangedException(NothingChangedException e) {
        return new ResponseEntity<Response>(Response.of("Keine Änderung gefunden!", false), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SamePasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response> handleResourceSamePasswordException(SamePasswordException e) {
        return new ResponseEntity<Response>(Response.of("Gleiches Passwort!", false), HttpStatus.BAD_REQUEST);
    }
}
