package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.controller.ProductController;
import com.estore.api.estoreapi.model.Product;

/**
 * Defines the interface for Product object persistence
 * 
 * @author SWEN 05 Team 4D - Big Development
 * 
 */
public interface IProductDAO {

    // define your functions here

    /**
     * Retrieves all Products
     * 
     * @return An array of Product objects, may be empty
     * 
     */
    Product[] getAllProducts() throws IOException;

    /**
     * Finds all products whose properties contains the given text
     * 
     * @param containsText The text to match against
     * 
     * @return An array of products whose properties contains the given text, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Product[] findProducts(String searchText) throws IOException;



}
