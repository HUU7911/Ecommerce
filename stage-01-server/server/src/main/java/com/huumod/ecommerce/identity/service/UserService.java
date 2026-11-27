package com.huumod.ecommerce.identity.service;

import com.huumod.ecommerce.common.event.NotificationEvent;
import com.huumod.ecommerce.common.exception.AppException;
import com.huumod.ecommerce.common.exception.ErrorCode;
import com.huumod.ecommerce.identity.constant.RoleDefine;
import com.huumod.ecommerce.identity.dto.request.UserCreationRequest;
import com.huumod.ecommerce.identity.dto.request.UserUpdateRequest;
import com.huumod.ecommerce.identity.dto.response.UserResponse;
import com.huumod.ecommerce.identity.entity.Role;
import com.huumod.ecommerce.identity.entity.User;
import com.huumod.ecommerce.identity.mapper.UserMapper;
import com.huumod.ecommerce.identity.repository.RoleRepository;
import com.huumod.ecommerce.identity.repository.UserRepository;
import com.huumod.ecommerce.profile.dto.request.ProfileCreationRequest;
import com.huumod.ecommerce.profile.service.ProfileService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserMapper userMapper;
    ProfileService profileService;
    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    KafkaTemplate<String, NotificationEvent> kafkaTemplate;

    @Transactional
    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.USER_EXISTS);
        }

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Set<Role> roles = new HashSet<>();
        roleRepository.findById(RoleDefine.USER.name()).ifPresent(roles::add);
        user.setRoles(roles);

        user = userRepository.save(user);

        profileService.createProfile(ProfileCreationRequest.builder()
                        .userId(user.getId())
                        .username(user.getUsername())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .birthDate(user.getBirthDate())
                .build());

        NotificationEvent event = NotificationEvent.builder()
                .chanel("EMAIL")
                .subject("created users")
                .body("Welcome to onetech phone" + user.getEmail())
                .recipient(user.getEmail())
                .build();

        try {
            kafkaTemplate.send("user-created", event);
        } catch (Exception e) {
            throw new AppException(ErrorCode.EMAIL_NOT_SEND);
        }

        return userMapper.toUserResponse(user);
    }

    @Transactional
    public UserResponse internalCreateUser(UserCreationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.USER_EXISTS);
        }

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Set<Role> roles = new HashSet<>();
        roleRepository.findById(RoleDefine.STAFF.name()).ifPresent(roles::add);
        user.setRoles(roles);

        user = userRepository.save(user);

        profileService.createProfile(ProfileCreationRequest.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .birthDate(user.getBirthDate())
                .build());

        NotificationEvent event = NotificationEvent.builder()
                .chanel("EMAIL")
                .subject("created users")
                .body("Welcome to onetech phone" + user.getEmail())
                .recipient(user.getEmail())
                .build();

        try {
            kafkaTemplate.send("user-created", event);
        } catch (Exception e) {
            throw new AppException(ErrorCode.EMAIL_NOT_SEND);
        }

        return userMapper.toUserResponse(user);
    }

    @Transactional
    public UserResponse updateUser(UserUpdateRequest request) {
        String userId = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        var user = userRepository.findById(userId).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND));

        userMapper.updateUser(user, request);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    public UserResponse getMyInfor() {
        String id = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        var user = userRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );

        return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUserById(String id) {
        userRepository.deleteById(id);
        profileService.deleteProfile(id);
    }
}
