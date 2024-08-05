package com.stock.market.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.market.dto.ModelDataDTO;
import com.stock.market.dto.OptionDataDTO;
import com.stock.market.dto.OptionDetailDTO;
import com.stock.market.entities.OptionData;
import com.stock.market.models.IndicativeNifty50DTO;
import com.stock.market.models.NseResponse;
import com.stock.market.services.NseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.stock.market.utils.Utility.roundOff;

@RestController
public class NseController {

    @Autowired
    private NseService nseService;

    @GetMapping("/option-chain")
    public ModelAndView getOptionChain() {
        ModelDataDTO modelDataDTO = nseService.latestOptionData();
        List<OptionData> list = modelDataDTO.getList();
        IndicativeNifty50DTO indicativeNifty50DTO = modelDataDTO.getIndicativeNifty50DTO();
        // Calculate the total sums
        BigDecimal totalCeOpenInterest = list.stream()
                .map(OptionData::getCeOpenInterest)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPeOpenInterest = list.stream()
                .map(OptionData::getPeOpenInterest)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalChangeCeOpenInterest = list.stream()
                .map(OptionData::getCeChangeinOpenInterest)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalChangePeOpenInterest = list.stream()
                .map(OptionData::getPeChangeinOpenInterest)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal pcr =  totalCeOpenInterest.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : totalPeOpenInterest.divide(totalCeOpenInterest,4 ,RoundingMode.HALF_UP);
        BigDecimal changeInPcr = totalChangeCeOpenInterest.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : totalChangePeOpenInterest.divide(totalChangeCeOpenInterest,4 ,RoundingMode.HALF_UP);

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
        modelAndView.addObject("spotPrice", indicativeNifty50DTO.getSpotPrice());
        modelAndView.addObject("variation", indicativeNifty50DTO.getVariation());
        return modelAndView;
    }

    @GetMapping("/chart")
    public ModelAndView getChart() {
        Date currentDate = new Date();
        currentDate.setDate(currentDate.getDate()-1);
        int interval = 3;
        List<Object[]> optionDetails = nseService.getOptionDetails(currentDate, interval);
        List<Object[]> updatedList = optionDetails.stream()
                .map(objects -> {
                    objects[0] = ((String) objects[0]).split(" ")[1].substring(0,5);
                    return objects;
                })
                .collect(Collectors.toList());




        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("chart");
        modelAndView.addObject("graphTitle", "PCR & Change in PCR Graph");
        modelAndView.addObject("col1", "Date");
        modelAndView.addObject("col2", "PCR");
        modelAndView.addObject("col3", "Change in PCR");
        modelAndView.addObject("chartData", updatedList);
        return modelAndView;
    }

}
