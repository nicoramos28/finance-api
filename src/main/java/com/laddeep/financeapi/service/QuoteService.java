package com.laddeep.financeapi.service;

import com.laddeep.financeapi.config.CacheConfig;
import com.laddeep.financeapi.entity.db.Quote;
import com.laddeep.financeapi.repository.QuoteRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.jvnet.hk2.annotations.Service;
import org.springframework.cache.annotation.Cacheable;

@Slf4j
@Service
public class QuoteService {

    private QuoteRepository quoteRepository;

    public QuoteService(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    public Quote getQuote(String quote){ return quoteRepository.findByQuote(quote); }

    @Cacheable(cacheNames = CacheConfig.QUOTE_CODE_NAMES)
    public List<Quote> getAllQuotes(){ return  quoteRepository.findAll(); }
}
