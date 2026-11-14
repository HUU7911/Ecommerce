package com.huumod.ecommerce.inventory.service;

import com.huumod.ecommerce.common.PageResponse;
import com.huumod.ecommerce.common.exception.AppException;
import com.huumod.ecommerce.common.exception.ErrorCode;
import com.huumod.ecommerce.inventory.dto.request.InventoryPhoneRequest;
import com.huumod.ecommerce.inventory.dto.response.InventoryPhoneResponse;
import com.huumod.ecommerce.inventory.mapper.InventoryPhoneMapper;
import com.huumod.ecommerce.inventory.repository.InventoryPhoneRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryPhoneService {

    InventoryPhoneMapper inventoryPhoneMapper;
    InventoryPhoneRepository inventoryPhoneRepository;

    static int MAX_QUANTITY = 99;

    public InventoryPhoneResponse createInventory(InventoryPhoneRequest request) {
        if (inventoryPhoneRepository.existsByProductId(request.getProductId()))
            throw new AppException(ErrorCode.INVENTORY_EXSITED);

        var inventory = inventoryPhoneMapper.toInventoryPhone(request);

        if (Objects.isNull(request.getQuantity()))
            inventory.setQuantity(1);

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
    public InventoryPhoneResponse increaseQuantity(String id) {
        int updated = inventoryPhoneRepository.increaseIfNotMax(id, MAX_QUANTITY);

        if (updated == 0) {
            if (!inventoryPhoneRepository.existsById(id))
                throw new AppException(ErrorCode.INVENTORY_NOT_FOUND);
            throw new AppException(ErrorCode.INVENTORY_MAX_SIZE);
        }

        var data = inventoryPhoneRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVENTORY_NOT_FOUND));

        return inventoryPhoneMapper.toInventoryPhoneResponse(data);
    }

    @Transactional
    public InventoryPhoneResponse reduceQuantity(String id) {
        int updated = inventoryPhoneRepository.decreaseIfAvailable(id);

        if (updated == 0) {
            if (!inventoryPhoneRepository.existsById(id))
                throw new AppException(ErrorCode.INVENTORY_NOT_FOUND);
            throw new AppException(ErrorCode.PRODUCT_OUT_STOCK);
        }

        var data = inventoryPhoneRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVENTORY_NOT_FOUND));

        var response = inventoryPhoneMapper.toInventoryPhoneResponse(data);

        if (data.getQuantity() == 0)
            response.setStatus("The product is out of stock.");

        return response;
    }

    public void removeInventory(String id) {
        inventoryPhoneRepository.deleteById(id);
    }
}
