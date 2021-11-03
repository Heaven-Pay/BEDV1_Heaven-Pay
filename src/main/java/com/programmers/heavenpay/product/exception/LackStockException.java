package com.programmers.heavenpay.product.exception;

import com.programmers.heavenpay.error.ErrorMessage;

public class LackStockException extends RuntimeException{
    public LackStockException(ErrorMessage message) {
        super(message.getMessage());
    }
}
