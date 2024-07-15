package com.stock.market.utils;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Data
public class Result implements Serializable {

    private Boolean flag;
    private StatusCode code;
    private String message;
    private Object data;

    public Result() {
    }

    public Result(Boolean flag, StatusCode code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public Result(Boolean flag, StatusCode code, String message, Object data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
