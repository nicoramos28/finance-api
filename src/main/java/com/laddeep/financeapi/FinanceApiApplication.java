package com.laddeep.financeapi;

import com.laddeep.financeapi.entity.db.Quote;
import com.laddeep.financeapi.service.EarningService;
import com.laddeep.financeapi.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import java.io.IOException;
import java.time.OffsetDateTime;

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

	@Scheduled(fixedRate = 300000L)
	public void financeApiRunner() throws IOException, InterruptedException {
		log.info("\n######################################## Runner started ########################################\n");
		earningService.getDailyEarning();
		Quote quote = new Quote(
				1L,
				"BEKE",
				OffsetDateTime.now()
		);
		stockService.geStockEmaValues(quote, "D", "7");
		log.info("\n######################################## Runner finished ########################################\n");
	}
}
