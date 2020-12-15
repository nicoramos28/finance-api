package com.laddeep.financeapi.controller.api;

import com.laddeep.financeapi.api.stockPrice.StockPriceDTO;
import com.laddeep.financeapi.component.QuoteBean;
import com.laddeep.financeapi.entity.db.Quote;
import com.laddeep.financeapi.service.StockService;
import com.laddeep.financeapi.service.TelegramMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;

@Slf4j
@RestController
public class StockPriceController {

    private StockService stockService;

    private TelegramMessageService telegramService;

    private QuoteBean quoteBean;

    public StockPriceController(
            StockService stockService,
            TelegramMessageService telegramService,
            QuoteBean quoteBean
    ){
        this.stockService = stockService;
        this.telegramService = telegramService;
        this.quoteBean = quoteBean;
    }

    @RequestMapping("/stock_price/quote{quote}")
    public StockPriceDTO retrieveStockPriceQuote(@PathVariable String quote){
        try {
            Quote ticker = quoteBean.get(quote);
            StockPriceDTO response = stockService.getStockPriceQuote(ticker);
            telegramService.notifyStockPriceQuote(quote, response);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/stock_follow/quote{quote}")
    public Long saveStockToFollow(@PathVariable String quote){
        Quote ticker = quoteBean.get(quote);
        return stockService.saveStockToFollow(ticker);
    }
}