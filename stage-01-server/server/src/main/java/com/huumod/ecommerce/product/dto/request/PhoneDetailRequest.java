package com.huumod.ecommerce.product.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhoneDetailRequest {

    Long id;

    String cpu;
    String ram;
    String storage;
    String screenSize;
    String battery;
    String camera;
    String operatingSystem;
}
