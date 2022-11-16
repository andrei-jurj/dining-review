package com.aj.diningreview.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String name) {
        super("Could not find user " + name);
    }
}
