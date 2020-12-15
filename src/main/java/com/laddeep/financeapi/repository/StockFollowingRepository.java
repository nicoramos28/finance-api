package com.laddeep.financeapi.repository;

import com.laddeep.financeapi.entity.api.StockFollowingDTO;
import com.laddeep.financeapi.entity.db.StockFollowing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockFollowingRepository extends JpaRepository<StockFollowing, StockFollowingDTO> {

    //@Query(value = "SELECT * FROM stock_follow sf WHERE sf.quote = ?",
      //      nativeQuery = true)
    StockFollowing findByQuote(String quote);
}
