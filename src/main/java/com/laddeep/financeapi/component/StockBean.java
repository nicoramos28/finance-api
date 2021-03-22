package com.laddeep.financeapi.component;

import com.laddeep.financeapi.entity.db.*;
import com.laddeep.financeapi.exceptions.PersistenceException;
import com.laddeep.financeapi.integrations.finnhub.api.Earning;
import com.laddeep.financeapi.integrations.finnhub.api.StockPriceQuote;
import com.laddeep.financeapi.repository.*;
import java.util.List;
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

    private QuoteRepository quoteRepository;

    private ExchangeHolidaysRepository holidaysRepository;

    public StockBean(
            StockPriceRepository stockPriceRepository,
            ValidationBean validationBean,
            StockFollowingRepository stockFollowingRepository,
            StockEarningRepository earningRepository,
            StockEmaRepository emaRepository,
            StockSmaRepository smaRepository,
            QuoteRepository quoteRepository,
            ExchangeHolidaysRepository holidaysRepository
    ) {
        this.stockPriceRepository = stockPriceRepository;
        this.validationBean = validationBean;
        this.stockFollowingRepository = stockFollowingRepository;
        this.earningRepository = earningRepository;
        this.emaRepository = emaRepository;
        this.smaRepository = smaRepository;
        this.quoteRepository = quoteRepository;
        this.holidaysRepository = holidaysRepository;
    }

    /**
     * Find in saved information the current bean. To make the correct search, a ticker is needed
     * @param ticker
     * @return
     */
    public Quote get(String ticker){
        Quote quote;
        validationBean.notNull("Quote", ticker);
        try{
            quote = quoteRepository.findByQuote(ticker);
            if(quote == null){
                quote = new Quote(null,ticker,OffsetDateTime.now());
            }else{
                quote.setLastUpdate(OffsetDateTime.now());
            }
            quoteRepository.save(quote);
        }catch (PersistenceException e){
            throw new PersistenceException("Error trying to get saved stock information");
        }
        return quote;
    }

    public Boolean isHolidays(OffsetDateTime today){
        StockExchangeHoliday holiday = holidaysRepository.findByDate(today.format(DateTimeFormatter.ISO_LOCAL_DATE));
        return holiday != null;
    }

    public void saveStockPrice(Quote quote, StockPriceQuote stockPrices) {
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
                        BigDecimal.ZERO,
                        stockPrices.getPc(),
                        OffsetDateTime.now(),
                        0
                );
                log.info("Saving new Stock Price to {}", quote.getQuote());
            }else{
                stock.setCurrentPrice(stockPrices.getC());
                stock.setHighestPrice(stockPrices.getH());
                stock.setLowestPrice(stockPrices.getL());
                stock.setOpenPrice(stockPrices.getO());
                stock.setPreviousClosePrice(stockPrices.getPc());
                log.info("Updating Stock Price to {}", quote.getQuote());
            }
            stockPriceRepository.save(stock);
        }catch (PersistenceException e){
            throw new PersistenceException("Error trying to get, update or insert new Stock information");
        }
    }

    public StockPrice getStockPrice(Long quoteId, OffsetDateTime date){
        try{
            StockPrice stock = stockPriceRepository.findByQuoteIdAndTime(quoteId, date);
            if(stock != null){
                return  stock;
            }
        }catch (PersistenceException e){
            throw new PersistenceException("Error trying to get Stock information");
        }
        return null;
    }

    public void saveStockCandle(Long quoteId, OffsetDateTime date, StockPrice stockToSave){
        try{
            StockPrice stock = stockPriceRepository.findByQuoteIdAndTime(quoteId, date);
            if(stock != null){
                if(stock.getCurrentPrice().compareTo(BigDecimal.ZERO) != 0
                        && stockToSave.getCurrentPrice().compareTo(BigDecimal.ZERO) == 0){
                    stockToSave.setCurrentPrice(stock.getCurrentPrice());
                }
                if(stock.getClosePrice().compareTo(BigDecimal.ZERO) != 0
                        && stockToSave.getClosePrice().compareTo(BigDecimal.ZERO) == 0){
                    stockToSave.setClosePrice(stock.getClosePrice());
                }
                if(stock.getPreviousClosePrice().compareTo(BigDecimal.ZERO) != 0
                        && stockToSave.getPreviousClosePrice().compareTo(BigDecimal.ZERO) == 0){
                    stockToSave.setPreviousClosePrice(stock.getPreviousClosePrice());
                }
                stockToSave.setId(stock.getId());
            }
            stockPriceRepository.save(stockToSave);
        }catch (PersistenceException e){
            throw new PersistenceException("Error trying to get Stock information");
        }
    }

    public Long saveStockToFollow(Quote quote){
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
                this.saveStockToFollow(stock);
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

    public void saveSma(Quote quote, BigDecimal ma, int period, int status, OffsetDateTime date){
        validationBean.notNull("Quote", quote.getQuote());
        try{
            StockSma sma = smaRepository.findByQuoteIdAndPeriodAndDate(quote.getId(), period, date);
            if(sma == null){
                sma = new StockSma(
                        null,
                        period,
                        quote.getId(),
                        date,
                        ma,
                        status
                );
                log.info("Saving new sma value to : {} - date {} - value {}", quote.getQuote(), sma.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE), ma);
            }else{
                sma.setValue(ma);
                sma.setStatus(status);
            }
            smaRepository.save(sma);
        }catch (PersistenceException e){
            throw new PersistenceException("Error trying to get, update or insert new SMA information");
        }
    }

    public void saveEma(Quote quote, BigDecimal ma, int period, int status, OffsetDateTime date){
        validationBean.notNull("Quote", quote.getQuote());
        try{
            StockEma ema = emaRepository.findByQuoteIdAndPeriodAndDate(quote.getId(), period, date);
            if(ema == null){
                ema = new StockEma(
                        null,
                        period,
                        quote.getId(),
                        date,
                        ma,
                        status
                );
                log.info("Saving new sma value to : {} - date {} - value {}", quote.getQuote(), ema.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE), ma);
            }else{
                ema.setValue(ma);
                ema.setStatus(status);
            }
            emaRepository.save(ema);
        }catch (PersistenceException e){
            throw new PersistenceException("Error trying to get, update or insert new SMA information");
        }
    }

    public void saveCandle(Quote quote, StockPrice candle, OffsetDateTime date){
        validationBean.notNull("StockPrices", candle);
        date = (date == null)? OffsetDateTime.now() : date;
        StockPrice stock = stockPriceRepository.findByQuoteIdAndTime(quote.getId(), date);
        try{
            if(stock == null){
                stock = new StockPrice(
                        null,
                        quote.getId(),
                        candle.getCurrentPrice(),
                        candle.getHighestPrice(),
                        candle.getLowestPrice(),
                        candle.getOpenPrice(),
                        candle.getClosePrice(),
                        candle.getPreviousClosePrice(),
                        candle.getTime(),
                        candle.getVolumen()
                );
                log.info("Saving new Candle Price to {} - date {}", quote.getQuote(), date);
            }else{
                stock.setClosePrice(candle.getPreviousClosePrice());
                stock.setHighestPrice(candle.getHighestPrice());
                stock.setClosePrice(candle.getClosePrice());
                stock.setLowestPrice(candle.getLowestPrice());
                stock.setOpenPrice(candle.getOpenPrice());
                stock.setVolumen(candle.getVolumen());
                log.info("Updating Candle Price to {}", quote.getQuote());
            }
            stockPriceRepository.save(stock);
        }catch (PersistenceException e){
            throw new PersistenceException("Error trying to get, update or insert new Candle information");
        }
    }

}
