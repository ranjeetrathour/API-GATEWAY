package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
public class LoanServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanServiceApplication.class, args);
	}

}

record Loan(String name) {};

@RestController
class LoanController {

	@GetMapping("/loans")
	public List<Loan> getLoans() {
		return List.of(
				new Loan("ram"),
				new Loan("krishna")
		);
	}
}