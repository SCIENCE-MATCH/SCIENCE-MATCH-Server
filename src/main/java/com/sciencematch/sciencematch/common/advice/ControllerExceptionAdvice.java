package com.sciencematch.sciencematch.common.advice;

import com.sciencematch.sciencematch.common.dto.ApiResponseDto;
import com.sciencematch.sciencematch.exception.ErrorStatus;
import com.sciencematch.sciencematch.exception.model.CustomException;
import io.lettuce.core.RedisCommandExecutionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RedisCommandExecutionException.class)
    protected ApiResponseDto handleRedisCommandExecutionException(final RedisCommandExecutionException e) {
        return ApiResponseDto.error(ErrorStatus.INVALID_TOKEN_INFO_EXCEPTION);
    }

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ApiResponseDto<?>> handleSophyException(CustomException e) {
        return ResponseEntity.status(e.getHttpStatus())
            .body(ApiResponseDto.error(e.getErrorStatus(), e.getMessage()));
    }
}
