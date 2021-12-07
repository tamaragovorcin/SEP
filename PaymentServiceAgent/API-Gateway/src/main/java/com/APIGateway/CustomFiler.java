package com.APIGateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
/*
@Configuration
public class CustomFiler implements GlobalFilter {
    Logger logger = LoggerFactory.getLogger(CustomFiler.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        logger.info("Pre Filter -> Authorization = " + request.getHeaders().getFirst("Authorization"));

        detectServiceFilter(request.getURI().toString());
        return chain.filter(exchange).then(Mono.fromRunnable(()-> {
           ServerHttpResponse response =  exchange.getResponse();
           logger.info("Post Filter = " + response.getStatusCode());
        }));
    }

    private void detectServiceFilter(String uri) {
        if(uri.contains("/api/paypal")) {
            logger.info("REDIRECTING TO  -> paypal service");
        }
        else if(uri.contains("/api/bankcard")) {
            logger.info("REDIRECTING TO  -> bank card service");
        }
        else if(uri.contains("/api/qr")) {
            logger.info("REDIRECTING TO  -> qr code service");
        }
        else if(uri.contains("/api/bitcoin")) {
            logger.info("REDIRECTING TO  -> bitcoin service");
        }
        else {
            logger.info("REDIRECTING TO  -> unknown");
        }
    }


}*/
