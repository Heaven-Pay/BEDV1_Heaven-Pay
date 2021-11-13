package com.programmers.heavenpay.error.exception;

import com.programmers.heavenpay.error.ErrorMessage;

public class LackStockException extends BusinessException {
    public LackStockException(ErrorMessage message) {
        super(message);
    }
}
