package com.laddeep.financeapi.integrations.finnhub.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockPriceQuote {

    private BigDecimal c;

    private BigDecimal h;

    private BigDecimal l;

    private BigDecimal o;

    private BigDecimal pc;

    private OffsetDateTime t;

    public StockPriceQuote(){}

    public StockPriceQuote(
            BigDecimal c,
            BigDecimal h,
            BigDecimal l,
            BigDecimal o,
            BigDecimal pc,
            OffsetDateTime t) {
        this.c = c;
        this.h = h;
        this.l = l;
        this.o = o;
        this.pc = pc;
        this.t = t;
    }

    public BigDecimal getC() {
        return c;
    }

    public void setC(BigDecimal c) {
        this.c = c;
    }

    public BigDecimal getH() {
        return h;
    }

    public void setH(BigDecimal h) {
        this.h = h;
    }

    public BigDecimal getL() {
        return l;
    }

    public void setL(BigDecimal l) {
        this.l = l;
    }

    public BigDecimal getO() {
        return o;
    }

    public void setO(BigDecimal o) {
        this.o = o;
    }

    public BigDecimal getPc() {
        return pc;
    }

    public void setPc(BigDecimal pc) {
        this.pc = pc;
    }

    public OffsetDateTime getT() {
        return t;
    }

    public void setT(OffsetDateTime t) {
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
