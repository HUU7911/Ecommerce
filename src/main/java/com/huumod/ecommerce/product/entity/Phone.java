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

    @ElementCollection
    @CollectionTable(name = "phone_options", joinColumns = @JoinColumn(name = "phone_id"))
    List<PhoneOption> options;

    @ElementCollection
    @CollectionTable(name = "phone_images", joinColumns = @JoinColumn(name = "phone_id"))
    @Column(name = "image_url")
    List<String> images;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "detail_id")
    PhoneDetail detail;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "phone_categories",
            joinColumns = @JoinColumn(name = "phone_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    Set<Category> categories;
}
