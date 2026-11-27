package com.huumod.ecommerce.identity.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role {

    @Id
    String name;

    String description;

    @ManyToMany(fetch = FetchType.EAGER)
    Set<Permission> permissions;
}
