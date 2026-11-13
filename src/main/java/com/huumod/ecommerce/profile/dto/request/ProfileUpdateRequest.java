package com.huumod.ecommerce.profile.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileUpdateRequest {

    String userId;

    String username;

    String firstName;

    String lastName;

    String phoneNumber;

    String email;

    LocalDate birthDate;
}
