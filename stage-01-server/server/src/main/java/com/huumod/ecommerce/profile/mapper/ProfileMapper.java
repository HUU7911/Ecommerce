package com.huumod.ecommerce.profile.mapper;

import com.huumod.ecommerce.profile.dto.request.ProfileCreationRequest;
import com.huumod.ecommerce.profile.dto.request.ProfileUpdateRequest;
import com.huumod.ecommerce.profile.dto.response.ProfileResponse;
import com.huumod.ecommerce.profile.entity.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    Profile toProfile(ProfileCreationRequest request);
    ProfileResponse toProfileResponse(Profile profile);
    void updateProfile(@MappingTarget Profile profile, ProfileUpdateRequest request);
}
