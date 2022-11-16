package com.demo.webflux_demo.dto;

public record InputFailedValidationResponse(
        int errorCode,
        int input,
        String message
) {
}
