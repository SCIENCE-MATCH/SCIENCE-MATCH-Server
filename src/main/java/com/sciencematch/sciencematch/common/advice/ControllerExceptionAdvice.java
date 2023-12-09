package com.sciencematch.sciencematch.common.advice;

import static com.sciencematch.sciencematch.exception.ErrorStatus.INVALID_TOKEN_INFO_EXCEPTION;

import com.sciencematch.sciencematch.common.dto.ApiResponseDto;
import com.sciencematch.sciencematch.exception.model.CustomException;
import io.lettuce.core.RedisCommandExecutionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RedisCommandExecutionException.class)
    protected ResponseEntity<Object> handleRedisCommandExecutionException(
        final RedisCommandExecutionException e) {
        return ResponseEntity.status(INVALID_TOKEN_INFO_EXCEPTION.getHttpStatus())
            .body(ApiResponseDto.error(INVALID_TOKEN_INFO_EXCEPTION.getHttpStatusCode(),
                INVALID_TOKEN_INFO_EXCEPTION.getMessage()));
    }

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<Object> handleCustomException(CustomException e) {
        return ResponseEntity.status(e.getHttpStatus())
            .body(ApiResponseDto.error(e.getErrorStatus(), e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        StringBuilder errMessage = new StringBuilder();

        for (FieldError error : result.getFieldErrors()) {
            errMessage.append("[")
                .append(error.getField())
                .append("] ")
                .append(":")
                .append(error.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ApiResponseDto.error(400, errMessage.toString()));
    }
}
