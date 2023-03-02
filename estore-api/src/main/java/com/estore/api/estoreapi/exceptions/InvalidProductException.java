package com.estore.api.estoreapi.exceptions;

/**
 * exception that is thrown if a user is attempted to be created when 
 * one with the same username exists
 */
public class InvalidProductException extends Exception {

    /**
     * creates a InvalidProductException
     * 
     * @param errorMessage the errormessage to display
     */
    public InvalidProductException(String errorMessage) {
        super(errorMessage); 
    }

}
