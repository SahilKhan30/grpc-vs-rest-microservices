package com.grpcvsrest.user_service.service;


import com.grpcvsrest.user_service.dto.UserResponse;
import com.grpcvsrest.user_service.dto.UserCreateRequest;
import com.grpcvsrest.user_service.dto.UserUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponse createUser(UserCreateRequest userCreateRequest);
    UserResponse getUserById(UUID id);
    List<UserResponse> getAllUsers();
    UserResponse updateUser(UUID id, UserUpdateRequest userUpdateRequest);
    void deleteUser(UUID id);
}
