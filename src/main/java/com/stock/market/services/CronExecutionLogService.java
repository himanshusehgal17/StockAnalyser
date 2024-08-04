package com.stock.market.services;

import com.stock.market.entities.CronExecutionLog;
import com.stock.market.repositories.CronExecutionLogRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CronExecutionLogService {

    @Autowired
    private CronExecutionLogRepository repository;

    // Retrieve the last executed time for a given cron code
    public LocalDateTime getLastExecuted(String cronCode) {
        return repository.findLastExecutedByCronCode(cronCode);
    }

    // Update the last executed time for a given cron code
    @Transactional
    public void updateLastExecuted(String cronCode, LocalDateTime lastExecuted) {
        repository.updateLastExecutedByCronCode(cronCode, lastExecuted);
    }

    public void save(CronExecutionLog log) {
        repository.save(log);
    }
}
