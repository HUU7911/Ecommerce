package com.huumod.ecommerce.profile.entity;

import com.huumod.ecommerce.profile.convert.EncryptConvertor;
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

    @Convert(converter = EncryptConvertor.class)
    String username;

    @Convert(converter = EncryptConvertor.class)
    String firstName;

    @Convert(converter = EncryptConvertor.class)
    String lastName;

    @Convert(converter = EncryptConvertor.class)
    String phoneNumber;

    @Convert(converter = EncryptConvertor.class)
    String email;

    @Convert(converter = EncryptConvertor.class)
    String avatar;

    LocalDate birthDate;
}
