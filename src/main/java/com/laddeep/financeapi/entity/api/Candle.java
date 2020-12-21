package com.laddeep.financeapi.entity.api;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class Candle {

    private List<BigDecimal> c;

    private List<BigDecimal> h;

    private List<BigDecimal> l;

    private List<BigDecimal> o;

    private List<Double> v;

    public Candle(){}

    public Candle(List<BigDecimal> c, List<BigDecimal> h, List<BigDecimal> l, List<BigDecimal> o, List<Double> v) {
        this.c = c;
        this.h = h;
        this.l = l;
        this.o = o;
        this.v = v;
    }

    public List<BigDecimal> getC() {
        return c;
    }

    public void setC(List<BigDecimal> c) {
        this.c = c;
    }

    public List<BigDecimal> getH() {
        return h;
    }

    public void setH(List<BigDecimal> h) {
        this.h = h;
    }

    public List<BigDecimal> getL() {
        return l;
    }

    public void setL(List<BigDecimal> l) {
        this.l = l;
    }

    public List<BigDecimal> getO() {
        return o;
    }

    public void setO(List<BigDecimal> o) {
        this.o = o;
    }

    public List<Double> getV() {
        return v;
    }

    public void setV(List<Double> v) {
        this.v = v;
    }

    @Override
    public String toString() {
        return "Candle{" +
                "c=" + c +
                ", h=" + h +
                ", l=" + l +
                ", o=" + o +
                ", v=" + v +
                '}';
    }
}
