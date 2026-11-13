
package com.huumod.ecommerce.profile.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "profile", indexes = {
        @Index(name = "idx_profile_user_id", columnList = "userId")
})
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String userId;

    String username;

    String firstName;

    String lastName;

    String phoneNumber;

    String email;

    LocalDate birthDate;
}
