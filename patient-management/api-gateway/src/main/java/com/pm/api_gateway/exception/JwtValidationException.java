package com.pm.api_gateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class JwtValidationException {
    //khi ma auth-service tha loi 401 unauthorized thi api-gateway ko hieu
    //no tra ve loi 500, chung ta nen sua bang cach nay api se tra ve loi 401
    // khien cho api clean hon
    @ExceptionHandler(WebClientResponseException. Unauthorized.class)
    public Mono<Void> handleUnauthorizedException(ServerWebExchange exchange){
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
}
