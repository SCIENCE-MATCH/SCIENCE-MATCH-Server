package com.sciencematch.sciencematch.exception.model;

import com.sciencematch.sciencematch.exception.ErrorStatus;

public class LogoutRefreshtokenException  extends  CustomException{

    public LogoutRefreshtokenException(ErrorStatus errorStatus,
        String message) {
        super(errorStatus, message);
    }
}
