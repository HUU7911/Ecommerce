package com.huumod.ecommerce.product.dto.response;

import com.huumod.ecommerce.product.entity.Category;
import com.huumod.ecommerce.product.entity.PhoneDetail;
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
public class PhoneResponse {

    String id;

    String name;

    String description;

    List<PhoneOption> options;

    List<String> images;

    Set<PhoneDetail> detail;

    Set<Category> categories;
}
