package com.laddeep.financeapi.repository;

import com.laddeep.financeapi.entity.api.StockFollowingDTO;
import com.laddeep.financeapi.entity.db.StockFollowing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockFollowingRepository extends JpaRepository<StockFollowing, StockFollowingDTO> {

    StockFollowing findByQuote(String quote);
}
