package org.evolutionsoftware.numberlambda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;

@SpringBootApplication
public class NumberLambdaApplication {

	public static void main(String[] args) {
		SpringApplication.run(NumberLambdaApplication.class, args);
	}

	@Bean
	public Function<String, String> uppercase() {
		return value -> {
			if (value.equals("exception")) {
				throw new RuntimeException("Intentional exception");
			}
			else {
				return value.toUpperCase();
			}
		};
	}
}
