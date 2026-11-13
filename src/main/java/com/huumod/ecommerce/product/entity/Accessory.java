package com.huumod.ecommerce.product.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "accessory")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Accessory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String name;

    String description;

    List<AccessoryOption> accessoryOptions;

    List<String> images;

    @OneToMany(fetch = FetchType.EAGER)
    Set<Category> categories;
}
