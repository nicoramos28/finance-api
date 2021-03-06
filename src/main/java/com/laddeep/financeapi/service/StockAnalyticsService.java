package com.laddeep.financeapi.service;

import com.laddeep.financeapi.component.StockBean;
import com.laddeep.financeapi.entity.api.EarningDTO;
import com.laddeep.financeapi.entity.db.Quote;
import com.laddeep.financeapi.entity.db.StockEma;
import com.laddeep.financeapi.entity.db.StockPrice;
import com.laddeep.financeapi.entity.db.StockSma;
import com.laddeep.financeapi.exceptions.ThreadException;
import com.laddeep.financeapi.integrations.finnhub.api.EarningsCalendar;
import com.laddeep.financeapi.repository.*;
import com.laddeep.financeapi.util.CandleUtil;
import com.laddeep.financeapi.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
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

    private StockBean stockBean;

    private CandleUtil candleUtil;

    private DateUtil dateUtil;

    private List<String> tickerList = new ArrayList<>();

    public StockAnalyticsService(TelegramMessageService messageService,
                                 StockEmaRepository emaRepository,
                                 StockSmaRepository smaRepository,
                                 StockEarningRepository earningRepository,
                                 StockPriceRepository stockPriceRepository,
                                 ExchangeHolidaysRepository holidaysRepository,
                                 QuoteRepository quoteRepository,
                                 StockBean stockBean,
                                 CandleUtil candleUtil,
                                 DateUtil dateUtil) {
        this.messageService = messageService;
        this.emaRepository = emaRepository;
        this.smaRepository = smaRepository;
        this.earningRepository = earningRepository;
        this.stockPriceRepository = stockPriceRepository;
        this.holidaysRepository = holidaysRepository;
        this.quoteRepository = quoteRepository;
        this.stockBean = stockBean;
        this.candleUtil = candleUtil;
        this.dateUtil = dateUtil;
    }

    /**
     *
     * @param ticker
     *
     * Analytics consist in :
     * 1 - Two candles of the same type (notification in a list)
     * 2 - Two candles same position on their moving averages (notification of signal)
     * 3 - Third candle different position on moving averages (notification of confirmation)
     */
    public void stockAnalyticsSignalAndConfirmation(String ticker){
        log.info("## ## ## ## ## ## Start Signal and Confirmation Analytics ## ## ## ## ## ##");
        Boolean lastTicker = ticker.equals("STOR");

        Quote quote = this.stockBean.get(ticker);

        List<StockPrice> candles = stockPriceRepository.findLastCandles(quote.getId());
        List<StockEma> emas7 = emaRepository.findLastEmaValues(quote.getId(), 7);
        List<StockEma> emas30 = emaRepository.findLastEmaValues(quote.getId(), 30);
        List<StockSma> smas200 = smaRepository.findLastSmaValues(quote.getId(), 200);

        //Candle 1 - Information
        StockPrice candle1 = candles.get(0);
        StockEma ema7_1 = emas7.get(0);
        StockEma ema30_1 = emas30.get(0);
        StockSma sma200_1 = smas200.get(0);
        int c1Type = candleUtil.getCandleType(candle1);

        //Candle 2 - Information
        StockPrice candle2 = candles.get(1);
        StockEma ema7_2 = emas7.get(1);
        StockEma ema30_2 = emas30.get(1);
        StockSma sma200_2 = smas200.get(1);
        int c2Type = candleUtil.getCandleType(candle2);

        //Candle 3 - Information
        StockPrice candle3 = candles.get(2);
        StockEma ema7_3 = emas7.get(2);
        StockEma ema30_3 = emas30.get(2);
        StockSma sma200_3 = smas200.get(2);
        int c3Type = candleUtil.getCandleType(candle3);

        if(c1Type == c2Type){
            if(!lastTicker){
                tickerList.add(ticker);
            }else{
                try {
                    messageService.notifyTwoCandles(tickerList);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if((ema7_1.getStatus() == ema30_1.getStatus()) && (ema7_2.getStatus() == ema30_2.getStatus()) && (ema7_1.getStatus() == ema7_2.getStatus()) && (ema30_1.getStatus() == ema30_2.getStatus())){
                if(ema7_1.getStatus() != ema7_3.getStatus()){
                    if(!(candleUtil.findCandlesGap(candle1, candle2)) && !(candleUtil.findCandlesGap(candle2, candle3))){
                        try {
                            messageService
                                    .notifySignalConfirmationStrategy(ticker, candle1, candle2, ema7_1, ema30_1, sma200_1);
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                            try {
                                throw new ThreadException(e.getMessage());
                            } catch (IOException | InterruptedException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        log.info("## ## ## ## ## ## End Signal and Confirmation Analytics ## ## ## ## ## ##");
    }

    /**
     *
     * Earnings analysis. Earnings that reflect an expected move of 10% or more will be notified.
     * Earning estimate : earning.epsEstimate
     * Earning result : earning.epsActual
     * Earning projection % = (StockPrice*100)/epsEstimate
     * Required @param earning
     *
     */
    public void earningsAnalyticsService(EarningsCalendar earnings){
        List<EarningDTO> earningList = new ArrayList<>();
        earnings.getEarnings().forEach(earning -> {
            Quote stockData = this.stockBean.get(earning.getSymbol());
            StockPrice stock = stockPriceRepository.findByQuoteIdAndTime(stockData.getId(), OffsetDateTime.now());
            if((stock != null) && (stock.getCurrentPrice().compareTo(BigDecimal.ZERO) !=0)){
                log.info("NEW Earning coming up to {}, estimate eps : {}", earning.getSymbol(), earning.getEpsEstimate());
                earningList.add(new EarningDTO(
                        earning.getEpsActual(),
                        earning.getEpsEstimate(),
                        earning.getHour(),
                        earning.getRevenueActual(),
                        earning.getRevenueEstimate(),
                        earning.getSymbol(),
                        stock.getCurrentPrice()
                ));
            }
        });
        if(!earningList.isEmpty()){
            try {
                messageService.notifyDailyEarning(earningList);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
