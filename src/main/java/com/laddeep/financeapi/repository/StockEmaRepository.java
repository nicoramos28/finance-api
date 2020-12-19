package com.laddeep.financeapi.repository;

import com.laddeep.financeapi.entity.db.StockEma;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockEmaRepository extends JpaRepository<StockEma, Long> {
}
