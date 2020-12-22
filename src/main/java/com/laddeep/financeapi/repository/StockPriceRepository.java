package com.laddeep.financeapi.repository;

import com.laddeep.financeapi.entity.db.StockPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface StockPriceRepository extends JpaRepository<StockPrice, Long> {

    @Query(value = "SELECT * FROM stock_price sp WHERE sp.id_quote = ? and date(sp.date) = date(?)",
            nativeQuery = true)
    StockPrice findByQuoteIdAndTime(Long id, OffsetDateTime time);

    @Query(value = "SELECT * FROM stock_price sp WHERE sp.id_quote = ? order by date desc limit 3",
            nativeQuery = true)
    List<StockPrice> findLastCandles(Long idQuote);
}
