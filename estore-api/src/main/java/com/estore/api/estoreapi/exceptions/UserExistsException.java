package com.estore.api.estoreapi.exceptions;

/**
 * exception that is thrown if a user is attempted to be created when 
 * one with the same username exists
 */
public class UserExistsException extends Exception {

    /**
     * creates a UserExistsException
     * 
     * @param errorMessage the errormessage to display
     */
    public UserExistsException(String errorMessage) {
        super(errorMessage); 
    }

}
