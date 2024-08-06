package com.stock.market.schedular;

import com.stock.market.entities.CronExecutionLog;
import com.stock.market.models.IndicativeNifty50DTO;
import com.stock.market.models.NseResponse;
import com.stock.market.entities.OptionData;
import com.stock.market.services.CronExecutionLogService;
import com.stock.market.services.NseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
@EnableScheduling
public class NseScheduler {

    @Autowired
    private NseService nseService;

    @Autowired
    private CronExecutionLogService cronExecutionService;

    @Scheduled(cron = "*/10 * * * * *") // Every 3 minutes, Monday to Friday
    public void fetchAndSaveData() {

        IndicativeNifty50DTO marketStatus = nseService.getMarketStatus().block();
        if(marketStatus == null) return;
        if(cronExecutionService.getLastExecuted(marketStatus.getIndexName()) == null) {
            logCronExecutionTime(marketStatus.getIndexName(),marketStatus.getDateTime());
            NseResponse nseResponse = nseService.getOptionChain(null).block();
            if (nseResponse == null || nseResponse.getFiltered() == null) return;
            for (NseResponse.OptionData data : nseResponse.getFiltered().getData()) {
                OptionData optionData = new OptionData();
                optionData.setCreatedOn(marketStatus.getDateTime());
                optionData.setCeOpenInterest(data.getCE().getOpenInterest());
                optionData.setCeChangeinOpenInterest(data.getCE().getChangeinOpenInterest());
                optionData.setPeOpenInterest(data.getPE().getOpenInterest());
                optionData.setPeChangeinOpenInterest(data.getPE().getChangeinOpenInterest());
                optionData.setStrikePrice(data.getStrikePrice());
                nseService.saveOptionChainData(optionData);
            }
        }
        else if(cronExecutionService.getLastExecuted(marketStatus.getIndexName()).isBefore(marketStatus.getDateTime())) {
            cronExecutionService.updateLastExecuted(marketStatus.getIndexName(),marketStatus.getDateTime());
            NseResponse nseResponse = nseService.getOptionChain(null).block();
            if (nseResponse == null || nseResponse.getFiltered() == null) return;
            for (NseResponse.OptionData data : nseResponse.getFiltered().getData()) {
                OptionData optionData = new OptionData();
                optionData.setCreatedOn(marketStatus.getDateTime());
                optionData.setCeOpenInterest(data.getCE().getOpenInterest());
                optionData.setCeChangeinOpenInterest(data.getCE().getChangeinOpenInterest());
                optionData.setPeOpenInterest(data.getPE().getOpenInterest());
                optionData.setPeChangeinOpenInterest(data.getPE().getChangeinOpenInterest());
                optionData.setStrikePrice(data.getStrikePrice());
                nseService.saveOptionChainData(optionData);
            }
        }


    }

    private void logCronExecutionTime(String cronCode, LocalDateTime localDateTime) {
        CronExecutionLog log = new CronExecutionLog();
        log.setCronCode(cronCode);
        log.setLastExecuted(localDateTime);
        log.setCronExpression("0 */3 * * MON-FRI");
        log.setDescription("Scheduled task executed");
        cronExecutionService.save(log);
    }
}
