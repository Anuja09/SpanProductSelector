package com.span.calculators.product_selector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class CalculatorArtifactApplication {

	public static void main(String[] args) {
		applicationContext = SpringApplication.run(CalculatorArtifactApplication.class, args);
	}

	@Autowired
	private static ApplicationContext applicationContext;

}
