package com.stock.market.controllers;

import com.stock.market.dto.ContractDTO;
import com.stock.market.utils.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class ContractController {

    @PostMapping("/createContract")
    public Result createContract(@RequestBody ContractDTO contractDTO) {
        return null;
    }

}
