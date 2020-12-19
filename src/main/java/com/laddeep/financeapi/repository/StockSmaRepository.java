package com.laddeep.financeapi.repository;

import com.laddeep.financeapi.entity.db.StockSma;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockSmaRepository extends JpaRepository<StockSma, Long> {

    StockSma findByQuoteIdAndPeriod(Long quoteId, int period);
}
