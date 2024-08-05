package com.stock.market.models;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class IndicativeNifty50DTO {
    private LocalDateTime dateTime;
    private String indexName;
    private BigDecimal spotPrice;
    private BigDecimal variation;
}
