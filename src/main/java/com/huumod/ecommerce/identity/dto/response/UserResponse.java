package com.huumod.ecommerce.identity.dto.response;

import com.huumod.ecommerce.identity.entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {

    String id;

    String username;

    String password;

    String firstName;

    String lastName;

    String email;

    Date birthDate;

    Set<Role> roles;
}
