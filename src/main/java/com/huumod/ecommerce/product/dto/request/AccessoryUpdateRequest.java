package com.huumod.ecommerce.product.dto.request;

import com.huumod.ecommerce.product.entity.AccessoryOption;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccessoryUpdateRequest {

    String name;

    String description;

    List<AccessoryOption> accessoryOptions;

    List<String> images;

    Set<String> categories;
}
