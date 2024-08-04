package com.stock.market.services;

import com.fasterxml.jackson.databind.JsonNode;
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
                    JsonNode indicativeNifty50 = jsonNode.get("indicativenifty50");
                    if (indicativeNifty50 != null) {
                        IndicativeNifty50DTO dto = new IndicativeNifty50DTO();
                        dto.setDateTime(DateTimeUtils.parseDateTime(indicativeNifty50.get("dateTime").asText()));
                        dto.setIndexName(indicativeNifty50.get("indexName").asText());
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

    public List<OptionData> latestOptionData() {
        IndicativeNifty50DTO indicativeNifty50DTO = getMarketStatus().block();
        if(indicativeNifty50DTO == null) return null;
        return optionDataRepository.findByCreatedOnAndSortByStrikePriceAsc(indicativeNifty50DTO.getDateTime());
    }
}
