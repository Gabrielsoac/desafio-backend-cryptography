package com.gabryellow.cryptography.controllers.exceptionHandlers;

import com.gabryellow.cryptography.config.APIRestError;
import com.gabryellow.cryptography.config.APIRestErrorBuilder;
import com.gabryellow.cryptography.services.exceptions.PurchaseNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class PurchaseExceptionHandler {

    @ExceptionHandler(PurchaseNotFoundException.class)
    public ResponseEntity<APIRestError> purchaseNotFoundExceptionHandler(PurchaseNotFoundException exception){

        APIRestError apiRestError = APIRestErrorBuilder
            .aAPIRestError()
            .withError(exception.getMessage())
            .withCode(HttpStatus.NOT_FOUND.value())
            .withName(HttpStatus.NOT_FOUND.name())
        .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiRestError);

    }
}
