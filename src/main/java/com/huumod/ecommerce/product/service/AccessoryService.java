package com.huumod.ecommerce.product.service;

import com.huumod.ecommerce.common.exception.AppException;
import com.huumod.ecommerce.common.exception.ErrorCode;
import com.huumod.ecommerce.product.dto.request.AccessoryRequest;
import com.huumod.ecommerce.product.dto.request.AccessoryUpdateRequest;
import com.huumod.ecommerce.product.dto.response.AccessoryResponse;
import com.huumod.ecommerce.product.entity.Category;
import com.huumod.ecommerce.product.mapper.AccessoryMapper;
import com.huumod.ecommerce.product.repository.AccessoryRepository;
import com.huumod.ecommerce.product.repository.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccessoryService {

    AccessoryMapper accessoryMapper;
    AccessoryRepository accessoryRepository;
    CategoryRepository categoryRepository;

    public AccessoryResponse createAccessory(AccessoryRequest request) {
        if (accessoryRepository.existsByName(request.getName()))
            throw new AppException(ErrorCode.ACCESSORY_EXISTED);

        var accessory = accessoryMapper.toAccessory(request);
        Set<Category> categories = new HashSet<>(
                categoryRepository.findAllById(request.getCategories()));
        accessory.setCategories(categories);

        accessory = accessoryRepository.save(accessory);

        return accessoryMapper.toAccessoryResponse(accessory);
    }

    public AccessoryResponse updateCategory(String id, AccessoryUpdateRequest request) {
        var accessory = accessoryRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.ACCESSORY_NOT_FOUND)
        );

        accessoryMapper.updateAccessory(accessory, request);
        Set<Category> categories = new HashSet<>(
                categoryRepository.findAllById(request.getCategories()));
        accessory.setCategories(categories);
        accessory = accessoryRepository.save(accessory);

        return accessoryMapper.toAccessoryResponse(accessory);
    }

    public List<AccessoryResponse> getAll() {
        return accessoryRepository.findAll().stream()
                .map(accessoryMapper::toAccessoryResponse)
                .toList();
    }

    public void deleteAccessory(String id) {
        accessoryRepository.deleteById(id);
    }
}
