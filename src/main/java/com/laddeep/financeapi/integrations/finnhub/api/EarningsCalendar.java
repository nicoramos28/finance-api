package com.laddeep.financeapi.integrations.finnhub.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EarningsCalendar {

    @JsonProperty("earningsCalendar")
    private List<Earning> earnings;

    public EarningsCalendar() {
        this.earnings = new ArrayList<>();
    }

    public List<Earning> getEarnings() {
        return earnings;
    }

    public void setEarnings(List<Earning> earnings) {
        this.earnings = earnings;
    }

    @Override
    public String toString() {
        return "EarningsCalendar{" +
                "earnings=" + earnings +
                '}';
    }
}
