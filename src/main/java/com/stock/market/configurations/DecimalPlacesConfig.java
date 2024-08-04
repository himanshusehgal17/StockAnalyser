package com.stock.market.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DecimalPlacesConfig {

    @Value("${decimal.places:4}")
    private int decimalPlaces;

    public int getDecimalPlaces() {
        return decimalPlaces;
    }
}
