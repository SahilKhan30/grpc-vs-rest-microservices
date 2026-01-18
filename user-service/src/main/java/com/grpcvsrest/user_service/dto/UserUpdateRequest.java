package com.grpcvsrest.user_service.dto;

import com.grpcvsrest.user_service.enums.Gender;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {

    private String firstName;

    private String lastName;

    @Email(message = "Invalid email format")
    private String email;

    @Min(value = 0, message = "Age must be at least 0")
    private Integer age;

    private Gender gender;

    private String country;

    private String city;

    private String address;

    private String postalCode;
}
