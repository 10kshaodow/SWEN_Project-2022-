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
     * Retrieves a {@linkplain Hero hero} with the given id
     * 
     * @param id The id of the {@link Hero hero} to get
     * 
     * @return a {@link Hero hero} object with the matching id
     * <br>
     * null if no {@link Hero hero} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Product getProduct(int id) throws IOException;

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


     /**
     * Deletes a product with the given id
     * 
     * @param id The id of the {@link Product product}
     * 
     * @return true if the {@link Product product} was deleted
     * <br>
     * false if Product with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteProduct(int id) throws IOException;



}
