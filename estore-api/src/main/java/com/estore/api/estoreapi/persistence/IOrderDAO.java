package com.estore.api.estoreapi.persistence;

import java.io.IOException;

//import com.estore.api.estoreapi.controller.OrderController;
import com.estore.api.estoreapi.model.Order;

/**
 * Defines the interface for Order object persistence
 * 
 * @author SWEN 05 Team 4D - Big Development
 * 
 */
public interface IOrderDAO {

    // define your functions here

    /**
     * Retrieves all Orders
     * 
     * @return An array of Order objects, may be empty
     * 
     */
    Order[] getAllOrders() throws IOException;


    /**
     * Retrieves a {@linkplain Order order} with the given id
     * 
     * @param id The id of the {@link Order order} to get
     * 
     * @return a {@link Order order} object with the matching id
     * <br>
     * null if no {@link Order order} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Order getOrder(int id) throws IOException;

     /**
     * Creates and saves a {@linkplain Order order}
     * 
     * @param order {@linkplain Order order} object to be created and saved
     * <br>
     * The id of the order object is ignored and a new uniqe id is assigned
     *
     * @return new {@link Order order} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    Order createOrder(Order order) throws IOException;

    /**
     * Updates and saves a {@linkplain Order order}
     * 
     * @param {@link Order order} object to be updated and saved
     * 
     * @return updated {@link Order order} if successful, null if
     * {@link Order order} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Order updateOrder(Order order) throws IOException;


     /**
     * Deletes a order with the given id
     * 
     * @param id The id of the {@link Order order}
     * 
     * @return true if the {@link Order order} was deleted
     * <br>
     * false if Order with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteOrder(int id) throws IOException;



}
