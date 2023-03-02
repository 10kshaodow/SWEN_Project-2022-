package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.exceptions.DeleteAdminException;
import com.estore.api.estoreapi.exceptions.UserExistsException;
import com.estore.api.estoreapi.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Implements the functionality for JSON file-based peristance for Products
 * 
 * @author SWEN Faculty
 */
@Component
public class UserFileDAO implements IUserDAO {

    // local cache of users
    Map<String, User> users;

    // provides conversion between User object and their JSON representation
    private ObjectMapper objectMapper;

    // Filename to read from and write to
    private String filename;

    /**
     * Creates a User File Data Access Object
     * 
     * @param filename     Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization
     *                     and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public UserFileDAO(@Value("${users.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        // load users from the file
        load();
    }

    /**
     * Saves the users from the map into the file as an array of JSON objects
     * 
     * @return true if the users were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        User[] userArray = this.getUsersArray();

        // Serializes the users to JSON format and write to a file
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.writeValue(new File(filename), userArray);
        return true;
    }

    /**
     * Loads users from the JSON file into the map
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        users = new TreeMap<>();

        // Deserializes the JSON objects from the file into an array of users
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        User[] userArray = objectMapper.readValue(new File(filename), User[].class);

        // Add each user to the tree map and keep track of the greatest id
        for (User user : userArray) {
            users.put(user.getUsername(), user);
        }
        return true;
    }

    /**
     * Collects all the values from the tree of users
     * and puts them into an array of users
     * 
     * @returns array of User objects, could be empty
     */
    private User[] getUsersArray() {
        ArrayList<User> users = new ArrayList<>();

        for (User user : this.users.values()) {
            users.add(user);
        }

        User[] productArray = new User[users.size()];
        users.toArray(productArray);
        return productArray;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUser(String username) throws IOException {
        synchronized (users) {
            if (users.containsKey(username))
                return users.get(username);
            else
                return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User[] getAllUsers() throws IOException {
        synchronized (users) {
            return this.getUsersArray();
        }
    };

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean userExists(String username) throws IOException {
        synchronized (users) {
            return this.users.containsKey(username);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User createUser(User user) throws IOException, UserExistsException {
        if (userExists(user.username))
            throw new UserExistsException(
                    "The username {" + user.username + "} already exists. Users must have unique usernames.");
        synchronized (users) {
            // We create a new user object because the id field is immutable
            // and we need to assign the next unique id
            User newUser = new User(user.getUsername(), user.getName(), user.getCreditCard(), user.getType(),
                    user.getAddress(), user.getOrderHistory(), user.getCart(), user.getNotifications(),
                    user.getNotificationHistory());
            users.put(newUser.getUsername(), newUser);
            save(); // may throw an IOException
            return newUser;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User updateUser(User user) throws IOException {
        synchronized (users) {
            if (users.containsKey(user.getUsername()) == false)
                return null; // user does not exist

            users.put(user.getUsername(), user);
            save(); // may throw an IOException
            return user;
        }
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public User addNotificationToUser(String username, int id) throws IOException {
        synchronized (users) {
            if (users.containsKey(username) == false)
                return null;

            User user = users.get(username);
            user.addNotification(id);
            users.put(username, user);
            save();
            return user;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteUser(String username) throws IOException, DeleteAdminException {
        synchronized (users) {
            if (username.equals("admin"))
                throw new DeleteAdminException("Cannot delete the admin user.");
            if (users.containsKey(username)) {
                users.remove(username);
                return save();
            } else
                return false;
        }
    }

}
