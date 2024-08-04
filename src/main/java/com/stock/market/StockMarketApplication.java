package com.stock.market;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class StockMarketApplication implements CommandLineRunner {

	@Value("${market.name}")
	private String readCloudConfig;

	public static void main(String[] args) {
		SpringApplication.run(StockMarketApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Read Cloud Config : " + readCloudConfig);
	}
}
