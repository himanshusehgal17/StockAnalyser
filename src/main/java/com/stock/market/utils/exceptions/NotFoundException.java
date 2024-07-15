package com.stock.market.utils.exceptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String errorMessage) {
        throw new RuntimeException(errorMessage);
    }
}
