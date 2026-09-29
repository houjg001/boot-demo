package org.example.web.config;

import org.example.grpc.service.UserRpcServiceGrpc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.client.GrpcChannelFactory;

@Configuration
public class RpcStubConfig {

    @Bean
    UserRpcServiceGrpc.UserRpcServiceBlockingStub userRpcServiceBlockingStub(GrpcChannelFactory grpcChannelFactory) {
        return UserRpcServiceGrpc.newBlockingStub(grpcChannelFactory.createChannel("default"));

    }
}
