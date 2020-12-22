package com.laddeep.financeapi.entity.db;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "stock_price", catalog = "finance_api")
public class StockPrice {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_quote")
    private Long quoteId;

    @Column(name = "current")
    private BigDecimal currentPrice;

    @Column(name = "highest")
    private BigDecimal highestPrice;

    @Column(name = "lowest")
    private BigDecimal lowestPrice;

    @Column(name = "open")
    private BigDecimal openPrice;

    @Column(name = "close")
    private BigDecimal closePrice;

    @Column(name = "previous_close")
    private BigDecimal previousClosePrice;

    @Column(name = "date")
    private OffsetDateTime time;

    @Column(name = "volumen")
    private double volumen;

    public StockPrice(){}

    public StockPrice(Long id,
                      Long quoteId,
                      BigDecimal currentPrice,
                      BigDecimal highestPrice,
                      BigDecimal lowestPrice,
                      BigDecimal openPrice,
                      BigDecimal closePrice,
                      BigDecimal previousClosePrice,
                      OffsetDateTime time,
                      double volumen) {
        this.id = id;
        this.quoteId = quoteId;
        this.currentPrice = currentPrice;
        this.highestPrice = highestPrice;
        this.lowestPrice = lowestPrice;
        this.openPrice = openPrice;
        this.closePrice = closePrice;
        this.previousClosePrice = previousClosePrice;
        this.time = time;
        this.volumen = volumen;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(Long quoteId) {
        this.quoteId = quoteId;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public BigDecimal getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(BigDecimal highestPrice) {
        this.highestPrice = highestPrice;
    }

    public BigDecimal getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(BigDecimal lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public BigDecimal getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(BigDecimal openPrice) {
        this.openPrice = openPrice;
    }

    public BigDecimal getClosePrice() { return closePrice; }

    public void setClosePrice(BigDecimal close) { this.closePrice = close;  }

    public BigDecimal getPreviousClosePrice() { return previousClosePrice; }

    public void setPreviousClosePrice(BigDecimal previousClosePrice) {
        this.previousClosePrice = previousClosePrice;
    }

    public OffsetDateTime getTime() {
        return time;
    }

    public void setTime(OffsetDateTime time) {
        this.time = time;
    }

    public double getVolumen() { return volumen; }

    public void setVolumen(double volumen) { this.volumen = volumen; }


    @Override
    public String toString() {
        return "StockPrice{" +
                "id=" + id +
                ", quoteId=" + quoteId +
                ", currentPrice=" + currentPrice +
                ", highestPrice=" + highestPrice +
                ", lowestPrice=" + lowestPrice +
                ", openPrice=" + openPrice +
                ", close=" + closePrice +
                ", previousClosePrice=" + previousClosePrice +
                ", time=" + time +
                ", volumen=" + volumen +
                '}';
    }

}
