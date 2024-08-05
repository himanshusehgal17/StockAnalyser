package com.stock.market.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.stock.market.dto.ModelDataDTO;
import com.stock.market.models.IndicativeNifty50DTO;
import com.stock.market.models.NseResponse;
import com.stock.market.entities.OptionData;
import com.stock.market.repositories.OptionDataRepository;
import com.stock.market.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NseService {

    @Autowired
    private WebClient webClient;

    @Autowired
    private OptionDataRepository optionDataRepository;

    public Mono<NseResponse> getOptionChain() {
        return webClient.get()
                .uri("https://www.nseindia.com/api/option-chain-indices?symbol=NIFTY")
                .retrieve()
                .bodyToMono(NseResponse.class)
                .map(nseResponse -> {
                    NseResponse.Filtered filtered = new NseResponse.Filtered();
                    filtered.setData(nseResponse.getFiltered().getData());
                    NseResponse response = new NseResponse();
                    response.setFiltered(filtered);
                    return response;
                })
                .onErrorResume(WebClientResponseException.class, ex -> {
                    // Handle the exception
                    return Mono.empty();
                });
    }


    public Mono<IndicativeNifty50DTO> getMarketStatus() {
        return webClient.get()
                .uri("https://www.nseindia.com/api/marketStatus")
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(jsonNode -> {
                    JsonNode marketState = jsonNode.get("marketState");
                    if (marketState != null) {
                        IndicativeNifty50DTO dto = new IndicativeNifty50DTO();
                        dto.setDateTime(DateTimeUtils.parseDateTime(marketState.get(0).get("tradeDate").asText()));
                        dto.setIndexName(marketState.get(0).get("index").asText());
                        dto.setSpotPrice(new BigDecimal(marketState.get(0).get("last").asText()).setScale(4, RoundingMode.HALF_UP));
                        dto.setVariation(new BigDecimal(marketState.get(0).get("variation").asText()).setScale(4, RoundingMode.HALF_UP));
                        return dto;
                    }
                    return null;
                })
                .onErrorResume(WebClientResponseException.class, ex -> {
                    // Handle the exception
                    return Mono.empty();
                });
    }

    public void saveOptionChainData(OptionData optionData) {
        optionDataRepository.save(optionData);
    }

    public ModelDataDTO latestOptionData() {
        IndicativeNifty50DTO indicativeNifty50DTO = getMarketStatus().block();
        if(indicativeNifty50DTO == null) return null;
        List<OptionData> list = optionDataRepository.findByCreatedOnAndSortByStrikePriceAsc(indicativeNifty50DTO.getDateTime());
        ModelDataDTO modelDataDTO = new ModelDataDTO();
        modelDataDTO.setList(list);
        modelDataDTO.setIndicativeNifty50DTO(indicativeNifty50DTO);
        return modelDataDTO;
    }
}
