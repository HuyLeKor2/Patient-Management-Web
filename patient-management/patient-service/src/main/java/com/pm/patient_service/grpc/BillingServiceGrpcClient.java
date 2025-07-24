package com.pm.patient_service.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BillingServiceGrpcClient {
    private static final Logger log = LoggerFactory.getLogger(BillingServiceGrpcClient.class);
    private final BillingServiceGrpc.BillingServiceBlockingStub blockingStub;

    //localhost:9001/BillingService/CreatePatient Account
    //sau khi deploy len thi duong dan se thay doi, bang cach truyen serverAdress = aws.grpc va serverPort= 123123
    // aws.grpc:123123/BillingService/CreatePatientAccount
    public BillingServiceGrpcClient(
            // Các giá trị được inject từ application.properties hoặc application.yml
            // ${...} là placeholder cho thuộc tính
            @Value("${billing.service.address:localhost}") String serverAddress, @Value("${billing.service.grpc.port:9001}") int serverPort) {
        log.info("connect to BillingServer GRPC service at {}:{}", serverAddress, serverPort);

        //bạn sẽ khởi tạo gRPC channel và stub
        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverAddress, serverPort).usePlaintext() // Hoặc useTransportSecurity()
                .build();
        blockingStub = BillingServiceGrpc.newBlockingStub(channel);
    }

    public BillingResponse createBillingAccount(String patientID, String name, String email) {
        BillingRequest request = BillingRequest.newBuilder()
                .setPatientId(patientID)
                .setName(name)
                .setEmail(email)
                .build();

        BillingResponse response = blockingStub.createBillingAccount(request);
        log.info("Received response from service via GRPC: accountID: {}", response);
        return response;
    }
}
