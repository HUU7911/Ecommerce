package com.huumod.ecommerce.search.repository;

import com.huumod.ecommerce.search.document.phone.PhoneDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneSearchRepository extends ElasticsearchRepository<PhoneDocument, String> {
}
