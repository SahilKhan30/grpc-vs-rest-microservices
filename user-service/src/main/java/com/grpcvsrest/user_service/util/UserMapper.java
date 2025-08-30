package com.grpcvsrest.user_service.util;

import com.grpcvsrest.user_service.dto.UserCreateRequest;
import com.grpcvsrest.user_service.dto.UserResponse;
import com.grpcvsrest.user_service.dto.UserUpdateRequest;
import com.grpcvsrest.user_service.entity.User;

public class UserMapper {

    public static User toCreateEntity (UserCreateRequest userCreateRequest){
        User user = new User();
        user.setFirstName(userCreateRequest.getFirstName());
        user.setLastName(userCreateRequest.getLastName());
        user.setEmail(userCreateRequest.getEmail());
        user.setAge(userCreateRequest.getAge());
        user.setGender(userCreateRequest.getGender());
        user.setCountry(userCreateRequest.getCountry());
        user.setCity(userCreateRequest.getCity());
        user.setAddress(userCreateRequest.getAddress());
        user.setPostalCode(userCreateRequest.getPostalCode());
        return user;
    }

    public static void toUpdateEntity(User user, UserUpdateRequest userUpdateRequest) {
        if (userUpdateRequest.getFirstName() != null) {
            user.setFirstName(userUpdateRequest.getFirstName());
        }
        if (userUpdateRequest.getLastName() != null) {
            user.setLastName(userUpdateRequest.getLastName());
        }
        if (userUpdateRequest.getEmail() != null) {
            user.setEmail(userUpdateRequest.getEmail());
        }
        if (userUpdateRequest.getAge() != null) {
            user.setAge(userUpdateRequest.getAge());
        }
        if (userUpdateRequest.getGender() != null) {
            user.setGender(userUpdateRequest.getGender());
        }
        if (userUpdateRequest.getCountry() != null) {
            user.setCountry(userUpdateRequest.getCountry());
        }
        if (userUpdateRequest.getCity() != null) {
            user.setCity(userUpdateRequest.getCity());
        }
        if (userUpdateRequest.getAddress() != null) {
            user.setAddress(userUpdateRequest.getAddress());
        }
        if (userUpdateRequest.getPostalCode() != null) {
            user.setPostalCode(userUpdateRequest.getPostalCode());
        }
    }

    public static UserResponse toResponse (User user){
        return UserResponse.builder()
                .id(user.getUuid())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .age(user.getAge())
                .gender(user.getGender())
                .country(user.getCountry())
                .city(user.getCity())
                .address(user.getAddress())
                .postalCode(user.getPostalCode())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
