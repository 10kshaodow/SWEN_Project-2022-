package com.estore.api.estoreapi.exceptions;

public class ProductTypeChangeException extends Exception {

  /**
   * creates a ProductTypeChangeException
   * 
   * @param errorMessage the errormessage to display
   */
  public ProductTypeChangeException(String errorMessage) {
    super(errorMessage);
  }

}
