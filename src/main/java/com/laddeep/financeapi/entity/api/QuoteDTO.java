package com.laddeep.financeapi.entity.api;

import lombok.Data;
import java.sql.Timestamp;
import java.time.OffsetDateTime;

@Data
public class QuoteDTO {

    private Long id;

    private String quote;

    private OffsetDateTime lastUpdate;

    public QuoteDTO(Long id, String quote, OffsetDateTime lastUpdate) {
        this.id = id;
        this.quote = quote;
        this.lastUpdate = lastUpdate;
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

    public OffsetDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(OffsetDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return "QuoteDTO{" +
                "id=" + id +
                ", quote='" + quote + '\'' +
                ", lastUpdate=" + lastUpdate +
                '}';
    }
}
