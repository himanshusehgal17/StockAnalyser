package com.stock.market.utils.exceptions;

public class ApplicationException extends RuntimeException {

    public ApplicationException(String errorMessage) {
        super(errorMessage);
    }
}
