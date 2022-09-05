package com.leovegas.wallet.exception;

import com.leovegas.wallet.dto.rest_response.RestResponse;
import com.leovegas.wallet.dto.rest_response.RestResponseType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<RestResponse> handleNotFoundException(NotFoundException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestResponse(RestResponseType.ERROR, ex.getMessage()));
    }

    @ExceptionHandler(value = {TransactionRunningException.class})
    protected ResponseEntity<RestResponse> handleTransactionRunningException(TransactionRunningException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new RestResponse(RestResponseType.ERROR, ex.getMessage()));
    }
}
