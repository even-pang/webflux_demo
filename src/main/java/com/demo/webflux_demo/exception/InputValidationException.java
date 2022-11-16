package com.demo.webflux_demo.exception;

import lombok.Getter;

@Getter
public class InputValidationException extends RuntimeException {
    private static final String MESSAGE = "allowed range in 10 - 20";
    private static final int ERROR_CODE = 100;
    private final int input;

    public InputValidationException(final int input) {
        this.input = input;
    }

    public static int errorCode() {
        return ERROR_CODE;
    }

    public static String message() {
        return MESSAGE;
    }
}
