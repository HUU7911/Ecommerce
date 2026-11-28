package com.huumod.ecommerce.search.document.phone;

import com.huumod.ecommerce.product.entity.PhoneDetail;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "phone")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setting(settingPath = "")
public class PhoneDocument {

    @Id
    String id;

    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "vi_accent"),
            otherFields = @InnerField(suffix = "folded", type = FieldType.Text, analyzer = "vi_folded"))
    String name;

    @Field(type = FieldType.Text, analyzer = "vi_folded")
    String description;

    @Field(type = FieldType.Object)
    List<PhoneDocumentOption> options;

    @Field(type = FieldType.Object)
    Set<CategoryDocument> categories;

    @Field(type = FieldType.Text)
    List<String> images;

    @Field(type = FieldType.Object)
    PhoneDocumentDetail detail;

    LocalDate createdAt;
}
