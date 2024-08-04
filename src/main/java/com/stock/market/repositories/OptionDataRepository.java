package com.stock.market.repositories;

import com.stock.market.entities.OptionData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OptionDataRepository extends JpaRepository<OptionData, Long> {

    @Query("SELECT o FROM OptionData o WHERE o.createdOn = :createdOn ORDER BY o.strikePrice ASC")
    List<OptionData> findByCreatedOnAndSortByStrikePriceAsc(@Param("createdOn") LocalDateTime createdOn);
}
