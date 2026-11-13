package com.huumod.ecommerce.product.service;

import com.huumod.ecommerce.common.exception.AppException;
import com.huumod.ecommerce.common.exception.ErrorCode;
import com.huumod.ecommerce.product.dto.request.PhoneDetailRequest;
import com.huumod.ecommerce.product.dto.request.PhoneDetailUpdateRequest;
import com.huumod.ecommerce.product.dto.response.PhoneDetailResponse;
import com.huumod.ecommerce.product.mapper.PhoneDetailMapper;
import com.huumod.ecommerce.product.repository.PhoneDetailRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PhoneDetailService {

    PhoneDetailMapper phoneDetailMapper;
    PhoneDetailRepository phoneDetailRepository;

    public PhoneDetailResponse createPhoneDetail(PhoneDetailRequest request) {
        var detail = phoneDetailMapper.toPhoneDetail(request);

        detail = phoneDetailRepository.save(detail);

        return phoneDetailMapper.toPhoneDetailResponse(detail);
    }

    public PhoneDetailResponse updatePhoneDetail(Long id, PhoneDetailUpdateRequest request) {
        var detail = phoneDetailRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.UNAUTHORIZED)
        );

        phoneDetailMapper.updatePhoneDetail(detail, request);

        detail = phoneDetailRepository.save(detail);

        return phoneDetailMapper.toPhoneDetailResponse(detail);
    }

    public void deleteDetail(Long id) {
        phoneDetailRepository.deleteById(id);
    }
}
