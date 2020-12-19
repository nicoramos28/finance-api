package com.laddeep.financeapi.repository;

import com.laddeep.financeapi.entity.db.StockEarning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.OffsetDateTime;

public interface StockEarningRepository extends JpaRepository<StockEarning, Long> {

    @Query(value = "SELECT * FROM stock_earning se WHERE se.id_quote = ? and date(se.date) = date(?)",
            nativeQuery = true)
    StockEarning findByQuoteIdAndDate(Long id, OffsetDateTime date);

    StockEarning findByQuoteId(Long id);
}
