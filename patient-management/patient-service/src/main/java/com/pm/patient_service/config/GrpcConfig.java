package com.pm.patient_service.config;

import com.pm.patient_service.grpc.BillingServiceGrpcClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcConfig {

    @Bean
    public BillingServiceGrpcClient billingServiceGrpcClient(
            @Value("${billing.service.address:localhost}") String serverAddress,
            @Value("${billing.service.grpc.port:9001}") int serverPort) {
        return new BillingServiceGrpcClient(serverAddress, serverPort);
    }
} 