package com.huumod.ecommerce.authentication.controller;

import com.huumod.ecommerce.authentication.dto.request.AuthenticationRequest;
import com.huumod.ecommerce.authentication.dto.request.IntrospectRequest;
import com.huumod.ecommerce.authentication.dto.request.JwtRequest;
import com.huumod.ecommerce.authentication.dto.response.AuthenticationResponse;
import com.huumod.ecommerce.authentication.dto.response.IntrospectResponse;
import com.huumod.ecommerce.authentication.service.AuthenticationService;
import com.huumod.ecommerce.common.ApiResponse;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AuthenticationService authenticationService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        return ApiResponse.<AuthenticationResponse>builder()
                .results(authenticationService.authenticate(request))
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        return ApiResponse.<IntrospectResponse>builder()
                .results(authenticationService.introspect(request))
                .build();
    }

    @PostMapping("/refreshToken")
    ApiResponse<AuthenticationResponse> refreshToken(@RequestBody JwtRequest request)
            throws ParseException, JOSEException {
        return ApiResponse.<AuthenticationResponse>builder()
                .results(authenticationService.refreshToken(request))
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody JwtRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder()
                .message("logout successfully")
                .build();
    }
}
