package com.huumod.ecommerce.authentication.service;

import com.huumod.ecommerce.authentication.dto.request.AuthenticationRequest;
import com.huumod.ecommerce.authentication.dto.request.IntrospectRequest;
import com.huumod.ecommerce.authentication.dto.request.JwtRequest;
import com.huumod.ecommerce.authentication.dto.response.AuthenticationResponse;
import com.huumod.ecommerce.authentication.dto.response.IntrospectResponse;
import com.huumod.ecommerce.common.exception.AppException;
import com.huumod.ecommerce.common.exception.ErrorCode;
import com.huumod.ecommerce.identity.entity.InvalidateToken;
import com.huumod.ecommerce.identity.entity.User;
import com.huumod.ecommerce.identity.repository.InvalidateTokenRepository;
import com.huumod.ecommerce.identity.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

    @NonFinal
    @Value("${jwt.signerKey}")
    String jwtSignerKey;

    @NonFinal
    @Value("${jwt.valid-duration}")
    int jwtValidDuration;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    int jwtRefreshableDuration;

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    InvalidateTokenRepository invalidateTokenRepository;

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws AppException {
        var user = (request.getName().contains("@"))
                ? userRepository.findByEmail(request.getName()).orElse(null)
                : userRepository.findByUsername(request.getName()).orElse(null);

        var authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if  (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        var token = generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    public AuthenticationResponse refreshToken(JwtRequest request) throws AppException, ParseException, JOSEException {
        var sigJwt = verifyToken(request.getToken(), true);

        String jti = sigJwt.getJWTClaimsSet().getJWTID();
        Date expiryTime = sigJwt.getJWTClaimsSet().getExpirationTime();

        InvalidateToken invalidateToken = InvalidateToken.builder()
                .id(jti)
                .expiryTime(expiryTime)
                .build();
        invalidateTokenRepository.save(invalidateToken);

        String userId = sigJwt.getJWTClaimsSet().getSubject();

        var user = userRepository.findById(userId).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );

        String token = generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    public void logout(JwtRequest request) throws AppException, ParseException {
        try {
            var sigJwt = verifyToken(request.getToken(), true);

            String jti = sigJwt.getJWTClaimsSet().getJWTID();
            Date expiryTime = sigJwt.getJWTClaimsSet().getExpirationTime();

            InvalidateToken invalidateToken = InvalidateToken.builder()
                    .id(jti)
                    .expiryTime(expiryTime)
                    .build();
            invalidateTokenRepository.save(invalidateToken);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    public IntrospectResponse introspect(IntrospectRequest request)
            throws AppException, ParseException {
        var token = request.getToken();

        boolean valid = true;
        try {
            verifyToken(token, true);
        }catch (JOSEException e) {
            valid = false;
        }

        return IntrospectResponse.builder()
                .valid(valid)
                .build();
    }

    public String generateToken(User user) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getId())
                .issuer("huumod.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(jwtValidDuration, ChronoUnit.SECONDS)
                        .toEpochMilli())
                )
                .claim("scope", buildScope(user))
                .jwtID(UUID.randomUUID().toString())
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(jwtSignerKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    public SignedJWT verifyToken(String token, boolean invalidate) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(jwtSignerKey.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = (invalidate)
                ? new Date(signedJWT
                    .getJWTClaimsSet()
                    .getIssueTime()
                    .toInstant()
                    .plus(jwtRefreshableDuration, ChronoUnit.SECONDS)
                    .toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        if (invalidateTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(", ");

        if (!CollectionUtils.isEmpty(user.getRoles()))
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());
                if (!CollectionUtils.isEmpty(role.getPermissions()))
                    role.getPermissions().forEach(permission ->
                            stringJoiner.add(permission.getName()));
            });

        return stringJoiner.toString();
    }
}
