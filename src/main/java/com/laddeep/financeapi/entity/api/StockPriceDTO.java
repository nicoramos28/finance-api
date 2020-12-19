package com.laddeep.financeapi.entity.api;

import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class StockPriceDTO {

    private BigDecimal currentPrice;

    private BigDecimal highestPrice;

    private BigDecimal lowestPrice;

    private BigDecimal openPrice;

    private BigDecimal previousClosePrice;

    private OffsetDateTime time;

    public StockPriceDTO(){}

    public StockPriceDTO(BigDecimal currentPrice, BigDecimal highestPrice, BigDecimal lowestPrice, BigDecimal openPrice, BigDecimal previousClosePrice, OffsetDateTime time) {
        this.currentPrice = currentPrice;
        this.highestPrice = highestPrice;
        this.lowestPrice = lowestPrice;
        this.openPrice = openPrice;
        this.previousClosePrice = previousClosePrice;
        this.time = time;
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

    public BigDecimal getPreviousClosePrice() {
        return previousClosePrice;
    }

    public void setPreviousClosePrice(BigDecimal previousClosePrice) {
        this.previousClosePrice = previousClosePrice;
    }

    public OffsetDateTime getTime() {
        return time;
    }

    public void setTime(OffsetDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "StockPriceDTO{" +
                "closePrice='" + currentPrice + '\'' +
                ", highestPrice='" + highestPrice + '\'' +
                ", lowestPrice='" + lowestPrice + '\'' +
                ", openPrice='" + openPrice + '\'' +
                ", previousClosePrice='" + previousClosePrice + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
