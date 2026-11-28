package com.huumod.ecommerce.product.service;

import com.huumod.ecommerce.contract.PageResponse;
import com.huumod.ecommerce.contract.event.CategoryEvent;
import com.huumod.ecommerce.contract.event.PhoneEventDetail;
import com.huumod.ecommerce.contract.event.PhoneEvent;
import com.huumod.ecommerce.contract.exception.AppException;
import com.huumod.ecommerce.contract.exception.ErrorCode;
import com.huumod.ecommerce.file.service.FileService;
import com.huumod.ecommerce.product.dto.request.PhoneRequest;
import com.huumod.ecommerce.product.dto.request.PhoneUpdateRequest;
import com.huumod.ecommerce.product.dto.response.PhoneResponse;
import com.huumod.ecommerce.product.entity.Category;
import com.huumod.ecommerce.product.mapper.event.CategoryEventMapper;
import com.huumod.ecommerce.product.mapper.event.PhoneDetailEventMapper;
import com.huumod.ecommerce.product.mapper.PhoneMapper;
import com.huumod.ecommerce.product.mapper.event.PhoneEventOptionMapper;
import com.huumod.ecommerce.product.repository.CategoryRepository;
import com.huumod.ecommerce.product.repository.PhoneDetailRepository;
import com.huumod.ecommerce.product.repository.PhoneRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PhoneService {

    FileService fileService;
    PhoneMapper phoneMapper;
    PhoneRepository phoneRepository;
    CategoryRepository categoryRepository;
    CategoryEventMapper categoryEventMapper;
    PhoneDetailRepository phoneDetailRepository;
    PhoneDetailEventMapper phoneDetailEventMapper;
    PhoneEventOptionMapper phoneEventOptionMapper;
    KafkaTemplate<String, PhoneEvent> kafkaTemplate;

    public PhoneResponse createPhone(PhoneRequest request, MultipartFile file) throws Exception {
        if (phoneRepository.existsByName(request.getName()))
            throw new AppException(ErrorCode.PHONE_EXISTED);

        var phone = phoneMapper.toPhone(request);

        var details = phoneDetailRepository.findById(request.getDetail()).orElse(null);
        phone.setDetail(details);

        Set<Category> categories = new HashSet<>(categoryRepository.findAllById(request.getCategories()));
        phone.setCategories(categories);

        var images = fileService.uploadFile(file);
        phone.setImages(List.of(images.getUrl()));
        phone = phoneRepository.save(phone);

        // Broker message with kafka
        PhoneEventDetail detail = phoneDetailEventMapper.toPhoneEventDetail(details);
        Set<CategoryEvent> category = categoryRepository.findAll().stream()
                .map(categoryEventMapper::toCategoryEvent)
                .collect(Collectors.toSet());

        // Event send to Elasticsearch
        PhoneEvent event = PhoneEvent.builder()
                .event("CREATED")
                .id(phone.getId())
                .options(phone.getOptions().stream()
                        .map(phoneEventOptionMapper::toPhoneEventOption)
                        .toList()
                ).images(phone.getImages())
                .categories(category)
                .detail(detail)
                .build();

        try {
            kafkaTemplate.send("created-product", event);
        } catch (Exception e) {
            throw new AppException(ErrorCode.PHONE_EXISTED);
        }

        return phoneMapper.toPhoneResponse(phone);
    }

    public PhoneResponse updatePhone(String id, PhoneUpdateRequest request, MultipartFile file) throws Exception {
        var phone = phoneRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.PHONE_NOT_FOUND)
        );

        phoneMapper.updatePhone(phone, request);
        var details = phoneDetailRepository.findById(request.getDetail())
                .orElse(null);
        phone.setDetail(details);

        Set<Category> categories = new HashSet<>(categoryRepository.findAllById(request.getCategories()));
        phone.setCategories(categories);

        // Upload images
        var images = fileService.uploadFile(file);
        phone.setImages(List.of(images.getUrl()));

        phone = phoneRepository.save(phone);

        // Broker message with kafka
        PhoneEventDetail detail = phoneDetailEventMapper.toPhoneEventDetail(details);
        Set<CategoryEvent> category = categoryRepository.findAll().stream()
                .map(categoryEventMapper::toCategoryEvent)
                .collect(Collectors.toSet());

        // Event send to Elasticsearch
        PhoneEvent event = PhoneEvent.builder()
                .event("UPDATED")
                .id(phone.getId())
                .options(phone.getOptions().stream()
                        .map(phoneEventOptionMapper::toPhoneEventOption)
                        .toList()
                ).images(phone.getImages())
                .categories(category)
                .detail(detail)
                .build();

        try {
            kafkaTemplate.send("update-product", event);
        } catch (Exception e) {
            throw new AppException(ErrorCode.PHONE_EXISTED);
        }

        return phoneMapper.toPhoneResponse(phone);
    }

    public PageResponse<PhoneResponse> getAll(int page, int size) {

        Sort sort = Sort.by("createdAt").ascending();
        Pageable pageable = PageRequest.of(page-1, size, sort);
        var pageData = phoneRepository.findAll(pageable);

        return PageResponse.<PhoneResponse>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(pageData.stream()
                        .map(phoneMapper::toPhoneResponse)
                        .toList()
                )
                .build();
    }

    public void deletePhone(String id) {
        phoneRepository.deleteById(id);
    }
}
