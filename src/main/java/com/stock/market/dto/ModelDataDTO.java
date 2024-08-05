package com.stock.market.dto;

import com.stock.market.entities.OptionData;
import com.stock.market.models.IndicativeNifty50DTO;
import lombok.Data;

import java.util.List;

@Data
public class ModelDataDTO {

    private List<OptionData> list;
    private IndicativeNifty50DTO indicativeNifty50DTO;

}
