package com.huumod.ecommerce.identity.entity;

import com.huumod.ecommerce.identity.convert.EncryptConvertor;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Convert(converter = EncryptConvertor.class)
    String username;

    String password;

    @Convert(converter = EncryptConvertor.class)
    String firstName;

    @Convert(converter = EncryptConvertor.class)
    String lastName;

    @Convert(converter = EncryptConvertor.class)
    String email;

    LocalDate birthDate;

    @ManyToMany(fetch = FetchType.EAGER)
    Set<Role> roles;
}
