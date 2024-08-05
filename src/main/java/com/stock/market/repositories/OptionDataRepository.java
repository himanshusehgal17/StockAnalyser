package com.stock.market.repositories;

import com.stock.market.dto.OptionDetailDTO;
import com.stock.market.entities.OptionData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface OptionDataRepository extends JpaRepository<OptionData, Long> {

    @Query("SELECT o FROM OptionData o WHERE o.createdOn = :createdOn ORDER BY o.strikePrice ASC")
    List<OptionData> findByCreatedOnAndSortByStrikePriceAsc(@Param("createdOn") LocalDateTime createdOn);

    @Query(value = "SELECT " +
            "DATE_FORMAT(created_on - INTERVAL (MINUTE(created_on) % :interval) MINUTE, '%Y-%m-%d %H:%i:00') AS interval_start, " +
            "SUM(pe_open_interest) / SUM(ce_open_interest) AS PCR, " +
            "SUM(pe_changein_open_interest) / SUM(ce_changein_open_interest) AS ChangeInPCR " +
            "FROM option_details " +
            "WHERE created_on BETWEEN :startDate AND DATE_ADD(:startDate, INTERVAL 1 DAY) " +
            "GROUP BY interval_start " +
            "ORDER BY interval_start", nativeQuery = true)
    List<Object[]> findByCreatedOnBetweenWithInterval(
            @Param("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @Param("interval") int interval
    );
}
