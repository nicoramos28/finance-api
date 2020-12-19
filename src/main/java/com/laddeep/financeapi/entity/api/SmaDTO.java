package com.laddeep.financeapi.entity.api;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class SmaDTO {

    private List<BigDecimal> sma;

    public SmaDTO(){}

    public SmaDTO(List<BigDecimal> sma) {
        this.sma = sma;
    }

    public List<BigDecimal> getSma() {
        return sma;
    }

    public void setSma(List<BigDecimal> sma) {
        this.sma = sma;
    }

    @Override
    public String toString() {
        return "SmaDTO{" +
                "sma=" + sma +
                '}';
    }
}
