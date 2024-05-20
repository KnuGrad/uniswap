package com.knu.uniswap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class UniswapApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniswapApplication.class, args);
	}

}
