package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@SpringBootApplication
public class BookServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookServiceApplication.class, args);
	}
}

// Record for representing a Book
record Book(String title, String author) {}

// Controller to handle HTTP requests
@RestController
class BookController {

	// Sample endpoint to get a list of books
	@GetMapping("/books")
	public List<Book> getBooks() {
		return List.of(
				new Book("1984", "George Orwell"),
				new Book("To Kill a Mockingbird", "Harper Lee"),
				new Book("The Great Gatsby", "F. Scott Fitzgerald")
		);
	}
}
