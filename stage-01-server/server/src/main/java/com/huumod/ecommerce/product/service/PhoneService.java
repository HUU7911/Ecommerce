package com.huumod.ecommerce.product.service;

import com.huumod.ecommerce.common.PageResponse;
import com.huumod.ecommerce.common.exception.AppException;
import com.huumod.ecommerce.common.exception.ErrorCode;
import com.huumod.ecommerce.file.service.FileService;
import com.huumod.ecommerce.product.dto.request.PhoneRequest;
import com.huumod.ecommerce.product.dto.request.PhoneUpdateRequest;
import com.huumod.ecommerce.product.dto.response.PhoneResponse;
import com.huumod.ecommerce.product.entity.Category;
import com.huumod.ecommerce.product.mapper.PhoneMapper;
import com.huumod.ecommerce.product.repository.CategoryRepository;
import com.huumod.ecommerce.product.repository.PhoneDetailRepository;
import com.huumod.ecommerce.product.repository.PhoneRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PhoneService {

    PhoneMapper phoneMapper;
    PhoneRepository phoneRepository;
    PhoneDetailRepository phoneDetailRepository;
    CategoryRepository categoryRepository;
    FileService fileService;

    public PhoneResponse createPhone(PhoneRequest request, MultipartFile file)
            throws Exception {
        if (phoneRepository.existsByName(request.getName()))
            throw new AppException(ErrorCode.PHONE_EXISTED);

        var phone = phoneMapper.toPhone(request);

        var details = phoneDetailRepository.findById(request.getDetail())
                .orElse(null);
        phone.setDetail(details);

        Set<Category> categories = new HashSet<>(
                categoryRepository.findAllById(request.getCategories()));
        phone.setCategories(categories);

        var images = fileService.uploadFile(file);
        phone.setImages(List.of(images.getUrl()));

        phone = phoneRepository.save(phone);

        return phoneMapper.toPhoneResponse(phone);
    }

    public PhoneResponse updatePhone(String id, PhoneUpdateRequest request, MultipartFile file)
            throws Exception {
        var phone = phoneRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.PHONE_NOT_FOUND)
        );

        phoneMapper.updatePhone(phone, request);
        var details = phoneDetailRepository.findById(request.getDetail())
                .orElse(null);
        phone.setDetail(details);

        Set<Category> categories = new HashSet<>(
                categoryRepository.findAllById(request.getCategories()));
        phone.setCategories(categories);
        var images = fileService.uploadFile(file);
        phone.setImages(List.of(images.getUrl()));

        phone = phoneRepository.save(phone);

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
