package com.huumod.ecommerce.search.kafka;

import com.huumod.ecommerce.contract.event.CategoryEvent;
import com.huumod.ecommerce.contract.event.PhoneEvent;
import com.huumod.ecommerce.search.document.phone.CategoryDocument;
import com.huumod.ecommerce.search.document.phone.PhoneDocument;
import com.huumod.ecommerce.search.mapper.PhoneDetailDocumentMapper;
import com.huumod.ecommerce.search.repository.PhoneSearchRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KafkaConsumer {

    PhoneSearchRepository phoneSearchRepository;
    PhoneDetailDocumentMapper phoneDetailMapper;

    @KafkaListener(topics = "create-product")
    public void consume(PhoneEvent event) {
        if (Objects.isNull(event)) {
            log.warn("Product not receive: ");
            return;
        }

        switch (event.getEvent()) {
            case "CREATED", "UPDATED" -> phoneSearchRepository.save(toPhoneDocument(event));
            case "DELETED" -> phoneSearchRepository.deleteById(event.getId());
        }
    }

    private PhoneDocument toPhoneDocument(PhoneEvent event) {
        return PhoneDocument.builder()
                .id(event.getId())
                .description(event.getDescription())
                .categories(toCategoryDocument(event.getCategories()))
                .detail(phoneDetailMapper.toPhoneDocumentDetail(event.getDetail()))
                .images(event.getImages())
                .createdAt(event.getCreatedAt())
                .build();
    }

    private Set<CategoryDocument> toCategoryDocument(Set<CategoryEvent> categoryEvents) {
        return categoryEvents.stream()
                .map(map -> CategoryDocument.builder()
                        .name(map.getName())
                        .description(map.getDescription())
                        .build())
                .collect(Collectors.toSet());
    }
}
