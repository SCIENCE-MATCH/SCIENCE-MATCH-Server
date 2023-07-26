package com.sciencematch.sciencematch.exception.model;

import com.sciencematch.sciencematch.exception.ErrorStatus;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final ErrorStatus errorStatus;

    public CustomException(ErrorStatus errorStatus, String message) {
        super(message);
        this.errorStatus = errorStatus;
    }

    public Integer getHttpStatus() {
        return errorStatus.getHttpStatusCode();
    }
}
