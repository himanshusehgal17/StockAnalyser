package com.stock.market.utils;

public enum StatusCode {
    SUCCESS(200, "Success"),
    NOT_FOUND(404, "Not Found"),
    SERVER_EXCEPTION(500, "Internal Server Error");

    private final int code;
    private final String description;

    StatusCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}

