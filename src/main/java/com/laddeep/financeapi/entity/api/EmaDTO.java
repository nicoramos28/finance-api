package com.laddeep.financeapi.entity.api;

import lombok.Data;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class EmaDTO {

    private BigDecimal ema;

    private BigDecimal c;

    private BigDecimal h;

    private BigDecimal l;

    private BigDecimal o;

    private Double v;

    private Timestamp t;

    public EmaDTO(){};

    public EmaDTO(BigDecimal ema,
                  BigDecimal c,
                  BigDecimal h,
                  BigDecimal l,
                  BigDecimal o,
                  Double v,
                  Timestamp t) {
        this.ema = ema;
        this.c = c;
        this.h = h;
        this.l = l;
        this.o = o;
        this.v = v;
        this.t = t;
    }

    public BigDecimal getEma() {
        return ema;
    }

    public void setEma(BigDecimal ema) {
        this.ema = ema;
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
        return "EmaDTO{" +
                "ema=" + ema +
                ", c=" + c +
                ", h=" + h +
                ", l=" + l +
                ", o=" + o +
                ", v=" + v +
                ", t=" + t +
                '}';
    }
}
