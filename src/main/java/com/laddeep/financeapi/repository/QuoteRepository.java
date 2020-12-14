package com.laddeep.financeapi.repository;

import com.laddeep.financeapi.entity.db.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuoteRepository extends JpaRepository<Quote, Long> {

    @Query(value = "SELECT * FROM quote q WHERE q.quote = ?",
           nativeQuery = true)
    Quote findByQuote(String quote);
}
