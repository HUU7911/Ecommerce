package com.huumod.ecommerce.contract.event;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhoneEvent {

    String event;

    String id;

    String name;

    String description;

    List<PhoneEventOption> options;

    List<String> images;

    PhoneEventDetail detail;

    Set<CategoryEvent> categories;

    LocalDate createdAt;
}
