package com.stock.market.models;

import com.stock.market.dto.ModelDataDTO;
import com.stock.market.dto.OptionDataDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ModelDataDTOV2 {

    private BigDecimal pcr;
    private BigDecimal changeInPcr;
    private BigDecimal spotPrice;
    private BigDecimal variation;
    private String lastSyncTime;
    private List<OptionDataDTO> optionDataList;



}
