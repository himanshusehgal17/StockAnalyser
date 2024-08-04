package com.stock.market.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cron_execution_log")
public class CronExecutionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cron_code",nullable = false)
    private String cronCode;

    @Column(name = "last_executed", nullable = false)
    private java.time.LocalDateTime lastExecuted;

    @Column(name = "cron_expression", nullable = false, length = 255)
    private String cronExpression;

    @Column(name = "description", length = 255)
    private String description;
}
