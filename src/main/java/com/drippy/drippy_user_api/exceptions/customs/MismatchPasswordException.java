package com.drippy.drippy_user_api.exceptions.customs;

public class MismatchPasswordException extends RuntimeException {
    public MismatchPasswordException() {
        super("password and confirmation password do not match");
    }
}
