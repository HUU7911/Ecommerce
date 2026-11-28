package com.huumod.ecommerce.inventory.service;

import com.huumod.ecommerce.contract.PageResponse;
import com.huumod.ecommerce.contract.exception.AppException;
import com.huumod.ecommerce.contract.exception.ErrorCode;
import com.huumod.ecommerce.inventory.dto.request.InventoryPhoneRequest;
import com.huumod.ecommerce.inventory.dto.response.InventoryPhoneResponse;
import com.huumod.ecommerce.inventory.entity.InventoryPhoneOption;
import com.huumod.ecommerce.inventory.mapper.InventoryPhoneMapper;
import com.huumod.ecommerce.inventory.repository.InventoryPhoneOptionRepository;
import com.huumod.ecommerce.inventory.repository.InventoryPhoneRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryPhoneService {

    InventoryPhoneMapper inventoryPhoneMapper;
    InventoryPhoneRepository inventoryPhoneRepository;
    InventoryPhoneOptionRepository inventoryPhoneOptionRepository;

    static int MAX_QUANTITY = 99;

    @Transactional
    public InventoryPhoneResponse createInventory(String phoneOptionId, InventoryPhoneRequest request) {
        if (inventoryPhoneRepository.existsByProductId(request.getProductId()))
            throw new AppException(ErrorCode.INVENTORY_EXSITED);

        var inventory = inventoryPhoneMapper.toInventoryPhone(request);

        Set<InventoryPhoneOption> optionSet = new HashSet<>();
        optionSet.add(inventoryPhoneOptionRepository.findById(phoneOptionId).orElse(null));
        inventory.setOption(optionSet);

        var phoneOption = inventoryPhoneOptionRepository.findById(phoneOptionId).orElseThrow(
                () -> new AppException(ErrorCode.PHONE_NOT_FOUND)
        );
        Integer totalQuantity = 0;
        totalQuantity += phoneOption.getQuantity();

        inventory.setTotalQuantity(totalQuantity);
        inventory.setCreatedAt(Instant.now());
        inventory.setUpdatedAt(Instant.now());
        inventory = inventoryPhoneRepository.save(inventory);

        return inventoryPhoneMapper.toInventoryPhoneResponse(inventory);
    }

    public PageResponse<InventoryPhoneResponse> getALl(int page, int size) {

        Sort sort = Sort.by("updatedAt").descending();
        Pageable pageable = PageRequest.of(page-1, size, sort);
        var pageData = inventoryPhoneRepository.findAll(pageable);

        return PageResponse.<InventoryPhoneResponse>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(pageData.stream()
                        .map(inventoryPhoneMapper::toInventoryPhoneResponse)
                        .toList()
                )
                .build();
    }

    @Transactional
    public InventoryPhoneResponse increaseQuantity(String productId) {
        int updated = inventoryPhoneRepository.increaseIfNotMax(productId, MAX_QUANTITY);

        if (updated == 0) {
            if (!inventoryPhoneRepository.existsByProductId(productId))
                throw new AppException(ErrorCode.INVENTORY_NOT_FOUND);
            throw new AppException(ErrorCode.INVENTORY_MAX_SIZE);
        }

        var data = inventoryPhoneRepository.findByProductId(productId)
                .orElseThrow(() -> new AppException(ErrorCode.INVENTORY_NOT_FOUND));

        return inventoryPhoneMapper.toInventoryPhoneResponse(data);
    }

    @Transactional
    public InventoryPhoneResponse reduceQuantity(String productId) {
        int updated = inventoryPhoneRepository.decreaseIfAvailable(productId);

        if (updated == 0) {
            if (!inventoryPhoneRepository.existsByProductId(productId))
                throw new AppException(ErrorCode.INVENTORY_NOT_FOUND);
            throw new AppException(ErrorCode.PRODUCT_OUT_STOCK);
        }

        var data = inventoryPhoneRepository.findByProductId(productId)
                .orElseThrow(() -> new AppException(ErrorCode.INVENTORY_NOT_FOUND));

        var response = inventoryPhoneMapper.toInventoryPhoneResponse(data);

        if (data.getTotalQuantity() == 0)
            response.setStatus("The product is out of stock.");

        return response;
    }

    public void removeInventory(String id) {
        inventoryPhoneRepository.deleteById(id);
    }
}
