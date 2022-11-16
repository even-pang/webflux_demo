package com.demo.webflux_demo.exception.handler;

import com.demo.webflux_demo.dto.InputFailedValidationResponse;
import com.demo.webflux_demo.exception.InputValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InputValidationHandler {

    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<InputFailedValidationResponse> handleException(final InputValidationException e) {
        InputFailedValidationResponse response = new InputFailedValidationResponse(
                InputValidationException.errorCode(),
                e.getInput(),
                InputValidationException.message()
        );

        return ResponseEntity.badRequest().body(response);
    }
}
