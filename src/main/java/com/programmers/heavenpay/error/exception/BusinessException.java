package com.programmers.heavenpay.error.exception;

import com.programmers.heavenpay.error.ErrorMessage;

public class BusinessException extends RuntimeException {
    private ErrorMessage errorMessage;

    public BusinessException(ErrorMessage message) {
        super(message.getMessage());
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
