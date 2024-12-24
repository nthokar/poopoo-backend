package ru.raperan.poopoo.mainservice.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.raperan.poopoo.mainservice.api.TrackCollectionEnd;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ TrackCollectionEnd.class })
    protected ResponseEntity<Object> handleConflict() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}