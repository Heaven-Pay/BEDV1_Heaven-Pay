package com.programmers.heavenpay.error.exception;

import com.programmers.heavenpay.error.ErrorMessage;

public class DuplicateDataException extends RuntimeException{
    public DuplicateDataException(ErrorMessage message) {
        super(message.getMessage());
    }
}
