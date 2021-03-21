package com.laddeep.financeapi.controller.api;

import com.laddeep.financeapi.component.StockBean;
import com.laddeep.financeapi.entity.api.StockPriceDTO;
import com.laddeep.financeapi.entity.db.Quote;
import com.laddeep.financeapi.exceptions.NotFoundException;
import com.laddeep.financeapi.service.StockService;
import com.laddeep.financeapi.service.TelegramMessageService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;

@Slf4j
@RestController
public class StockController {

    private StockService stockService;

    private TelegramMessageService telegramService;

    private StockBean stockBean;

    public StockController(
            StockService stockService,
            TelegramMessageService telegramService,
            StockBean stockBean
    ){
        this.stockService = stockService;
        this.telegramService = telegramService;
        this.stockBean = stockBean;
    }

    @RequestMapping("/stock_price/{quote}")
    public StockPriceDTO retrieveStockPrice(@PathVariable String quote){
        try {
            Quote ticker = stockBean.get(quote);
            StockPriceDTO response = stockService.getStockPrice(ticker);
            telegramService.notifyStockPriceQuote(quote, response);
            return response;
        } catch (IOException | InterruptedException e) {
            try {
                telegramService.notifyThreadException("Quote '" + quote + "' cannot be find");
            } catch (IOException | InterruptedException ioException) {
                log.info("---> xxx The Quote cannot be found and there was a problem send telegram exception xxx <---");
            }
            throw new NotFoundException(e.getMessage());
        }
    }

    @RequestMapping(value = "/stock_follow/quote{quote}", method = RequestMethod.POST)
    public Long saveStockToFollow(@PathVariable String quote){
        Quote ticker = stockBean.get(quote);
        return stockService.saveStockToFollow(ticker);
    }

    @RequestMapping(value = "/two_candles", method = RequestMethod.POST)
    public void runTwoCandlesStrategyQuotes(){
        Thread twoCandlesAnalyticsThread = new Thread(()->{
            stockService.getQuotesInTwoCandlesStrategy();
        });
        twoCandlesAnalyticsThread.start();
    }


}