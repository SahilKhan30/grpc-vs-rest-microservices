package com.grpcvsrest.poll_service.clients;

import com.grpcvsrest.poll_service.dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "user-service", url = "http://localhost:8081/api/rest/users")
public interface UserRestClient {

    @GetMapping("/{id}")
    UserResponse getUserById(@PathVariable("id") UUID id);

}
