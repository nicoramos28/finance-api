package com.laddeep.financeapi.entity.db;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "stock_technical_sma", catalog = "finance_api")
public class StockSma {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "period", nullable = false)
    private int period;

    @Column(name = "id_quote", nullable = false)
    private Long quoteId;

    @Column(name = "date", nullable = false)
    private OffsetDateTime date;

    @Column(name = "value", nullable = false)
    private BigDecimal value;

    @Column(name = "status")
    private int status;

    public StockSma(){}

    public StockSma(Long id, int period, Long quoteId, OffsetDateTime date, BigDecimal value, int status) {
        this.id = id;
        this.period = period;
        this.quoteId = quoteId;
        this.date = date;
        this.value = value;
        this.status = status;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "StockSma{" +
                "id=" + id +
                ", period=" + period +
                ", quoteId=" + quoteId +
                ", date=" + date +
                ", value=" + value +
                ", status=" + status +
                '}';
    }

}
