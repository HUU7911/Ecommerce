package com.huumod.ecommerce.search.document.phone;

import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Builder
@Document(indexName = "category")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryDocument {

    @Id
    String name;

    String description;
}
