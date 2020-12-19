package com.laddeep.financeapi.service;

import com.laddeep.financeapi.entity.api.StockPriceDTO;
import com.laddeep.financeapi.integrations.telegram.TelegramClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class TelegramMessageService {

    private TelegramClient telegramClient;

    public TelegramMessageService(
            TelegramClient telegramClient
    ){
        this.telegramClient = telegramClient;
    }

    public void notifyStockPriceQuote(String quote, StockPriceDTO quoteData) throws IOException, InterruptedException {
        String message = quote + " prices :\n"
                + "----- Current price : " + quoteData.getCurrentPrice() + "\n"
                + "----- Open price : " + quoteData.getOpenPrice() + "\n"
                + "----- Highest price : " + quoteData.getHighestPrice() + "\n"
                + "----- Lowest price : " + quoteData.getLowestPrice() + "\n"
                + "----- Previous close price : " + quoteData.getPreviousClosePrice() + "";
        telegramClient.sendMessage(message);
    }


}
