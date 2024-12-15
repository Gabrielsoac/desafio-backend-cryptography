package com.gabryellow.cryptography.controllers.exceptionHandlers;

import com.gabryellow.cryptography.config.APIRestError;
import com.gabryellow.cryptography.config.APIRestErrorBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class IllegalArgumentExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<APIRestError> illegalArgumentExceptionHandler(IllegalArgumentException e){
        APIRestError error = APIRestErrorBuilder.aAPIRestError()
                .withCode(HttpStatus.BAD_REQUEST.value())
                .withName(HttpStatus.BAD_REQUEST.name())
                .withError(e.getMessage())
        .build();

        return ResponseEntity.badRequest().body(error);
    }
}
