package com.sciencematch.sciencematch.exception.model;

import com.sciencematch.sciencematch.exception.ErrorStatus;

public class NotFoundException extends CustomException{

    public NotFoundException(ErrorStatus errorStatus,
        String message) {
        super(errorStatus, message);
    }
}
