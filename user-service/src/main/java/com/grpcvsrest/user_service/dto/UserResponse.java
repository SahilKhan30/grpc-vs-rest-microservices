package com.grpcvsrest.user_service.dto;

import com.grpcvsrest.user_service.enums.Gender;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private Integer age;
    private Gender gender;
    private String country;
    private String city;
    private String address;
    private String postalCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
