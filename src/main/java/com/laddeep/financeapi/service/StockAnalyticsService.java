package com.laddeep.financeapi.service;

import com.laddeep.financeapi.component.QuoteBean;
import com.laddeep.financeapi.entity.db.Quote;
import com.laddeep.financeapi.entity.db.StockPrice;
import com.laddeep.financeapi.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class StockAnalyticsService {

    private TelegramMessageService messageService;

    private StockEmaRepository emaRepository;

    private StockSmaRepository smaRepository;

    private StockEarningRepository earningRepository;

    private StockPriceRepository stockPriceRepository;

    private ExchangeHolidaysRepository holidaysRepository;

    private QuoteRepository quoteRepository;

    private QuoteBean quoteBean;

    public StockAnalyticsService(TelegramMessageService messageService,
                                 StockEmaRepository emaRepository,
                                 StockSmaRepository smaRepository,
                                 StockEarningRepository earningRepository,
                                 StockPriceRepository stockPriceRepository,
                                 ExchangeHolidaysRepository holidaysRepository,
                                 QuoteRepository quoteRepository) {
        this.messageService = messageService;
        this.emaRepository = emaRepository;
        this.smaRepository = smaRepository;
        this.earningRepository = earningRepository;
        this.stockPriceRepository = stockPriceRepository;
        this.holidaysRepository = holidaysRepository;
        this.quoteRepository = quoteRepository;
    }

    public void StockAnalyticsSignalAndConfirmation(String ticker){
        log.info("\n######################################## Start Signal and Confirmation Analytics ########################################");
        //Getting candles to analyze
        Quote quote = quoteBean.get(ticker);
        List<StockPrice> candles = stockPriceRepository.findLastCandles(quote.getId());
        StockPrice candle1 = candles.get(1);
        StockPrice candle2 = candles.get(2);
        StockPrice candle3 = candles.get(3);
        //getting Moving Average positions
    }


}
