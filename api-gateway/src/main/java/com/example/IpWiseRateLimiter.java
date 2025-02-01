package com.example;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class IpWiseRateLimiter extends AbstractGatewayFilterFactory<IpWiseRateLimiter.Config> {
    private final static int REQUEST_LIMIT = 5;
    private final static Duration TIME_WINDOW = Duration.ofMinutes(5);

    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    public IpWiseRateLimiter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                String ip = exchange.getRequest().getLocalAddress().getAddress().toString().substring(1, exchange.getRequest().getLocalAddress().getAddress().toString().length() - 1);
                Bucket bucket = cache.computeIfAbsent(ip, k -> createBucket());
                if (bucket.getAvailableTokens()==0){
                    exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                    return exchange.getResponse().setComplete();
                }
                if (bucket.tryConsume(1)) {
                    System.out.println(bucket.getAvailableTokens());
                    return chain.filter(exchange);
                } else if (bucket.getAvailableTokens() < 1) {
                    exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                    return exchange.getResponse().setComplete();
                } else {
                    return Mono.empty() ;
                }
            }
        };
    }

    private Bucket createBucket() {
        return Bucket4j.builder()
                .addLimit(Bandwidth.classic(REQUEST_LIMIT, Refill.intervally(REQUEST_LIMIT, TIME_WINDOW)))
                .build();
    }

    private String extractIpFromRequest(ServerHttpRequest request) {
        return request.getLocalAddress().getAddress().getHostAddress();
    }


    public static class Config {
        // Add your configuration properties for rate-limiting, if needed
    }

}
