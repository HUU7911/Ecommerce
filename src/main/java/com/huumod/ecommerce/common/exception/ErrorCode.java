package com.huumod.ecommerce.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ErrorCode {

    UNCATEGORIZED_EXCEPTION(9999, "uncategorized exception", HttpStatus.UNAUTHORIZED),
    UNAUTHENTICATED(1001, "unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1002, "unauthorized", HttpStatus.UNAUTHORIZED),
    USER_EXISTS(1003, "user already exists", HttpStatus.CONFLICT),
    USER_NOT_FOUND(1004, "user not found", HttpStatus.NOT_FOUND),
    INVALID_KEY(1005, "invalid key", HttpStatus.BAD_REQUEST),
    PROFILE_EXISTED(1006, "profile existed", HttpStatus.CONFLICT),
    PROFILE_NOT_FOUND(1007, "profile not found", HttpStatus.NOT_FOUND)
    ;

    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;
}
