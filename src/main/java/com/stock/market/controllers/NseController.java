package com.stock.market.controllers;

import com.stock.market.dto.OptionDataDTO;
import com.stock.market.entities.OptionData;
import com.stock.market.models.NseResponse;
import com.stock.market.services.NseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static com.stock.market.utils.Utility.roundOff;

@RestController
public class NseController {

    @Autowired
    private NseService nseService;

    @GetMapping("/option-chain")
    public ModelAndView getOptionChain(Model model) {
        List<OptionData> list = nseService.latestOptionData();
        // Calculate the total sums
        BigDecimal totalCeOpenInterest = list.stream()
                .map(OptionData::getCeOpenInterest)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPeOpenInterest = list.stream()
                .map(OptionData::getPeOpenInterest)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalChangeCeOpenInterest = list.stream()
                .map(OptionData::getCeOpenInterest)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalChangePeOpenInterest = list.stream()
                .map(OptionData::getPeOpenInterest)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal pcr = totalPeOpenInterest.divide(totalCeOpenInterest,4 ,RoundingMode.HALF_UP);
        BigDecimal changeInPcr = totalChangePeOpenInterest.divide(totalChangeCeOpenInterest,4 ,RoundingMode.HALF_UP);

        List<OptionDataDTO> updatedList = list.stream()
                .map(optionData -> OptionDataDTO.builder()
                        .strikePrice(optionData.getStrikePrice())
                        .peOpenInterest(optionData.getPeOpenInterest())
                        .peChangeinOpenInterest(optionData.getPeChangeinOpenInterest())
                        .ceOpenInterest(optionData.getCeOpenInterest())
                        .ceChangeinOpenInterest(optionData.getCeChangeinOpenInterest())
                        .pcr(optionData.getCeOpenInterest().compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : optionData.getPeOpenInterest().divide(optionData.getCeOpenInterest(),4, RoundingMode.HALF_UP))
                        .changeInPCR(optionData.getCeChangeinOpenInterest().compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : optionData.getPeChangeinOpenInterest().divide(optionData.getCeChangeinOpenInterest(),4, RoundingMode.HALF_UP))
                        .build())
                .toList();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("optionData");
        modelAndView.addObject("optionDataList", updatedList);
        modelAndView.addObject("pcr", pcr);
        modelAndView.addObject("changeInPcr", changeInPcr);
        return modelAndView;
    }

}
