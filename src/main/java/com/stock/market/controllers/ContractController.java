package com.stock.market.controllers;

import com.stock.market.models.Contract;
import com.stock.market.services.ContractService;
import com.stock.market.utils.Result;
import com.stock.market.utils.StatusCode;
import com.stock.market.utils.exceptions.ApplicationException;
import com.stock.market.utils.exceptions.BusinessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ContractController {

    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @PostMapping("/createContract")
    public Result createContract(@RequestBody Contract contract) {
        try {
            Contract createdContract = contractService.createContract(contract);
            return new Result(true, StatusCode.SUCCESS, "Created New Contract", createdContract);
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                throw new BusinessException("Contract code must be unique");
            }
            throw new ApplicationException(e.getMessage());  // rethrow other cases
        } catch (Exception e) {
            throw e;
        }
    }

}
