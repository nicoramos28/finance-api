package com.laddeep.financeapi.entity.db;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "quote", catalog = "finance_api")
public class Quote {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quote")
    private String quote;

    @Column(name = "last_update")
    private OffsetDateTime lastUpdate;

    public Quote() {
    }

    public Quote(Long id, String quote, OffsetDateTime lastUpdate) {
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
        return "Quote{" +
                "id=" + id +
                ", quote='" + quote + '\'' +
                ", lastUpdate=" + lastUpdate +
                '}';
    }
}
