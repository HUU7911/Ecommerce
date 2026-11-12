package com.huumod.ecommerce.identity.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {

    String username;

    String firstName;

    String lastName;

    String email;

    Date birthDate;

    Set<String> roles;
}
