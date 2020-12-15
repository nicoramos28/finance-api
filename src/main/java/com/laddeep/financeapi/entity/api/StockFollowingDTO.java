package com.laddeep.financeapi.entity.api;

import lombok.Data;
import java.time.OffsetDateTime;

@Data
public class StockFollowingDTO {

    private Long id;

    private Long quoteId;

    private String quote;

    private OffsetDateTime lastUpdate;

    public StockFollowingDTO(Long id, Long quoteId, String quote, OffsetDateTime lastUpdate) {
        this.id = id;
        this.quoteId = quoteId;
        this.quote = quote;
        this.lastUpdate = lastUpdate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(Long quoteId) {
        this.quoteId = quoteId;
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
        return "StockFollowingDTO{" +
                "id=" + id +
                ", quoteId=" + quoteId +
                ", quote='" + quote + '\'' +
                ", lastUpdate=" + lastUpdate +
                '}';
    }
}
