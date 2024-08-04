package com.stock.market.models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IndicativeNifty50DTO {
    private LocalDateTime dateTime;
    private String indexName;
}
