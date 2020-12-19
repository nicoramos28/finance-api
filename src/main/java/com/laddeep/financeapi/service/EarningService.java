package com.laddeep.financeapi.service;

import com.laddeep.financeapi.component.QuoteBean;
import com.laddeep.financeapi.component.StockBean;
import com.laddeep.financeapi.entity.db.Quote;
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

    private StockService stockService;


    public EarningService(
            QuoteBean quoteBean,
            StockBean stockBean,
            FinnhubClient finnhubClient,
            StockService stockService) {
        this.quoteBean = quoteBean;
        this.stockBean = stockBean;
        this.finnhubClient = finnhubClient;
        this.stockService = stockService;
    }

    public void getDailyEarning(){
        EarningsCalendar earnings = finnhubClient.getEarnings(OffsetDateTime.now(), OffsetDateTime.now());
        earnings.getEarnings().forEach(earning -> {
            log.info("Analyzing earning {} ", earning.getSymbol());
            Quote stock = quoteBean.get(earning.getSymbol());
            stockBean.saveEarning(earning, stock);
            log.info("Updating earning stock prices");
            stockService.getStockPriceQuote(stock);
        });
    }

}
