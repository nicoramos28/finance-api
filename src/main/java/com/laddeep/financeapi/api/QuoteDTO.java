package com.laddeep.financeapi.api;

import lombok.Data;
import org.glassfish.grizzly.http.util.TimeStamp;

@Data
public class QuoteDTO {

    private Long id;

    private String quote;

    private TimeStamp last_update;

    public QuoteDTO(Long id, String quote, TimeStamp last_update) {
        this.id = id;
        this.quote = quote;
        this.last_update = last_update;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public TimeStamp getLast_update() {
        return last_update;
    }

    public void setLast_update(TimeStamp last_update) {
        this.last_update = last_update;
    }

    @Override
    public String toString() {
        return "QuoteDTO{" +
                "id=" + id +
                ", quote='" + quote + '\'' +
                ", last_update=" + last_update +
                '}';
    }
}
