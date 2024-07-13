package com.stock.market.services;

import com.stock.market.models.Contract;
import com.stock.market.repositories.ContractRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ContractService {

    private final ContractRepository contractRepository;

    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public Contract createContract(Contract contract) {
        return contractRepository.save(contract);
    }

}
