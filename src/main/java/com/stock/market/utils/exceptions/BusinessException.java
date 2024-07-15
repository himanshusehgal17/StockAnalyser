package com.stock.market.utils.exceptions;

public class BusinessException extends RuntimeException {

    public BusinessException(String errorMessage) {
        super(errorMessage);
    }
}
