package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.exceptions.*;
import com.estore.api.estoreapi.model.*;

/**
 * Defines the interface for User object persistence
 * 
 * @author SWEN 05 Team 4D - Big Development
 * 
 */
public interface IUserDAO {

    /**
     * Retrieves all Users
     * 
     * @return An array of User objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    User[] getAllUsers() throws IOException;

    /**
     * checks if a username exists
     * 
     * @param username the username to check
     * 
     * @return true if the username exists, false otherwise
     * 
     * @throws IOException if an issue with underlying storage
     */
    boolean userExists(String username) throws IOException; 

    /**
     * Retrieves a user with the given username
     * 
     * @param username The username of the user
     * 
     * @return a user with the matching username or null if not found
     * 
     * @throws IOException if an issue with underlying storage
     */
    User getUser(String username) throws IOException;

     /**
     * Creates and saves a user
     * 
     * @param user a user to be created and saved
     *
     * @return new if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    User createUser(User user) throws IOException, UserExistsException;

    /**
     * Updates and saves a {@linkplain User product}
     * 
     * @param user the user to be updated and saved
     * 
     * @return updated user if successful, null if not found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    User updateUser(User user) throws IOException;

    /**
     * adds a notification to a user
     * 
     * @param username the username to add the id
     * 
     * @param id the id of the notification to add
     * 
     * @return the updated user
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    User addNotificationToUser(String username, int id) throws IOException; 

     /**
     * Deletes a user with the given username
     * 
     * @param username The username of the user
     * 
     * @return true if the user was deleted, false if not found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteUser(String username) throws IOException, DeleteAdminException;

}

