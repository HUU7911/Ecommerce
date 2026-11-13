package com.huumod.ecommerce.profile.service;

import com.huumod.ecommerce.common.exception.AppException;
import com.huumod.ecommerce.common.exception.ErrorCode;
import com.huumod.ecommerce.file.service.FileService;
import com.huumod.ecommerce.profile.dto.request.ProfileCreationRequest;
import com.huumod.ecommerce.profile.dto.response.ProfileResponse;
import com.huumod.ecommerce.profile.mapper.ProfileMapper;
import com.huumod.ecommerce.profile.repository.ProfileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProfileService {

    ProfileMapper profileMapper;
    ProfileRepository profileRepository;
    FileService fileService;

    public ProfileResponse createProfile(ProfileCreationRequest request) {
        if (profileRepository.existsByUserId(request.getUserId())) {
            throw new AppException(ErrorCode.PROFILE_EXISTED);
        }

        var profile = profileMapper.toProfile(request);
        profile = profileRepository.save(profile);

        return profileMapper.toProfileResponse(profile);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<ProfileResponse> getAll() {
        return profileRepository.findAll().stream()
                .map(profileMapper::toProfileResponse)
                .toList();
    }

    public ProfileResponse getMyProfile() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        var profile = profileRepository.findByUserId(userId).orElseThrow(
                () -> new AppException(ErrorCode.PROFILE_NOT_FOUND)
        );

        return profileMapper.toProfileResponse(profile);
    }

    public ProfileResponse uploadAvatar(MultipartFile multipartFile) throws Exception {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        var profile = profileRepository.findByUserId(userId).orElseThrow(
                () -> new AppException(ErrorCode.PROFILE_NOT_FOUND));

        var avatar = fileService.uploadFile(multipartFile);

        profile.setAvatar(avatar.getUrl());

        return profileMapper.toProfileResponse(profileRepository.save(profile));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteProfile(String userId) {
        profileRepository.deleteByUserId(userId);
    }
}
