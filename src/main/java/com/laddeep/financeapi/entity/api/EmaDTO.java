package com.laddeep.financeapi.entity.api;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class EmaDTO {

    private List<BigDecimal> ema;

    public EmaDTO(){}

    public EmaDTO(List<BigDecimal> ema) {
        this.ema = ema;
    }

    public List<BigDecimal> getEma() {
        return ema;
    }

    public void setEma(List<BigDecimal> ema) {
        this.ema = ema;
    }

    public BigDecimal get(){
        return ema.get(ema.size()-1);
    }
    @Override
    public String toString() {
        return "EmaDTO{" +
                "ema=" + ema +
                '}';
    }
}