package com.stock.market.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OptionDataDTO {

    private BigDecimal strikePrice;
    private BigDecimal ceOpenInterest;
    private BigDecimal ceChangeinOpenInterest;
    private BigDecimal peOpenInterest;
    private BigDecimal peChangeinOpenInterest;
    private BigDecimal pcr;
    private BigDecimal changeInPCR;

}