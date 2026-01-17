package com.grpcvsrest.poll_service.util;

import com.google.protobuf.Timestamp;
import com.grpcvsrest.poll_service.enums.Gender;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

public class UserGrpcResponseMapper {

    public static com.grpcvsrest.poll_service.dto.response.UserResponse toUserResponse(
            com.grpcvsrest.user_service.grpc.UserResponse grpcResponse) {
        com.grpcvsrest.poll_service.dto.response.UserResponse dto = new com.grpcvsrest.poll_service.dto.response.UserResponse();

        dto.setId(UUID.fromString(grpcResponse.getId()));
        dto.setFirstName(grpcResponse.getFirstName());
        dto.setLastName(grpcResponse.getLastName());
        dto.setEmail(grpcResponse.getEmail());
        dto.setAge(grpcResponse.getAge());
        dto.setGender(Gender.valueOf(grpcResponse.getGender().name())); // convert enum to String
        dto.setCountry(grpcResponse.getCountry());
        dto.setCity(grpcResponse.getCity());
        dto.setAddress(grpcResponse.getAddress());
        dto.setPostalCode(grpcResponse.getPostalCode());
        dto.setCreatedAt(toLocalDateTime(grpcResponse.getCreatedAt()));
        dto.setUpdatedAt(toLocalDateTime(grpcResponse.getUpdatedAt()));

        return dto;
    }

    private static LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null || timestamp.getSeconds() == 0) {
            return null;
        }
        return Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
