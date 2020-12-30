package com.laddeep.financeapi.entity.api;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class SmaDTO {

    private BigDecimal sma;

    private BigDecimal c;

    private BigDecimal h;

    private BigDecimal l;

    private BigDecimal o;

    private Double v;

    private Timestamp t;

    public SmaDTO(){}

    public SmaDTO(BigDecimal sma,
                  BigDecimal c,
                  BigDecimal h,
                  BigDecimal l,
                  BigDecimal o,
                  Double v,
                  Timestamp t) {
        this.sma = sma;
        this.c = c;
        this.h = h;
        this.l = l;
        this.o = o;
        this.v = v;
        this.t = t;
    }

    public BigDecimal getSma() {
        return sma;
    }

    public void setSma(BigDecimal sma) {
        this.sma = sma;
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

    public Double getV() {
        return v;
    }

    public void setV(Double v) {
        this.v = v;
    }

    public Timestamp getT() {
        return t;
    }

    public void setT(Timestamp t) {
        this.t = t;
    }

    @Override
    public String toString() {
        return "SmaDTO{" +
                "sma=" + sma +
                ", c=" + c +
                ", h=" + h +
                ", l=" + l +
                ", o=" + o +
                ", v=" + v +
                ", t=" + t +
                '}';
    }
}
