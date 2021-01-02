package com.laddeep.financeapi.service;

import com.laddeep.financeapi.component.QuoteBean;
import com.laddeep.financeapi.component.StockBean;
import com.laddeep.financeapi.entity.db.Quote;
import com.laddeep.financeapi.integrations.finnhub.FinnhubClient;
import com.laddeep.financeapi.integrations.finnhub.api.EarningsCalendar;
import com.laddeep.financeapi.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
public class EarningService {

    private QuoteBean quoteBean;

    private StockBean stockBean;

    private FinnhubClient finnhubClient;

    private StockService stockService;

    private StockAnalyticsService analyticsService;

    private DateUtil dateUtil;


    public EarningService(
            QuoteBean quoteBean,
            StockBean stockBean,
            FinnhubClient finnhubClient,
            StockService stockService,
            StockAnalyticsService analyticsService,
            DateUtil dateUtil) {
        this.quoteBean = quoteBean;
        this.stockBean = stockBean;
        this.finnhubClient = finnhubClient;
        this.stockService = stockService;
        this.analyticsService = analyticsService;
        this.dateUtil = dateUtil;
    }

    public void getEarnings(){
        OffsetDateTime day = OffsetDateTime.now();
        while(dateUtil.isWeekend(day) || quoteBean.isHolidays(day)){
            if(dateUtil.isSaturday(day) || quoteBean.isHolidays(day)) {
                day = day.minus(1, ChronoUnit.DAYS);
            }else if(dateUtil.isSunday(day)){
                day = day.minus(2, ChronoUnit.DAYS);
            }
        }
        EarningsCalendar earnings = finnhubClient.getEarnings(day, day);
        earnings.getEarnings().forEach(earning -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("Analyzing earning {} ", earning.getSymbol());
            Quote stock = quoteBean.get(earning.getSymbol());
            stockBean.saveEarning(earning, stock);
            log.info("Updating earning stock prices");
            stockService.getStockPriceQuote(stock);
        });
        analyticsService.earningsAnalyticsService(earnings);

    }


}
