package com.laddeep.financeapi.integrations.finnhub;

import com.laddeep.financeapi.entity.api.EmaDTO;
import com.laddeep.financeapi.exceptions.BadRequestException;
import com.laddeep.financeapi.integrations.finnhub.api.EarningsCalendar;
import com.laddeep.financeapi.integrations.finnhub.api.StockPriceQuote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


@Slf4j
@Component
public class FinnhubClient {

    @Value("${finnhub.url}")
    private String URL;

    @Value("${finnhub.base.url}")
    private String BASE_URL;

    @Value("${finnhub.token}")
    private String TOKEN;

    public RestTemplate restTemplate;

    FinnhubClient(){
        this.restTemplate = new RestTemplate();
    }

    public <R> ResponseEntity<R> get(String url, Class<R> responseType){
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Finnhub-Token", TOKEN);
        HttpEntity<String> entity = new HttpEntity<>("", headers);
        log.info("GET URL Request : {}", url);
        log.info("Headers : {}", headers);
        ResponseEntity<R> response = this.restTemplate.exchange(url, HttpMethod.GET, entity, responseType);
        log.info("Finnhub api response : {}", response);
        return response;
    }

    /**
     * Stock Price Quoute
     * https://finnhub.io/api/v1/quote?symbol=AAPL&token=
     * @param quote
     * @return stockPriceQuote
     */
    public StockPriceQuote getStockPriceQuote(String quote){
        try{
            ResponseEntity<StockPriceQuote> dto = this.get(URL + BASE_URL + "/quote?symbol=" + quote,
                    StockPriceQuote.class);
            log.info("Finnhub response - {} Stock Price: {}",quote, dto);
            if(dto != null){
                return dto.getBody();
            }
        }catch (BadRequestException e){
            throw new BadRequestException(e.getMessage());
        }
        return null;
    }

    /**
     * Earnings Calendar
     * https://finnhub.io/api/v1/calendar/earnings?from=2020-03-12&to=2020-03-15
     * @param fromDate
     * @param toDate
     * @return earningsCalendar
     */
    public EarningsCalendar getEarnings(OffsetDateTime fromDate, OffsetDateTime toDate){
        String from = fromDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        String to = toDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        log.info("Getting earnings from : {} - to : {}", from, to);
        try{
            ResponseEntity<EarningsCalendar> earnings = this.get(URL + BASE_URL
                    + "/calendar/earnings?from=" + from + "&to=" + to, EarningsCalendar.class);

            if(earnings.getBody() != null){
                return earnings.getBody();
            }
        }catch (BadRequestException e){
            throw new BadRequestException(e.getMessage());
        }
        return null;
    }

    /**
     * Technical Indicators
     * https://finnhub.io/api/v1/indicator?symbol=AAPL&resolution=D&from=1583098857&to=1584308457&indicator=sma&timeperiod=3
     * @param quote
     * @param resolution
     * @param toDate
     * @param indicator
     * @param timePeriod
     * @return stockEma
     */
    public EmaDTO getMovingAverage(String quote, String resolution, OffsetDateTime toDate, String indicator, String timePeriod){
        String from = toDate.minus(2, ChronoUnit.MONTHS).format(DateTimeFormatter.ISO_LOCAL_DATE);
        String to = toDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        log.info("Getting EMA values");
        try{
            ResponseEntity<EmaDTO> emaValues = this.get(
                    URL + BASE_URL + "/indicator?symbol=" + quote + "&resolution="
                            + resolution + "&from=" + from + "&to=" + to + "&indicator=" + indicator + "&timeperiod=" + timePeriod,
                    EmaDTO.class
            );
            if(emaValues.getBody() != null){
                return emaValues.getBody();
            }
        }catch (BadRequestException e){
            throw new BadRequestException(e.getMessage());
        }
        return null;
    }
}

