package com.laddeep.financeapi.api.stockPrice;

import lombok.Data;

@Data
public class StockPriceDTO {

    private String currentPrice;

    private String highestPrice;

    private String lowestPrice;

    private String openPrice;

    private String previousClosePrice;

    private String time;

    public StockPriceDTO(){}

    public StockPriceDTO(String currentPrice, String highestPrice, String lowestPrice, String openPrice, String previousClosePrice, String time) {
        this.currentPrice = currentPrice;
        this.highestPrice = highestPrice;
        this.lowestPrice = lowestPrice;
        this.openPrice = openPrice;
        this.previousClosePrice = previousClosePrice;
        this.time = time;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(String highestPrice) {
        this.highestPrice = highestPrice;
    }

    public String getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(String lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public String getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(String openPrice) {
        this.openPrice = openPrice;
    }

    public String getPreviousClosePrice() {
        return previousClosePrice;
    }

    public void setPreviousClosePrice(String previousClosePrice) {
        this.previousClosePrice = previousClosePrice;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
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
