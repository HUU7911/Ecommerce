package com.huumod.ecommerce.inventory.service;

import com.huumod.ecommerce.contract.common.exception.AppException;
import com.huumod.ecommerce.contract.common.exception.ErrorCode;
import com.huumod.ecommerce.inventory.dto.request.InventoryAccessoryRequest;
import com.huumod.ecommerce.inventory.dto.response.InventoryAccessoryResponse;
import com.huumod.ecommerce.inventory.mapper.InventoryAccessoryMapper;
import com.huumod.ecommerce.inventory.repository.InventoryAccessoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryAccessoryService {

    InventoryAccessoryMapper inventoryAccessoryMapper;
    InventoryAccessoryRepository inventoryAccessoryRepository;

     public InventoryAccessoryResponse createInventoryAccessory(InventoryAccessoryRequest request) {
         if (inventoryAccessoryRepository.existsByProductId(request.getProductId()))
             throw new AppException(ErrorCode.INVENTORY_EXSITED);

         var accessory = inventoryAccessoryMapper.toInventoryAccessory(request);

         accessory = inventoryAccessoryRepository.save(accessory);

         return inventoryAccessoryMapper.toInventoryAccessoryResponse(accessory);
     }
}
