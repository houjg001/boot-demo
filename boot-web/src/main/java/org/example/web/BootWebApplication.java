package org.example.web;

import com.example.grpc.proto.HelloRequest;
import com.example.grpc.proto.HelloWorldGrpc;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.grpc.client.ImportGrpcClients;

@SpringBootApplication
@ImportGrpcClients
public class BootWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootWebApplication.class, args);
    }

    /*@Bean
    public CommandLineRunner runner(HelloWorldGrpc.HelloWorldBlockingStub stub) {
        return args -> {
            System.out.println(stub.sayHello(HelloRequest.newBuilder().setName("grpc").build()));
        };
    }*/
}
