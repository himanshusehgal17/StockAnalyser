package com.stock.market.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "option_details")
@AllArgsConstructor
@NoArgsConstructor
public class OptionData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime createdOn;
    private BigDecimal strikePrice;
    private BigDecimal ceOpenInterest;
    private BigDecimal ceChangeinOpenInterest;
    private BigDecimal peOpenInterest;
    private BigDecimal peChangeinOpenInterest;


    // Ensure data is rounded before persisting to the database
    @PrePersist
    public void prePersist() {
        roundValues();
    }

    // Ensure data is rounded after loading from the database
    @PostLoad
    public void postLoad() {
        roundValues();
    }

    private void roundValues() {
        this.strikePrice = round(this.strikePrice);
        this.ceOpenInterest = round(this.ceOpenInterest);
        this.ceChangeinOpenInterest = round(this.ceChangeinOpenInterest);
        this.peOpenInterest = round(this.peOpenInterest);
        this.peChangeinOpenInterest = round(this.peChangeinOpenInterest);

    }

    private BigDecimal round(BigDecimal value) {
        if (value == null) {
            return null;
        }
        return value.setScale(4, RoundingMode.HALF_UP);
    }

}
