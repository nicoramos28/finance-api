package com.laddeep.financeapi.entity.db;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "stock_following", catalog = "finance_api")
public class StockFollowing {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_quote")
    private Long quoteId;

    @Column(name = "quote")
    private String quote;

    @Column(name = "last_update")
    private OffsetDateTime lastUpdate;

    public StockFollowing(Long id, Long quoteId, String quote, OffsetDateTime lastUpdate) {
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
        return "StockFollowing{" +
                "id=" + id +
                ", quoteId=" + quoteId +
                ", quote='" + quote + '\'' +
                ", lastUpdate=" + lastUpdate +
                '}';
    }
}
