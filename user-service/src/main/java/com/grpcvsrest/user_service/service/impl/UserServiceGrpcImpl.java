package com.grpcvsrest.user_service.service.impl;

import com.google.protobuf.Timestamp;
import com.grpcvsrest.user_service.grpc.Gender;
import com.grpcvsrest.user_service.grpc.UserResponse;

import com.grpcvsrest.user_service.grpc.GetUserByIdRequest;

import com.grpcvsrest.user_service.grpc.UserServiceGrpc;
import com.grpcvsrest.user_service.repository.UserRepository;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@RequiredArgsConstructor
@GrpcService
public class UserServiceGrpcImpl extends UserServiceGrpc.UserServiceImplBase {

    private final UserRepository userRepository;

    private Timestamp toProtoTimestamp(LocalDateTime time) {
        if (time == null) return Timestamp.getDefaultInstance();
        return Timestamp.newBuilder()
                .setSeconds(time.toEpochSecond(ZoneOffset.UTC))
                .setNanos(time.getNano())
                .build();
    }

    @Override
    public void getUserById(GetUserByIdRequest request, StreamObserver<UserResponse> responseObserver) {
        var user = userRepository.findByUuid(UUID.fromString(request.getId())).orElse(null);

        UserResponse response;
        if (user != null) {
            response = UserResponse.newBuilder()
                    .setId(user.getUuid().toString())
                    .setFirstName(user.getFirstName())
                    .setLastName(user.getLastName())
                    .setEmail(user.getEmail())
                    .setAge(user.getAge() != null ? user.getAge() : 0)
                    .setGender(Gender.valueOf(user.getGender() != null ? user.getGender().name() : ""))
                    .setCountry(user.getCountry() != null ? user.getCountry() : "")
                    .setCity(user.getCity() != null ? user.getCity() : "")
                    .setAddress(user.getAddress() != null ? user.getAddress() : "")
                    .setPostalCode(user.getPostalCode() != null ? user.getPostalCode() : "")
                    .setCreatedAt(toProtoTimestamp(user.getCreatedAt()))
                    .setUpdatedAt(toProtoTimestamp(user.getUpdatedAt()))
                    .build();
        } else {
            response = UserResponse.newBuilder()
                    .setId(request.getId())
                    .setFirstName("Unknown")
                    .build();
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }


}
