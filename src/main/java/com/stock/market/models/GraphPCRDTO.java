package com.stock.market.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GraphPCRDTO {
    private String graphTitle;
    private String lastSyncTime;
    private List<Object[]> chartData;
}
