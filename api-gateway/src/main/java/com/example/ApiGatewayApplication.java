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

@Configuration
class GatewayConfiguration {

    private final CountryNameAddFilter countryNameAddFilter;

    public GatewayConfiguration(CountryNameAddFilter countryNameAddFilter) {
        this.countryNameAddFilter = countryNameAddFilter;
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("book-service", r -> r.path("/books/**") // Path predicate
                        .filters(f -> f.filter(new IpWiseRateLimiter().apply(new IpWiseRateLimiter.Config()))
                                .filter(new CountryNameAddFilter().apply(new CountryNameAddFilter.Config()))
                        ) // Apply custom filter
                        .uri("http://localhost:8081")) // Destination URI
                .route("author-service", r -> r.path("/authors/**")
                        .uri("http://localhost:8081")) // Path predicate
                .route("loan-service", r -> r.path("/loans/**")
                        .uri("http://localhost:8082"))
                .build();
    }
}
