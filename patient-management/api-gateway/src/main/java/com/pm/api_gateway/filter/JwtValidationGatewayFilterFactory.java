package com.pm.api_gateway.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class JwtValidationGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
    //JwtValidationGatewayFilterFactory:JwtValidation dong dau co the dat bat ki cai gi,
    // va GatewayFilterFactory la de spring biet day la lop filter va dung duoc trong file yml,
    // cho nen khi goi class nay ben application.yml thi no hop voi filter ben do

    private final WebClient webClient;

    public JwtValidationGatewayFilterFactory(WebClient.Builder webClientBuilder,
                                             @Value("${auth.service.url}") String authServiceUrl) {
        // Khởi tạo WebClient với URL cơ sở được lấy từ cấu hình, ket hop voi
        //return trong ham apply ben duoi de check request di vao co hop le ko.
        this.webClient = webClientBuilder.baseUrl(authServiceUrl).build();

        // Các URL ví dụ:
        // auth-service:4005 (ví dụ về một service name trong môi trường Kubernetes/Docker)
        // ecs.aws.askdjkasdjk:5000 (ví dụ về một endpoint AWS ECS)
    }

    @Override
    public GatewayFilter apply(Object config) {
        // Đây là nơi bạn sẽ triển khai logic lọc của mình.
        // Ví dụ: kiểm tra token JWT trong request.
        return (exchange, chain) -> {
            String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            //thấy jwt bad thi tra ve response luon chu khong can goi them api
            if (token == null || !token.startsWith("Bearer ")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            //kiểm tra xong hết va noi voi spring hay cho request di vo trong
            return webClient.get()
                    .uri("/validate")
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .retrieve()
                    .toBodilessEntity()
                    .then(chain.filter(exchange));
        };
    }
}
