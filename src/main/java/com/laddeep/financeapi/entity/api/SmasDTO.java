package com.laddeep.financeapi.entity.api;

import lombok.Data;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
public class SmasDTO {

    private List<BigDecimal> sma;

    private List<BigDecimal> c;

    private List<BigDecimal> h;

    private List<BigDecimal> l;

    private List<BigDecimal> o;

    private List<Double> v;

    private List<Timestamp> t;

    public SmasDTO(){}

    public SmasDTO(List<BigDecimal> sma,
                   List<BigDecimal> c,
                   List<BigDecimal> h,
                   List<BigDecimal> l,
                   List<BigDecimal> o,
                   List<Double> v,
                   List<Timestamp> t) {
        this.sma = sma;
        this.c = c;
        this.h = h;
        this.l = l;
        this.o = o;
        this.v = v;
        this.t = t;
    }

    public SmaDTO retreiveSma(SmasDTO smaList, int index){
        SmaDTO sma = new SmaDTO(
                smaList.sma.get(smaList.getSma().size() - index),
                smaList.c.get(smaList.getC().size() - index),
                smaList.h.get(smaList.getH().size() - index),
                smaList.l.get(smaList.getL().size() - index),
                smaList.o.get(smaList.getO().size() - index),
                smaList.v.get(smaList.getV().size() - index),
                smaList.t.get(smaList.getT().size() - index)
        );
        return sma;
    }

    public List<BigDecimal> getSma() {
        return sma;
    }

    public void setSma(List<BigDecimal> sma) {
        this.sma = sma;
    }

    public BigDecimal get(){
        return sma.get(sma.size()-1);
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
        return "SmasDTO{" +
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
