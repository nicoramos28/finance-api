package com.laddeep.financeapi.repository;

import com.laddeep.financeapi.entity.db.StockSma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.OffsetDateTime;
import java.util.List;

public interface StockSmaRepository extends JpaRepository<StockSma, Long> {

    StockSma findByQuoteIdAndPeriod(Long quoteId, int period);

    @Query(value = "SELECT * FROM stock_technical_sma ss WHERE ss.id_quote = ? and ss.period = ? and date(ss.date) = date(?) order by date desc limit 3",
            nativeQuery = true)
    StockSma findByQuoteIdAndPeriodAndDate(Long quoteId, int period, OffsetDateTime date);

    @Query(value = "SELECT * FROM stock_technical_sma ss WHERE ss.id_quote = ? and ss.period = ? order by date desc limit 3",
            nativeQuery = true)
    List<StockSma> findLastSmaValues(Long quoteId, int period);
}
