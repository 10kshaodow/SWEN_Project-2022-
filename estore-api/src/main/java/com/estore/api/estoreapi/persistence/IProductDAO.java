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
     * Creates and saves a {@linkplain Product product}
     * 
     * @param product {@linkplain Product product} object to be created and saved
     * <br>
     * The id of the product object is ignored and a new uniqe id is assigned
     *
     * @return new {@link Product product} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    Product createProduct(Product product) throws IOException;

    /**
     * Updates and saves a {@linkplain Product product}
     * 
     * @param {@link Product product} object to be updated and saved
     * 
     * @return updated {@link Product product} if successful, null if
     * {@link Product product} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Product updateProduct(Product product) throws IOException;


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
