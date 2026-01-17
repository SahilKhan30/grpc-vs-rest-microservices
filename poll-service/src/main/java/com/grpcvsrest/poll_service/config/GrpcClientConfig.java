package com.grpcvsrest.poll_service.config;

import com.grpcvsrest.user_service.grpc.UserServiceGrpc;
import com.grpcvsrest.vote_service.grpc.VoteServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClientConfig {

    @Bean
    public ManagedChannel userServiceChannel() {
        return ManagedChannelBuilder
                .forAddress("localhost", 9081) // port where user-service runs
                .usePlaintext()
                .build();
    }

    @Bean
    public UserServiceGrpc.UserServiceBlockingStub userServiceStub(@Qualifier("userServiceChannel") ManagedChannel channel) {
        return UserServiceGrpc.newBlockingStub(channel);
    }

    @Bean
    public ManagedChannel voteServiceChannel() {
        return ManagedChannelBuilder
                .forAddress("localhost", 9101) // port where user-service runs
                .usePlaintext()
                .build();
    }

    @Bean
    public VoteServiceGrpc.VoteServiceBlockingStub voteServiceStub(@Qualifier("voteServiceChannel") ManagedChannel channel){
        return VoteServiceGrpc.newBlockingStub(channel);
    }


}