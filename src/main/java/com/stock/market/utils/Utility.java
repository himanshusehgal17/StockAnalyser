package com.stock.market.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Utility {

    public static BigDecimal roundOff(BigDecimal value) {
        if (value == null) {
            return null;
        }
        return value.setScale(4, RoundingMode.HALF_UP);
    }
}
