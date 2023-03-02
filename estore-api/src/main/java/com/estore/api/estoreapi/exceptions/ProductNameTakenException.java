package com.estore.api.estoreapi.exceptions;

/**
 * exception that is thrown if a user is attempted to be created when 
 * one with the same username exists
 */
public class ProductNameTakenException extends Exception {

    /**
     * creates a ProductNameTakenException
     * 
     * @param errorMessage the errormessage to display
     */
    public ProductNameTakenException(String errorMessage) {
        super(errorMessage); 
    }

}
