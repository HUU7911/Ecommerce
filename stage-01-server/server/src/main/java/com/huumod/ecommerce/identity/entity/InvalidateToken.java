package com.huumod.ecommerce.identity.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@Table(name = "invalidate_token")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvalidateToken {

    @Id
    String id;

    Date expiryTime;
}
