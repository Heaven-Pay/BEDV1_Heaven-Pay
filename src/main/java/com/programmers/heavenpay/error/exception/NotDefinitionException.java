package com.programmers.heavenpay.error.exception;

import com.programmers.heavenpay.error.ErrorMessage;

public class NotDefinitionException extends BusinessException {
    public NotDefinitionException(ErrorMessage message) {
        super(message);
    }
}
