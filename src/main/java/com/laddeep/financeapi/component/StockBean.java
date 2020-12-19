package com.laddeep.financeapi.component;

import com.laddeep.financeapi.entity.db.*;
import com.laddeep.financeapi.exceptions.PersistenceException;
import com.laddeep.financeapi.integrations.finnhub.api.Earning;
import com.laddeep.financeapi.integrations.finnhub.api.StockPriceQuote;
import com.laddeep.financeapi.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class StockBean {

    private StockPriceRepository stockPriceRepository;

    private StockFollowingRepository stockFollowingRepository;

    private ValidationBean validationBean;

    private StockEarningRepository earningRepository;

    private StockEmaRepository emaRepository;

    private StockSmaRepository smaRepository;

    public StockBean(
            StockPriceRepository stockPriceRepository,
            ValidationBean validationBean,
            StockFollowingRepository stockFollowingRepository,
            StockEarningRepository earningRepository,
            StockEmaRepository emaRepository,
            StockSmaRepository smaRepository
    ) {
        this.stockPriceRepository = stockPriceRepository;
        this.validationBean = validationBean;
        this.stockFollowingRepository = stockFollowingRepository;
        this.earningRepository = earningRepository;
        this.emaRepository = emaRepository;
        this.smaRepository = smaRepository;
    }

    public void get(Quote quote, StockPriceQuote stockPrices) {
        validationBean.notNull("StockPrices", stockPrices);
        StockPrice stock = stockPriceRepository.findByQuoteIdAndTime(quote.getId(), OffsetDateTime.now());
        try{
            if(stock == null){
                stock = new StockPrice(
                        null,
                        quote.getId(),
                        stockPrices.getC(),
                        stockPrices.getH(),
                        stockPrices.getL(),
                        stockPrices.getO(),
                        stockPrices.getPc(),
                        OffsetDateTime.now()
                );
                log.info("Saving new Stock Price to {}", quote.getQuote());
            }else{
                if(stock.getCurrentPrice().equals(BigDecimal.ZERO)){
                    stock.setCurrentPrice(stockPrices.getC());
                    stock.setHighestPrice(stockPrices.getH());
                    stock.setLowestPrice(stockPrices.getL());
                    stock.setOpenPrice(stockPrices.getO());
                    stock.setPreviousClosePrice(stockPrices.getPc());
                    log.info("Updating Stock Price to {}", quote.getQuote());
                }
                stock.setCurrentPrice(stockPrices.getC());
                stock.setHighestPrice(stockPrices.getH());
                stock.setLowestPrice(stockPrices.getL());
                log.info("Updating Stock Price to {}", quote.getQuote());
            }
            stockPriceRepository.save(stock);
        }catch (PersistenceException e){
            throw new PersistenceException("Error trying to get, update or insert new Stock information");
        }

    }

    public Long getFollow(Quote quote){
        validationBean.notNull("Quote", quote.getQuote());
        StockFollowing stock = stockFollowingRepository.findByQuote(quote.getQuote());
        try{
            if(stock == null){
                stock = new StockFollowing(
                        null,
                        quote.getId(),
                        quote.getQuote(),
                        OffsetDateTime.now()
                );
            }else{
                stock.setLastUpdate(OffsetDateTime.now());
            }
            stockFollowingRepository.save(stock);
            return stock.getId();
        }catch (PersistenceException e){
            throw new PersistenceException("Error trying to get, update or insert new Follow Stock information");
        }
    }

    public void saveEarning(Earning earning, Quote stock){
        validationBean.notNull( "Quote", earning.getSymbol());
        StockEarning stockEarning = earningRepository.findByQuoteId(stock.getId());
        try{
            if(stockEarning == null){
                stockEarning = new StockEarning(
                        null,
                        stock.getId(),
                        OffsetDateTime.now(),
                        earning.getEpsActual(),
                        earning.getEpsEstimate(),
                        earning.getQuarter(),
                        earning.getRevenueActual(),
                        earning.getRevenueEstimate(),
                        1
                );
                earningRepository.save(stockEarning);
                this.getFollow(stock);
                log.info("Saving new earning : {} - date {}", earning.getSymbol(), stockEarning.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
            }else if(stockEarning.getActualRevenue() == 0 && earning.getRevenueActual() != 0){
                stockEarning.setCurrentEps(earning.getEpsActual());
                stockEarning.setActualRevenue(earning.getRevenueActual());
                stockEarning.setDate(OffsetDateTime.now());
            }else if(stockEarning.getEnabled() == 0){
                stockEarning.setDate(OffsetDateTime.now());
                stockEarning.setCurrentEps(earning.getEpsActual());
                stockEarning.setEstimateEps(earning.getEpsEstimate());
                stockEarning.setQuarter(earning.getQuarter());
                stockEarning.setActualRevenue(earning.getRevenueActual());
                stockEarning.setEstimateRevenue(earning.getRevenueEstimate());
                stockEarning.setEnabled(1);
            }
        }catch (PersistenceException e){
            throw new PersistenceException("Error trying to get, update or insert new Earning information");
        }
    }

    public void saveSma(Quote quote, BigDecimal ma, int period, int status){
        validationBean.notNull("Quote", quote.getQuote());
        try{
            StockSma sma = smaRepository.findByQuoteIdAndPeriod(quote.getId(), period);
            if(sma == null){
                sma = new StockSma(
                        null,
                        period,
                        quote.getId(),
                        OffsetDateTime.now(),
                        ma,
                        status
                );
                log.info("Saving new sma value to : {} - date {} - value {}", quote.getQuote(), sma.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE), ma);
            }else{
                sma.setValue(ma);
            }
            smaRepository.save(sma);
        }catch (PersistenceException e){
            throw new PersistenceException("Error trying to get, update or insert new SMA information");
        }
    }

    public void saveEma(Quote quote, BigDecimal ma, int period, int status){
        validationBean.notNull("Quote", quote.getQuote());
        try{
            StockEma ema = emaRepository.findByQuoteIdAndPeriod(quote.getId(), period);
            if(ema == null){
                ema = new StockEma(
                        null,
                        period,
                        quote.getId(),
                        OffsetDateTime.now(),
                        ma,
                        status
                );
                log.info("Saving new sma value to : {} - date {} - value {}", quote.getQuote(), ema.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE), ma);
            }else{
                ema.setValue(ma);
            }
            emaRepository.save(ema);
        }catch (PersistenceException e){
            throw new PersistenceException("Error trying to get, update or insert new SMA information");
        }
    }
}
