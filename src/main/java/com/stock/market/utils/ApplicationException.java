package com.stock.market.utils;

public class ApplicationException extends RuntimeException {

    public ApplicationException(String errorCode) {
        throw new RuntimeException(errorCode);
    }
}
