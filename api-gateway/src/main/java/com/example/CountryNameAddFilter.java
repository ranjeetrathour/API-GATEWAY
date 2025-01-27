package com.example;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class CountryNameAddFilter extends AbstractGatewayFilterFactory<CountryNameAddFilter.Config> {

    @Override
    public GatewayFilter apply(Config config) {
       return new GatewayFilter() {
           @Override
           public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
               return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                  exchange.getResponse().getHeaders().add("country", "India");
              }));
           }
       };
    }
    
    public static class Config {
	}
}
