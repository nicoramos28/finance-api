package com.laddeep.financeapi.controller.api;

import com.laddeep.financeapi.component.StockBean;
import com.laddeep.financeapi.service.EarningService;
import com.laddeep.financeapi.service.TelegramMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

//@Slf4j
//@RestController
public class EarningsController {

    /*
    private EarningService earningService;

    private TelegramMessageService telegramService;

    private StockBean stockBean;


    public EarningsController(
            EarningService earningService,
            TelegramMessageService telegramService,
            StockBean stockBean) {
        this.earningService = earningService;
        this.telegramService = telegramService;
        this.stockBean = stockBean;
    }
    /* EVALUAR SI ES UTIL TENER ESTE ENDPOINT
    @RequestMapping("/earning/quote{quote}")
    public EarningDTO retrieveStockPriceQuote(@PathVariable String quote){
        return null;
    }*/
}
