package com.laddeep.financeapi.repository;

import com.laddeep.financeapi.entity.db.StockExchangeHoliday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.OffsetDateTime;

public interface ExchangeHolidaysRepository extends JpaRepository<StockExchangeHoliday, OffsetDateTime> {


    @Query(value = "SELECT * FROM stock_exchange_holiday seh WHERE date(seh.date) = date(?)",
            nativeQuery = true)
    StockExchangeHoliday findByDate(String time);
}
