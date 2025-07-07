package com.pm.patient_service.exception;

public class EmailAlreadyExistsExecption extends RuntimeException {
    public EmailAlreadyExistsExecption(String message) {
        super(message);
    }
}
