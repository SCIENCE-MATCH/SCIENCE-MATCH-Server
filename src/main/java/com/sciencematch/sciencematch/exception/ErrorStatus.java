package com.sciencematch.sciencematch.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorStatus {
    /*
     * 400 BAD_REQUEST
     */
    VALIDATION_EXCEPTION(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INVALID_TOKEN_INFO_EXCEPTION(HttpStatus.BAD_REQUEST, "토큰 혹은 만료시간 설정이 잘못되었습니다."),

    /**
     * 401 UNAUTHORIZED
     */
    INVALID_ACCESS_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "유효하지 않은 액세스 토큰입니다."),
    REFRESH_TOKEN_TIME_EXPIRED_EXCEPTION(HttpStatus.UNAUTHORIZED, "만료된 리프레시 토큰입니다."),
    LOGOUT_REFRESH_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "로그아웃 하여 리프레시 토큰이 존재하지 않는 상태입니다."),

    /**
     * 403 FORBIDDEN
     */

    FORBIDDEN_USER_EXCEPTION(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
