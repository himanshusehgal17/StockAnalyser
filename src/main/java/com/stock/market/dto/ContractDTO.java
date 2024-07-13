package com.stock.market.dto;

public record ContractDTO(Integer id,
                          String contractCode,
                          String contractDescription,
                          String contractType) {
}
