package com.laddeep.financeapi.entity.api;

import lombok.Data;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
public class EmasDTO {

    private List<BigDecimal> ema;

    private List<BigDecimal> c;

    private List<BigDecimal> h;

    private List<BigDecimal> l;

    private List<BigDecimal> o;

    private List<Double> v;

    private List<Timestamp> t;

    public EmasDTO(){}

    public EmasDTO(List<BigDecimal> ema,
                   List<BigDecimal> c,
                   List<BigDecimal> h,
                   List<BigDecimal> l,
                   List<BigDecimal> o,
                   List<Double> v,
                   List<Timestamp> t) {
        this.ema = ema;
        this.c = c;
        this.h = h;
        this.l = l;
        this.o = o;
        this.v = v;
        this.l = l;
    }

    public EmaDTO retreiveEma(EmasDTO emaList, int index){
        EmaDTO ema = new EmaDTO(
            emaList.ema.get(emaList.getEma().size() - index),
                emaList.c.get(emaList.getC().size() - index),
                emaList.h.get(emaList.getH().size() - index),
                emaList.l.get(emaList.getL().size() - index),
                emaList.o.get(emaList.getO().size() - index),
                emaList.v.get(emaList.getV().size() - index),
                emaList.t.get(emaList.getT().size() - index)
        );
        return ema;
    }

    public List<BigDecimal> getEma() {
        return ema;
    }

    public void setEma(List<BigDecimal> ema) {
        this.ema = ema;
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

    public List<Timestamp> getT() {
        return t;
    }

    public void setT(List<Timestamp> t) {
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