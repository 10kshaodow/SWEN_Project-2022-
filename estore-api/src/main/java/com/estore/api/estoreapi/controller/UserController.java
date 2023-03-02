package com.estore.api.estoreapi.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estore.api.estoreapi.exceptions.DeleteAdminException;
import com.estore.api.estoreapi.exceptions.UserExistsException;
import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.persistence.IUserDAO;

/**
 * Handles the REST API requests for the User resource
 * 
 * @author SWEN 05 Team 4D - Big Development
 */
@RestController
@RequestMapping("users")
public class UserController {
    private static final Logger LOG = Logger.getLogger(UserController.class.getName());

    private IUserDAO userDao;
    private final SimpMessagingTemplate template;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param userDao The {@link productDAO user Data Access Object} to
     *                perform CRUD operations
     */
    @Autowired
    public UserController(IUserDAO userDao, SimpMessagingTemplate template) {
        this.userDao = userDao;
        this.template = template;
    }

    /**
     * simple error handler for requests that may throw an error
     * logs to console and returns a response of specified type
     * 
     * @param <T> takes in a generalized type
     * @param e   takes in the error message
     * @return a response entity of the specified typ
     */
    private <T> ResponseEntity<T> errorHandler(Exception e) {
        return new ResponseEntity<T>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Gets the entire users array from the DAO
     * 
     * @return ResponseEntity with a okay status and the users array or
     *         a ResponseEntity with a Internal Server Error if something went wrong
     *         loading the data
     */
    @GetMapping("")
    public ResponseEntity<User[]> getAllUsers() {
        LOG.info("GET /users");

        try {
            User[] users = userDao.getAllUsers();

            return new ResponseEntity<User[]>(users, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return this.errorHandler(e);
        }
    }

    /**
     * Retrieves a user with the given username
     * 
     * @param username The username of the user to get
     * 
     * @return a user object with the matching username
     *         <br>
     *         null if no user with a matching username is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        LOG.info("GET /users/" + username);

        try {
            User user = userDao.getUser(username);
            if (user != null)
                return new ResponseEntity<User>(user, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request to check if a username is taken
     * 
     * @param username the username to search for
     * 
     * @return ResponseEntity with True if the username is taken
     * 
     *         Example: checks if the username "fa" is unique
     *         GET http://localhost:8080/users/exists/fa
     */
    @GetMapping("/exists/{username}")
    public ResponseEntity<Boolean> userExists(@PathVariable String username) {
        LOG.info("GET /users/exists/" + username);
        try {
            boolean usernameTaken = userDao.userExists(username);
            return new ResponseEntity<Boolean>(usernameTaken, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a user with a given username
     * 
     * @param user - The user to create
     * 
     * @return a user object with the matching username
     *         <br>
     *         will be removed from the user list
     * 
     * @throws IOException if an issue with underlying storage, such as an invalid
     *                     username.
     */
    @DeleteMapping("/{username}")
    public ResponseEntity<User> deleteUser(@PathVariable String username) {
        LOG.info("DELETE /users/" + username);
        try {
            Boolean product_void = userDao.deleteUser(username);
            User user = userDao.getUser(username);
            if (product_void != false)
                return new ResponseEntity<User>(user, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (DeleteAdminException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Creates a user with the provided user object
     * 
     * @param user - The user to create
     * 
     * @return ResponseEntity with created user object and HTTP
     *         status of CREATED<br>
     *         ResponseEntity with HTTP status of CONFLICT if {@link User
     *         user} object already exists<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        LOG.info("POST /users " + user);
        try {
            User newUser = userDao.createUser(user);
            if (newUser != null)
                return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
            else
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (UserExistsException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    /**
     * Updates the user with the provided
     * user object, if it exists
     * 
     * @param user The user to update
     * 
     * @return ResponseEntity with updated user object and HTTP
     *         status of OK if updated<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        LOG.info("PUT /users " + user);
        try {
            User newUser = userDao.updateUser(user);
            if (newUser != null)
                return new ResponseEntity<User>(newUser, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{username}/{notificationId}")
    public ResponseEntity<User> addNotificationToUser(@PathVariable String username, @PathVariable int notificationId) {
        LOG.info("PUT /users/" + username + "/" + notificationId);
        try {
            User gotUser = userDao.addNotificationToUser(username, notificationId);
            if (gotUser != null) {
                // alert that a notification has been created
                this.template.convertAndSend("/topic", "New Notification");
                return new ResponseEntity<User>(gotUser, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
