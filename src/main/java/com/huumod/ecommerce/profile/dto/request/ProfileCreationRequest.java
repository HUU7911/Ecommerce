package com.huumod.ecommerce.profile.dto.request;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileCreationRequest {

    String id;

    String userId;

    String username;

    String firstName;

    String lastName;

    String phoneNumber;

    String email;

    LocalDate birthDate;
}
