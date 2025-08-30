package com.grpcvsrest.user_service.service.impl;

import com.grpcvsrest.user_service.dto.UserCreateRequest;
import com.grpcvsrest.user_service.dto.UserResponse;
import com.grpcvsrest.user_service.dto.UserUpdateRequest;
import com.grpcvsrest.user_service.entity.User;
import com.grpcvsrest.user_service.repository.UserRepository;
import com.grpcvsrest.user_service.service.UserService;
import com.grpcvsrest.user_service.util.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse createUser(UserCreateRequest userCreateRequest) {
        User user = UserMapper.toCreateEntity(userCreateRequest);
        User savedUser = userRepository.save(user);
        return UserMapper.toResponse(savedUser);
    }

    @Override
    public UserResponse getUserById(UUID id) {
       User user = userRepository.findByUuid(id)
               .orElseThrow(()->new RuntimeException("User not found"));
       return UserMapper.toResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    @Override
    public UserResponse updateUser(UUID id, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findByUuid(id)
                .orElseThrow(()->new RuntimeException("User not found"));
        UserMapper.toUpdateEntity(user,userUpdateRequest);
        User savedUser = userRepository.save(user);
        return UserMapper.toResponse(savedUser);
    }

    @Override
    public void deleteUser(UUID id) {
        User user = userRepository.findByUuid(id)
                .orElseThrow(()->new RuntimeException("User not found"));
        userRepository.delete(user);
    }
}
