package com.laddeep.financeapi.repository;

import com.laddeep.financeapi.entity.db.StockExchangeHoliday;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;

public interface ExchangeHolidaysRepository extends JpaRepository<StockExchangeHoliday, OffsetDateTime> {

    public StockExchangeHoliday findByDate(OffsetDateTime date);
}
