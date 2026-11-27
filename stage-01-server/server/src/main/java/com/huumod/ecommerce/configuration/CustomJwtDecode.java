package com.huumod.ecommerce.configuration;

import com.huumod.ecommerce.authentication.dto.request.IntrospectRequest;
import com.huumod.ecommerce.authentication.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import org.jspecify.annotations.NullMarked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import javax.crypto.spec.SecretKeySpec;
import java.util.Objects;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomJwtDecode implements JwtDecoder {

    @NonFinal
    @Value("${jwt.signerKey}")
    String jwtSignerKey;

    private NimbusJwtDecoder nimbusJwtDecoder;
    private final AuthenticationService authenticationService;

    @NullMarked
    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            var introspect = authenticationService.introspect(IntrospectRequest.builder()
                    .token(token)
                    .build());
            if (!introspect.isValid()) {
                throw new JwtException("Invalid token");
            }
        } catch (Exception e) {
            throw new JwtException(e.getMessage());
        }

        if (Objects.isNull(nimbusJwtDecoder)) {
            SecretKeySpec secretKeySpec = new SecretKeySpec(jwtSignerKey.getBytes(), "HmacSHA256");
            nimbusJwtDecoder = NimbusJwtDecoder
                    .withSecretKey(secretKeySpec)
                    .macAlgorithm(MacAlgorithm.HS256)
                    .build();
        }

        return nimbusJwtDecoder.decode(token);
    }
}
