
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
@Table(name = "users", indexes = {
        @Index(name = "idx_users_email", columnList = "email"),
        @Index(name = "idx_users_username", columnList = "username")
}, uniqueConstraints = {
        @UniqueConstraint(name = "uk_users_email", columnNames = "email"),
        @UniqueConstraint(name = "uk_users_username", columnNames = "username")
})
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
