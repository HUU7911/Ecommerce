package com.huumod.ecommerce.contract.configuration;

import com.huumod.ecommerce.contract.constant.PermissionDefine;
import com.huumod.ecommerce.contract.exception.AppException;
import com.huumod.ecommerce.contract.exception.ErrorCode;
import com.huumod.ecommerce.contract.constant.RoleDefine;
import com.huumod.ecommerce.identity.entity.Permission;
import com.huumod.ecommerce.identity.entity.Role;
import com.huumod.ecommerce.identity.entity.User;
import com.huumod.ecommerce.identity.repository.PermissionRepository;
import com.huumod.ecommerce.identity.repository.RoleRepository;
import com.huumod.ecommerce.identity.repository.UserRepository;
import com.huumod.ecommerce.profile.dto.request.ProfileCreationRequest;
import com.huumod.ecommerce.profile.service.ProfileService;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationConfigInit {

    @NonFinal
    @Value("minio.config.bucket")
    private final String BUCKET;

    @NonFinal
    private final String USERNAME = "admin";

    @NonFinal
    private final String PASSWORD = "admin";

    @NonFinal
    private final String[] PermissionName = {
            "READ",
            "UPLOAD",
            "POST",
            "UPDATE_POST",
            "DELETE",
            "PAID",
            "CANCEL",
    };

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProfileService profileService;

    @Bean
    public ApplicationRunner applicationRunner(
            RoleRepository roleRepository,
            PermissionRepository permissionRepository,
            MinioClient minioClient
    ) {
        return _ -> {

            // Permission manager
            for (String permission : PermissionName) {
                if (Objects.nonNull(permission)) {
                    permissionRepository.save(Permission.builder()
                                    .name(permission)
                                    .description("has permission " + permission)
                            .build());
                }

                var roleAdmin = roleRepository.findById(RoleDefine.ADMIN.name()).orElseThrow(
                        () -> new AppException(ErrorCode.UNAUTHENTICATED)
                );

                Set<Permission> permissions = new HashSet<>(
                        permissionRepository.findAllById(List.of(PermissionName)));
                roleAdmin.setPermissions(permissions);
            }

            // Role manager
            if (roleRepository.findById(RoleDefine.USER.name()).isEmpty()) {
                // Role User
                var roleUser = roleRepository.save(Role.builder()
                                .name(RoleDefine.USER.name())
                                .description("has role users")
                        .build());

                Set<Permission> permissionUser = new HashSet<>(
                        permissionRepository.findAllById(
                                Collections.singleton(PermissionDefine.READ.name())));
                roleUser.setPermissions(permissionUser);

                // Role staff
                var roleStaff = roleRepository.save(Role.builder()
                        .name(RoleDefine.STAFF.name())
                        .description("has role staff")
                        .build());

                Set<Permission> permissions = new HashSet<>(
                        permissionRepository.findAllById(List.of(PermissionName)));
                roleStaff.setPermissions(permissions);

                // Role admin
                var adminRole = roleRepository.save(Role.builder()
                        .name(RoleDefine.ADMIN.name())
                        .description("has role admin")
                        .build());

                Set<Role> roles = new HashSet<>();
                roles.add(adminRole);

                // User Admin
                User user = User.builder()
                        .username(USERNAME)
                        .password(passwordEncoder.encode(PASSWORD))
                        .firstName("tran")
                        .lastName("huu")
                        .email("trihuutran4@gmail.com")
                        .birthDate(LocalDate.of(2006, 4, 5))
                        .roles(roles)
                        .build();
                user = userRepository.save(user);

                profileService.createProfile(ProfileCreationRequest.builder()
                        .userId(user.getId())
                        .username(user.getUsername())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .birthDate(user.getBirthDate())
                        .build());
                log.info("user has been saved");
            }
            log.info("user has been created");

            // MinIO
            try {
                boolean existed = minioClient.bucketExists(BucketExistsArgs.builder()
                                .bucket(BUCKET)
                        .build());

                if (!existed) {
                    minioClient.makeBucket(MakeBucketArgs.builder()
                                    .bucket(BUCKET)
                            .build());
                } else {
                    throw new AppException(ErrorCode.BUCKET_EXISTED);
                }
            } catch (Exception e) {
                throw new AppException(ErrorCode.BUCKET_EXISTED);
            }
        };
    }
}
