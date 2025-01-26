package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

}

//@Configuration
//class GatewayConfiguration {
//	@Bean
//	public RouteLocator routeLocator(RouteLocatorBuilder builder) {
//		return builder.routes()
//				.route("book-service", r -> r.path("/books/**") // Path predicate
//						.uri("http://localhost:8081")) // Destination URI
//				.build();
//	}
//}
