package com.laddeep.financeapi.integrations.finnhub.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockPriceQuote {

    private String c;

    private String h;

    private String l;

    private String o;

    private String pc;

    private String t;

    public StockPriceQuote(){}

    public StockPriceQuote(
            String c,
            String h,
            String l,
            String o,
            String pc,
            String t) {
        this.c = c;
        this.h = h;
        this.l = l;
        this.o = o;
        this.pc = pc;
        this.t = t;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getH() {
        return h;
    }

    public void setH(String h) {
        this.h = h;
    }

    public String getL() {
        return l;
    }

    public void setL(String l) {
        this.l = l;
    }

    public String getO() {
        return o;
    }

    public void setO(String o) {
        this.o = o;
    }

    public String getPc() {
        return pc;
    }

    public void setPc(String pc) {
        this.pc = pc;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    @Override
    public String toString() {
        return "StockPriceQuote{" +
                "c='" + c + '\'' +
                ", h='" + h + '\'' +
                ", l='" + l + '\'' +
                ", o='" + o + '\'' +
                ", pc='" + pc + '\'' +
                ", t='" + t + '\'' +
                '}';
    }
}
