package com.sciencematch.sciencematch.exception.model;

import com.sciencematch.sciencematch.exception.ErrorStatus;

public class ExistEmailException extends CustomException{

    public ExistEmailException(ErrorStatus errorStatus,
        String message) {
        super(errorStatus, message);
    }
}
