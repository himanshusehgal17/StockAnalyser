package com.stock.market.services;

import com.stock.market.entities.Contract;
import com.stock.market.repositories.ContractRepository;
import com.stock.market.utils.exceptions.ApplicationException;
import com.stock.market.utils.exceptions.BusinessException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ContractService {

    private final ContractRepository contractRepository;

    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public Contract createContract(Contract contract) {
        Contract createdContract = null;
        try {
            createdContract  = contractRepository.save(contract);

        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                throw new BusinessException("Contract code must be unique");
            }
            throw new ApplicationException(e.getMessage());  // rethrow other cases
        } catch (Exception e) {
            throw e;
        }

        return createdContract;
    }

}
