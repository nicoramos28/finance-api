package com.laddeep.financeapi.controller.api;

import com.laddeep.financeapi.api.stockPrice.QuoteDto;
import com.laddeep.financeapi.service.StockPriceService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockPriceController {

    StockPriceService stockPriceService;

    public StockPriceController(
        StockPriceService stockPriceService
    ){
        this.stockPriceService = stockPriceService;
    }

    @RequestMapping("/stock_price/quote{quote}")
    public QuoteDto retrieveStockPriceQuote(@PathVariable String quote){
        return stockPriceService.GetStockPriceQuote(quote);
    }
}
