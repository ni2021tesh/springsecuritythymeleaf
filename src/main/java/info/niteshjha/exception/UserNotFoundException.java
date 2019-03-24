// Copyright (c) 2018 Travelex Ltd

package info.niteshjha.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String s) {
        super(s);
    }
}
