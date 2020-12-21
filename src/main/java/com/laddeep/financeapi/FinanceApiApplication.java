package com.laddeep.financeapi;

import com.laddeep.financeapi.service.EarningService;
import com.laddeep.financeapi.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import java.io.IOException;

@EnableScheduling
@SpringBootApplication
@Slf4j
public class FinanceApiApplication {

	@Autowired
	EarningService earningService;

	@Autowired
	StockService stockService;

	public static void main(String[] args) {
		SpringApplication.run(FinanceApiApplication.class, args);
	}

	@Scheduled(fixedDelay = 200000L)
	public void financeApiRunner() throws IOException, InterruptedException {
		log.info("\n######################################## Runner started ########################################");
		//earningService.getDailyEarning();
		//stockService.geStockEmaValues("AAPL", null);
		//stockService.geStockSmaValues("AAPL", null);
		stockService.technicalMovingAverageAnalytics();
		log.info("\n######################################## Runner finished ########################################\n");
	}
}
