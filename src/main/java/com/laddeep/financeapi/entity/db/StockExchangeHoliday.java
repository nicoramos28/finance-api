package com.laddeep.financeapi.entity.db;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "stock_exchange_holiday", catalog = "finance_api")
public class StockExchangeHoliday {

    @Id
    @Column(name = "date", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private OffsetDateTime date;

    @Column(name = "holiday", nullable = false)
    private String holiday;

    public StockExchangeHoliday(){}

    public StockExchangeHoliday(OffsetDateTime date, String holiday) {
        this.date = date;
        this.holiday = holiday;
    }

    public OffsetDateTime getDate() {
        return date;
    }

    public void setDate(OffsetDateTime date) {
        this.date = date;
    }

    public String getHoliday() {
        return holiday;
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }

    @Override
    public String toString() {
        return "StockExchangeHolidays{" +
                "date=" + date +
                ", holiday='" + holiday + '\'' +
                '}';
    }
}
