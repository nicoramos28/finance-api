package com.laddeep.financeapi.integrations.finnhub;

import com.laddeep.financeapi.integrations.finnhub.api.StockPriceQuote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class FinnhubClient {

    private static final String URL = "https://finnhub.io";

    private static final String BASE_URL = "/api/v1";

    private static final String TOKEN = "bv9dp1n48v6ujthqrjog";

    public RestTemplate restTemplate;

    FinnhubClient(){
        this.restTemplate = new RestTemplate();
    }

    public <T, R> ResponseEntity<R> get(String url, Class<R> responseType){
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Finnhub-Token", TOKEN);
        HttpEntity<String> entity = new HttpEntity<>("", headers);
        log.info("GET URL Request : {}", url);
        log.info("Headers : {}", headers);
        return this.restTemplate.exchange(url, HttpMethod.GET, entity, responseType);
    }

    /**
     * Stock Price Quoute
     * https://finnhub.io/api/v1/quote?symbol=AAPL&token=
     */
    public StockPriceQuote getStockPriceQuote(String quote){
        ResponseEntity<StockPriceQuote> dto = this.get(URL + BASE_URL + "/quote?symbol=" + quote,
                StockPriceQuote.class);
        log.info("Finnhub response - {} Stock Price: {}",quote, dto);
        if(dto != null){
            return dto.getBody();
        }
        return null;
    }

}

