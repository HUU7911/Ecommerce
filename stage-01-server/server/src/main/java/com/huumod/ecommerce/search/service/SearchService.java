package com.huumod.ecommerce.search.service;

import com.huumod.ecommerce.contract.common.PageResponse;
import com.huumod.ecommerce.search.document.phone.PhoneDocument;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SearchService {

    ElasticsearchOperations elasticsearchOperations;
    PhoneQuery phoneQuery;

    @NonFinal
    static int MAX_PAGE_SIZE = 90;

    public PageResponse<PhoneDocument> searchProduct(String keyword, String category, BigDecimal price, int page, int size) {

        int safePage = Math.max(page, 0);
        int safeSize = Math.clamp(size, 1, MAX_PAGE_SIZE);

        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(page-1, size, sort);

        NativeQuery nativeQuery = NativeQuery.builder()
                .withQuery(phoneQuery.build(keyword, category, price))
                .withPageable(pageable)
                .build();

        SearchHits<PhoneDocument> hit = elasticsearchOperations.search(nativeQuery, PhoneDocument.class);
        List<PhoneDocument> documents = hit.stream()
                .map(SearchHit::getContent)
                .toList();
        long total = hit.getTotalHits();

        return PageResponse.<PhoneDocument>builder()
                .currentPage(safePage)
                .totalPages((int) Math.ceil((double) total / safeSize))
                .pageSize(safeSize)
                .totalElements(total)
                .data(documents)
                .build();
    }
}
