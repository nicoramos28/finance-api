package com.laddeep.financeapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@SpringBootApplication
@Slf4j
public class FinanceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinanceApiApplication.class, args);
	}

	@Scheduled(fixedRate = 3000L)
	public void financeApiRunner() {
		log.info("Runner started");
		//My logic
		log.info("Runner finished");
	}
}
