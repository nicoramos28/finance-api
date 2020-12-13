package com.laddeep.financeapi.controller.api;

import com.laddeep.financeapi.api.stockPrice.QuoteDto;
import com.laddeep.financeapi.service.StockPriceService;
import com.laddeep.financeapi.service.TelegramMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
public class StockPriceController {

    private StockPriceService stockPriceService;

    private TelegramMessageService telegramService;

    public StockPriceController(
        StockPriceService stockPriceService,
        TelegramMessageService telegramService
    ){
        this.stockPriceService = stockPriceService;
        this.telegramService = telegramService;
    }

    @RequestMapping("/stock_price/quote{quote}")
    public QuoteDto retrieveStockPriceQuote(@PathVariable String quote){
        QuoteDto response = stockPriceService.GetStockPriceQuote(quote);
        try {
            telegramService.notifyStockPriceQuote(quote, response);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }
}
