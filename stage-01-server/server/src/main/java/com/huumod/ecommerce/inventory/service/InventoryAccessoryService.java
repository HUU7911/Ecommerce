package com.huumod.ecommerce.inventory.service;

import com.huumod.ecommerce.contract.PageResponse;
import com.huumod.ecommerce.contract.constant.InventoryStatus;
import com.huumod.ecommerce.contract.exception.AppException;
import com.huumod.ecommerce.contract.exception.ErrorCode;
import com.huumod.ecommerce.inventory.dto.request.*;
import com.huumod.ecommerce.inventory.dto.response.InventoryAccessoryResponse;
import com.huumod.ecommerce.inventory.entity.InventoryAccessory;
import com.huumod.ecommerce.inventory.mapper.InventoryAccessoryMapper;
import com.huumod.ecommerce.inventory.repository.InventoryAccessoryRepository;
import com.huumod.ecommerce.product.repository.AccessoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryAccessoryService {

    InventoryAccessoryRepository repository;
    InventoryAccessoryMapper mapper;
    AccessoryRepository accessoryRepository;

    @Transactional
    public InventoryAccessoryResponse create(InventoryAccessoryRequest request) {

        if (!accessoryRepository.existsById(request.getProductId()))
            throw new AppException(ErrorCode.ACCESSORY_NOT_FOUND);

        if (repository.existsByProductId(request.getProductId()))
            throw new AppException(ErrorCode.INVENTORY_EXSITED);

        var inventory = InventoryAccessory.builder()
                .productId(request.getProductId())
                .quantity(0)
                .reservedQuantity(0)
                .lowStockThreshold(request.getLowStockThreshold() == null
                        ? 5
                        : request.getLowStockThreshold()
                ).status(InventoryStatus.ACTIVE)
                .build();

        return mapper.toResponse(repository.save(inventory));
    }

    @Transactional(readOnly = true)
    public InventoryAccessoryResponse getById(String id) {

        var inventory = repository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVENTORY_NOT_FOUND));

        return mapper.toResponse(inventory);
    }

    @Transactional(readOnly = true)
    public InventoryAccessoryResponse getByProductId(String productId) {

        var inventory = repository.findByProductId(productId).orElseThrow(
                () -> new AppException(ErrorCode.INVENTORY_NOT_FOUND));

        return mapper.toResponse(inventory);
    }

    @Transactional(readOnly = true)
    public PageResponse<InventoryAccessoryResponse> getAll(int page, int size, InventoryStatus status) {

        Sort sort = Sort.by("updatedAt").descending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        var pageData = (Objects.isNull(status))
                ? repository.findAll(pageable)
                : repository.findAllByStatus(status, pageable);

        return PageResponse.<InventoryAccessoryResponse>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(pageData.getContent().stream()
                        .map(mapper::toResponse)
                        .toList())
                .build();
    }

    @Transactional
    public InventoryAccessoryResponse stockIn(String productId, StockInRequest request) {

        var inventory = repository.findByProductIdForUpdate(productId).orElseThrow(
                () -> new AppException(ErrorCode.INVENTORY_NOT_FOUND));

        if (inventory.getStatus() != InventoryStatus.ACTIVE)
            throw new IllegalStateException("Inventory is in active");

        inventory.setQuantity(Math.addExact(inventory.getQuantity(), request.getQuantity()));

        return mapper.toResponse(repository.save(inventory));
    }

    @Transactional
    public InventoryAccessoryResponse adjust(String productId, StockAdjustmentRequest request) {

        var inventory = repository.findByProductIdForUpdate(productId).orElseThrow(
                () -> new AppException(ErrorCode.INVENTORY_NOT_FOUND));

        if (request.getActualQuantity() < inventory.getReservedQuantity())
            throw new IllegalArgumentException("Actual quantity cannot be less than reserved quantity");

        inventory.setQuantity(request.getActualQuantity());

        return mapper.toResponse(repository.save(inventory));
    }

    @Transactional
    public void deactivate(String productId) {

        var inventory = repository.findByProductIdForUpdate(productId).orElseThrow(
                () -> new AppException(ErrorCode.INVENTORY_NOT_FOUND));

        if (inventory.getReservedQuantity() > 0)
            throw new IllegalStateException("Cannot deactivate inventory with active reservations");

        inventory.setStatus(InventoryStatus.INACTIVE);

        repository.save(inventory);
    }

    @Transactional(readOnly = true)
    public PageResponse<InventoryAccessoryResponse> getLowStock(int page, int size) {

        Sort sort = Sort.by("updatedAt").descending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        var pageData = repository.findLowStock(pageable);

        return PageResponse.<InventoryAccessoryResponse>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(pageData.getContent().stream()
                        .map(mapper::toResponse)
                        .toList())
                .build();
    }
}
