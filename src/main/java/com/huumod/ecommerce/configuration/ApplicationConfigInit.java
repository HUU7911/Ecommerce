package com.huumod.ecommerce.configuration;

import com.huumod.ecommerce.identity.constant.RoleDefine;
import com.huumod.ecommerce.identity.entity.Role;
import com.huumod.ecommerce.identity.entity.User;
import com.huumod.ecommerce.identity.repository.PermissionRepository;
import com.huumod.ecommerce.identity.repository.RoleRepository;
import com.huumod.ecommerce.identity.repository.UserRepository;
import com.huumod.ecommerce.profile.dto.request.ProfileCreationRequest;
import com.huumod.ecommerce.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationConfigInit {

    @NonFinal
    private final String USERNAME = "admin";

    @NonFinal
    private final String PASSWORD = "admin";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProfileService profileService;

    @Bean
    public ApplicationRunner applicationRunner(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        return args -> {
            if (roleRepository.findById(RoleDefine.USER.name()).isEmpty()) {
                roleRepository.save(Role.builder()
                                .name(RoleDefine.USER.name())
                                .description("has role users")
                        .build());

                var adminRole = roleRepository.save(Role.builder()
                        .name(RoleDefine.ADMIN.name())
                        .description("has role admin")
                        .build());

                Set<Role> roles = new HashSet<>();
                roles.add(adminRole);

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
        };
    }
}
