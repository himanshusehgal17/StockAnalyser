package com.stock.market.repositories;

import com.stock.market.entities.CronExecutionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface CronExecutionLogRepository extends JpaRepository<CronExecutionLog, Long> {
    
    // Method to find last executed time by cron code
    @Query("SELECT c.lastExecuted FROM CronExecutionLog c WHERE c.cronCode = :cronCode")
    LocalDateTime findLastExecutedByCronCode(@Param("cronCode") String cronCode);

    // Method to update last executed time by cron code
    @Modifying
    @Query("UPDATE CronExecutionLog c SET c.lastExecuted = :lastExecuted WHERE c.cronCode = :cronCode")
    void updateLastExecutedByCronCode(@Param("cronCode") String cronCode, @Param("lastExecuted") LocalDateTime lastExecuted);
}
