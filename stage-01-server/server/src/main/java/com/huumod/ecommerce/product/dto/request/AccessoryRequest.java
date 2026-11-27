package com.huumod.ecommerce.product.dto.request;

import com.huumod.ecommerce.product.entity.AccessoryOption;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccessoryRequest {

    String id;

    String name;

    String description;

    List<AccessoryOption> accessoryOptions;

    List<String> images;

    Set<String> categories;
}
