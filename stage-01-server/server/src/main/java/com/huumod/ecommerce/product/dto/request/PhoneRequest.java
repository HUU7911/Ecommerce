package com.huumod.ecommerce.product.dto.request;

import com.huumod.ecommerce.product.entity.PhoneOption;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhoneRequest {

    String id;

    String name;

    String description;

    List<PhoneOption> options;

    List<String> images;

    Long detail;

    Set<String> categories;
}
