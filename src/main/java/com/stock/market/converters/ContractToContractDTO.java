package com.stock.market.converters;

import com.stock.market.dto.ContractDTO;
import com.stock.market.entities.Contract;
import org.springframework.core.convert.converter.Converter;

public class ContractToContractDTO implements Converter<Contract, ContractDTO> {
    @Override
    public ContractDTO convert(Contract source) {
        return new ContractDTO(source.getId(),
                source.getContractCode(),
                source.getContractDescription(),
                source.getContractType());
    }
}
