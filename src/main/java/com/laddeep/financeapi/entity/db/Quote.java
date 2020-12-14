package com.laddeep.financeapi.entity.db;

import javax.persistence.*;
import java.sql.Timestamp;

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
    private Timestamp lastUpdate;

    public Quote() {
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

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
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
