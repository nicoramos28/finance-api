package com.laddeep.financeapi.entity.db;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "stock_earning", catalog = "finance_api")
public class StockEarning {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_quote", nullable = false)
    private Long quoteId;

    @Column(name = "date", nullable = false)
    private OffsetDateTime date;

    @Column(name = "eps_current")
    private float currentEps;

    @Column(name = "eps_estimate")
    private float estimateEps;

    @Column(name = "quarter")
    private int quarter;

    @Column(name = "revenue_actual")
    private double actualRevenue;

    @Column(name = "revenue_estimate")
    private double estimateRevenue;

    public StockEarning(){}

    public StockEarning(Long id,
                        Long quoteId,
                        OffsetDateTime date,
                        float currentEps,
                        float estimateEps,
                        int quarter,
                        double actualRevenue,
                        double estimateRevenue) {
        this.id = id;
        this.quoteId = quoteId;
        this.date = date;
        this.currentEps = currentEps;
        this.estimateEps = estimateEps;
        this.quarter = quarter;
        this.actualRevenue = actualRevenue;
        this.estimateRevenue = estimateRevenue;
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

    public OffsetDateTime getDate() {
        return date;
    }

    public void setDate(OffsetDateTime date) {
        this.date = date;
    }

    public float getCurrentEps() {
        return currentEps;
    }

    public void setCurrentEps(float currentEps) {
        this.currentEps = currentEps;
    }

    public float getEstimateEps() {
        return estimateEps;
    }

    public void setEstimateEps(float estimateEps) {
        this.estimateEps = estimateEps;
    }

    public int getQuarter() {
        return quarter;
    }

    public void setQuarter(int quarter) {
        this.quarter = quarter;
    }

    public double getActualRevenue() {
        return actualRevenue;
    }

    public void setActualRevenue(double actualRevenue) {
        this.actualRevenue = actualRevenue;
    }

    public double getEstimateRevenue() {
        return estimateRevenue;
    }

    public void setEstimateRevenue(double estimateRevenue) {
        this.estimateRevenue = estimateRevenue;
    }

    @Override
    public String toString() {
        return "StockEarning{" +
                "id=" + id +
                ", quoteId=" + quoteId +
                ", date=" + date +
                ", currentEps=" + currentEps +
                ", estimateEps=" + estimateEps +
                ", quarter=" + quarter +
                ", actualRevenue=" + actualRevenue +
                ", estimateRevenue=" + estimateRevenue +
                '}';
    }
}
