package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.exceptions.InvalidProductException;
import com.estore.api.estoreapi.exceptions.ProductNameTakenException;
import com.estore.api.estoreapi.exceptions.ProductTypeChangeException;
import com.estore.api.estoreapi.model.products.Product;

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
     * Retrieves a {@linkplain Product product} with the given id
     * 
     * @param id The id of the {@link Product product} to get
     * 
     * @return a {@link Product product} object with the matching id
     *         <br>
     *         null if no {@link Product product} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Product getProduct(int id) throws IOException;

    /**
     * Finds all products whose properties contains the given text
     * 
     * @param containsText The text to match against
     * 
     * @return An array of products whose properties contains the given text, may be
     *         empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Product[] findProducts(String searchText) throws IOException;

    /**
     * Retrieves Products of a certain type
     * 
     * @param type the type of product to find
     * 
     * @return An array of Product objects of the given type
     * 
     * @throws IOException if an issue with underlying storage
     */
    Product[] getProductsOfType(int type) throws IOException;

    /**
     * Creates and saves a {@linkplain Product product}
     * 
     * @param product {@linkplain Product product} object to be created and saved
     *                <br>
     *                The id of the product object is ignored and a new uniqe id is
     *                assigned
     *
     * @return new {@link Product product} if successful, false otherwise
     * 
     * @throws IOException               if an issue with underlying storage
     * @throws ProductNameTakenException
     * @throws InvalidProductException
     */
    Product createProduct(Product product) throws IOException, ProductNameTakenException, InvalidProductException;

    /**
     * Updates and saves a {@linkplain Product product}
     * 
     * @param {@link Product product} object to be updated and saved
     * 
     * @return updated {@link Product product} if successful, null if
     *         {@link Product product} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Product updateProduct(Product product) throws IOException, ProductTypeChangeException, InvalidProductException;

    /**
     * Deletes a product with the given id
     * 
     * @param id The id of the {@link Product product}
     * 
     * @return true if the {@link Product product} was deleted
     *         <br>
     *         false if Product with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteProduct(int id) throws IOException;

}
