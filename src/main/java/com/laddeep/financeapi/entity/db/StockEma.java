package com.laddeep.financeapi.entity.db;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "stock_tecnhical_ema", catalog = "finance_api")
public class StockEma {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "period")
    private int period;

    @Column(name = "id_quote")
    private Long quoteId;

    @Column(name = "date")
    private OffsetDateTime date;

    @Column(name = "value")
    private BigDecimal value;

    public StockEma(){}

    public StockEma(Long id, int period, Long quoteId, OffsetDateTime date, BigDecimal value) {
        this.id = id;
        this.period = period;
        this.quoteId = quoteId;
        this.date = date;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public Long getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(Long quoteId) {
        this.quoteId = quoteId;
    }

    public OffsetDateTime getDate() {
        return date;
    }

    public void setDate(OffsetDateTime date) {
        this.date = date;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "StockEma{" +
                "id=" + id +
                ", period=" + period +
                ", quoteId=" + quoteId +
                ", date=" + date +
                ", value=" + value +
                '}';
    }
}
