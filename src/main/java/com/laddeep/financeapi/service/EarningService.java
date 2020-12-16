package com.laddeep.financeapi.service;

import com.laddeep.financeapi.component.QuoteBean;
import com.laddeep.financeapi.component.StockBean;
import com.laddeep.financeapi.integrations.finnhub.FinnhubClient;
import com.laddeep.financeapi.integrations.finnhub.api.EarningsCalendar;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
@Slf4j
public class EarningService {

    private QuoteBean quoteBean;

    private StockBean stockBean;

    private FinnhubClient finnhubClient;


    public EarningService(
            QuoteBean quoteBean,
            StockBean stockBean,
            FinnhubClient finnhubClient) {
        this.quoteBean = quoteBean;
        this.stockBean = stockBean;
        this.finnhubClient = finnhubClient;
    }

    public void getDailyEarning(){
        EarningsCalendar earnings = finnhubClient.getEarnings(OffsetDateTime.now(), OffsetDateTime.now());
        earnings.getEarnings().forEach(earning -> {
            log.info("Analyzing earning {} ", earning.getSymbol());
            Long stockId = quoteBean.get(earning.getSymbol()).getId();
            stockBean.saveEarning(earning, stockId);
        });

    }

}

