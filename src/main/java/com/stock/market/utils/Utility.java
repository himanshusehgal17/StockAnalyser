package com.stock.market.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.stock.market.dto.OptionDetailDTO;

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
