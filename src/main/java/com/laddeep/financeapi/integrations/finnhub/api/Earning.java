package com.laddeep.financeapi.integrations.finnhub.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Earning {

    private String date;

    private float epsActual;

    private float epsEstimate;

    private String hour;

    private int quarter;

    private double revenueActual;

    private double revenueEstimate;

    private String symbol;

    private int year;

    public Earning(){}

    public Earning(String date,
                   float epsActual,
                   float epsEstimate,
                   String hour,
                   int quarter,
                   double revenueActual,
                   double revenueEstimate,
                   String symbol,
                   int year) {
        this.date = date;
        this.epsActual = epsActual;
        this.epsEstimate = epsEstimate;
        this.hour = hour;
        this.quarter = quarter;
        this.revenueActual = revenueActual;
        this.revenueEstimate = revenueEstimate;
        this.symbol = symbol;
        this.year = year;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public int getQuarter() {
        return quarter;
    }

    public void setQuarter(int quarter) {
        this.quarter = quarter;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Earning{" +
                "date='" + date + '\'' +
                ", epsActual=" + epsActual +
                ", epsEstimate=" + epsEstimate +
                ", hour='" + hour + '\'' +
                ", quarter=" + quarter +
                ", revenueActual=" + revenueActual +
                ", revenueEstimate=" + revenueEstimate +
                ", symbol='" + symbol + '\'' +
                ", year=" + year +
                '}';
    }
}
