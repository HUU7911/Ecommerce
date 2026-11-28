package com.huumod.ecommerce.search.document.phone;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "phone_detail")
@Setting(settingPath = "elasticsearch/product-setting.json")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhoneDocumentDetail {

    @Id
    Long id;

    @Field(type = FieldType.Text, analyzer = "vi_folded")
    String cpu;

    @Field(type = FieldType.Text, analyzer = "vi_folded")
    String ram;

    @Field(type = FieldType.Text, analyzer = "vi_folded")
    String storage;

    @Field(type = FieldType.Text, analyzer = "vi_folded")
    String screenSize;

    @Field(type = FieldType.Text, analyzer = "vi_folded")
    String battery;

    @Field(type = FieldType.Text, analyzer = "vi_folded")
    String camera;

    @Field(type = FieldType.Text, analyzer = "vi_folded")
    String operatingSystem;
}
