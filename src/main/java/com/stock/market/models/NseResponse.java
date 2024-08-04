package com.stock.market.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NseResponse {

    private Filtered filtered;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Filtered {
        private List<OptionData> data;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OptionData {
        private BigDecimal strikePrice;
        private String expiryDate;
        @JsonProperty("PE")
        private OptionDetails PE;
        @JsonProperty("CE")
        private OptionDetails CE;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OptionDetails {
        private int strikePrice;
        private String expiryDate;
        private String underlying;
        private String identifier;
        private BigDecimal openInterest;
        private BigDecimal changeinOpenInterest;
        private BigDecimal pchangeinOpenInterest;
        private int totalTradedVolume;
        private BigDecimal impliedVolatility;
        private BigDecimal lastPrice;
        private BigDecimal change;
        private BigDecimal pChange;
        private int totalBuyQuantity;
        private int totalSellQuantity;
        private int bidQty;
        private BigDecimal bidprice;
        private int askQty;
        private double askPrice;
        private double underlyingValue;
    }
}
