package com.laddeep.financeapi.controller.api;

import com.laddeep.financeapi.component.QuoteBean;
import com.laddeep.financeapi.service.EarningService;
import com.laddeep.financeapi.service.TelegramMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class EarningsController {

    private EarningService earningService;

    private TelegramMessageService telegramService;

    private QuoteBean quoteBean;


    public EarningsController(
            EarningService earningService,
            TelegramMessageService telegramService,
            QuoteBean quoteBean) {
        this.earningService = earningService;
        this.telegramService = telegramService;
        this.quoteBean = quoteBean;
    }
    /* EVALUAR SI ES UTIL TENER ESTE ENDPOINT
    @RequestMapping("/earning/quote{quote}")
    public EarningDTO retrieveStockPriceQuote(@PathVariable String quote){
        return null;
    }*/
}
