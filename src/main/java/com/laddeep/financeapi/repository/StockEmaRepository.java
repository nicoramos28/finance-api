package com.laddeep.financeapi.repository;

import com.laddeep.financeapi.entity.db.StockEma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.OffsetDateTime;
import java.util.List;

public interface StockEmaRepository extends JpaRepository<StockEma, Long> {

    StockEma findByQuoteIdAndPeriod(Long quoteId, int period);

    @Query(value = "SELECT * FROM stock_technical_ema se WHERE se.id_quote = ? and se.period = ? and date(se.date) = date(?) order by date desc limit 3",
            nativeQuery = true)
    StockEma findByQuoteIdAndPeriodAndDate(Long quoteId, int period, OffsetDateTime date);

    @Query(value = "SELECT * FROM stock_technical_ema se WHERE se.id_quote = ? and se.period = ? order by date desc limit 3",
            nativeQuery = true)
    List<StockEma> findLastEmaValues(Long quoteId, int period);
}
