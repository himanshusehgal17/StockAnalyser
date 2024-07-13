package com.stock.market.utils;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {

    private Boolean flag;
    private StatusCode code;
    private String message;
    private Object data;

}
