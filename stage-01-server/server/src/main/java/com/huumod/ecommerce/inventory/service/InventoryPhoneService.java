package com.huumod.ecommerce.inventory.service;

import com.huumod.ecommerce.contract.PageResponse;
import com.huumod.ecommerce.contract.constant.InventoryStatus;
import com.huumod.ecommerce.contract.exception.AppException;
import com.huumod.ecommerce.contract.exception.ErrorCode;
import com.huumod.ecommerce.inventory.dto.request.*;
import com.huumod.ecommerce.inventory.dto.response.InventoryPhoneResponse;
import com.huumod.ecommerce.inventory.entity.InventoryPhone;
import com.huumod.ecommerce.inventory.mapper.InventoryPhoneMapper;
import com.huumod.ecommerce.inventory.repository.InventoryPhoneRepository;
import com.huumod.ecommerce.product.repository.PhoneRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryPhoneService {

    InventoryPhoneRepository repository;
    InventoryPhoneMapper mapper;
    PhoneRepository phoneRepository;

    @Transactional
    public InventoryPhoneResponse create(InventoryPhoneRequest request) {

        if (!phoneRepository.existsById(request.getProductId()))
            throw new AppException(ErrorCode.PHONE_NOT_FOUND);

        if (repository.existsByProductId(request.getProductId()))
            throw new AppException(ErrorCode.INVENTORY_EXSITED);

        var inventory = InventoryPhone.builder()
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
    public InventoryPhoneResponse getById(String id) {

        var inventory = repository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVENTORY_NOT_FOUND));

        return mapper.toResponse(inventory);
    }

    @Transactional(readOnly = true)
    public InventoryPhoneResponse getByProductId(String productId) {

        var inventory = repository.findByProductId(productId).orElseThrow(
                () -> new AppException(ErrorCode.INVENTORY_NOT_FOUND));

        return mapper.toResponse(inventory);
    }

    @Transactional(readOnly = true)
    public PageResponse<InventoryPhoneResponse> getAll(int page, int size, InventoryStatus status) {

        if (page < 1 || size < 1 || size > 100)
            throw new IllegalArgumentException("Invalid pagination parameters");

        var pageable = PageRequest.of(page - 1, size, Sort.by("updatedAt").descending());

        var pageData = status == null
                ? repository.findAll(pageable)
                : repository.findAllByStatus(status, pageable);

        return PageResponse.<InventoryPhoneResponse>builder()
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
    public InventoryPhoneResponse stockIn(String productId, StockInRequest request) {

        var inventory = repository.findByProductIdForUpdate(productId).orElseThrow(
                () -> new AppException(ErrorCode.INVENTORY_NOT_FOUND));

        if (inventory.getStatus() != InventoryStatus.ACTIVE)
            throw new IllegalStateException("Inventory is inactive");

        inventory.setQuantity(Math.addExact(inventory.getQuantity(), request.getQuantity()));

        return mapper.toResponse(repository.save(inventory));
    }

    @Transactional
    public InventoryPhoneResponse adjust(String productId, StockAdjustmentRequest request) {

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
    public PageResponse<InventoryPhoneResponse> getLowStock(int page, int size) {

        Sort sort = Sort.by("updatedAt").descending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        var pageData = repository.findLowStock(pageable);

        return PageResponse.<InventoryPhoneResponse>builder()
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
