package com.huumod.ecommerce.product.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
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

    @ElementCollection
    @CollectionTable(name = "accessory_options",
            joinColumns = @JoinColumn(name = "accessory_id"))
    List<AccessoryOption> accessoryOptions = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "accessory_images",
            joinColumns = @JoinColumn(name = "accessory_id"))
    @Column(name = "image_url")
    List<String> images = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "accessory_category",
            joinColumns = @JoinColumn(name = "accessory_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    Set<Category> categories;
}