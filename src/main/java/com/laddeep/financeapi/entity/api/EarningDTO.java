package com.laddeep.financeapi.entity.api;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class EarningDTO {

    private float epsActual;

    private float epsEstimate;

    private String hour;

    private double revenueActual;

    private double revenueEstimate;

    private String symbol;

    private BigDecimal currentPrice;

    public EarningDTO(float epsActual,
                      float epsEstimate,
                      String hour,
                      double revenueActual,
                      double revenueEstimate,
                      String symbol,
                      BigDecimal currentPrice) {
        this.epsActual = epsActual;
        this.epsEstimate = epsEstimate;
        this.hour = hour;
        this.revenueActual = revenueActual;
        this.revenueEstimate = revenueEstimate;
        this.symbol = symbol;
        this.currentPrice = currentPrice;
    }

    public float getEpsActual() {
        return epsActual;
    }

    public void setEpsActual(float epsActual) {
        this.epsActual = epsActual;
    }

    public float getEpsEstimate() {
        return epsEstimate;
    }

    public void setEpsEstimate(float epsEstimate) {
        this.epsEstimate = epsEstimate;
    }

    public String getHour() { return hour; }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public double getRevenueActual() {
        return revenueActual;
    }

    public void setRevenueActual(double revenueActual) {
        this.revenueActual = revenueActual;
    }

    public double getRevenueEstimate() {
        return revenueEstimate;
    }

    public void setRevenueEstimate(double revenueEstimate) {
        this.revenueEstimate = revenueEstimate;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }
}
