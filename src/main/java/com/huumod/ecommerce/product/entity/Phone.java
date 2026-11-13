package com.huumod.ecommerce.product.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@Table(name = "phone")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String name;

    String description;

    List<PhoneOption> options;

    List<String> images;

    @OneToOne(fetch = FetchType.EAGER)
    Set<PhoneDetail> detail;

    @OneToMany(fetch = FetchType.EAGER)
    Set<Category> categories;
}
