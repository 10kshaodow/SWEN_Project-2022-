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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estore.api.estoreapi.model.*;
import com.estore.api.estoreapi.persistence.INotificationDAO;
import com.estore.api.estoreapi.persistence.IUserDAO;

/**
 * Handles the REST API requests for the Notification resource
 * 
 * @author SWEN 05 Team 4D - Big Development
 */
@RestController
@RequestMapping("notifications")
public class NotificationController {
    private static final Logger LOG = Logger.getLogger(UserController.class.getName());

    private INotificationDAO notificationDao;
    private final SimpMessagingTemplate template; 


    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param notificationDao The {@link productDAO notification Data Access Object} to
     *                   perform CRUD operations
     */
    @Autowired
    public NotificationController(INotificationDAO notificationDao, IUserDAO userDAO, SimpMessagingTemplate template) {
        this.notificationDao = notificationDao;
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
     * Gets the entire notifications array from the DAO
     * 
     * @return ResponseEntity with a okay status and the notifications array or
     *         a ResponseEntity with a Internal Server Error if something went wrong
     *         loading the data
     */
    @GetMapping("")
    public ResponseEntity<Notification[]> getAllNotifications() {
        LOG.info("GET /notifications");
        try {
            Notification[] notifications = notificationDao.getAllNotifications();
            return new ResponseEntity<Notification[]>(notifications, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return this.errorHandler(e);
        }
    }

    /**
     * Retrieves a notification with the given id
     * 
     * @param id The id of the notification to get
     * 
     * @return a notification object with the matching id
     *         <br>
     *         null if no notification with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotification(@PathVariable int id) {
        LOG.info("GET /notifications/" + id);
        try {
            Notification notification = notificationDao.getNotification(id);
            if (notification != null)
                return new ResponseEntity<Notification>(notification, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

     /**
     * Deletes a notification with a given id
     * 
     * @param notification - The notification to create
     * 
     * @return a notification object with the matching id
     *         <br>
     *         will be removed from the notification list
     * 
     * @throws IOException if an issue with underlying storage, such as an invalid id. 
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Notification> deleteNotification(@PathVariable int id) {
        LOG.info("DELETE /notifications/" + id);
        try {
            Boolean notification_void = notificationDao.deleteNotification(id);
            if (notification_void == false)
                return new ResponseEntity<>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a notification with the provided notification object
     * 
     * @param notification - The notification to create
     * 
     * @return ResponseEntity with created notification object and HTTP
     *         status of CREATED<br>
     *         ResponseEntity with HTTP status of CONFLICT if {@link Notification
     *         notification} object already exists<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) {
        LOG.info("POST /notifications " + notification);
        try {
            Notification newNotification = notificationDao.createNotification(notification);
            if (newNotification != null) {
                // this.template.convertAndSend("/topic", "New Notification");
                return new ResponseEntity<Notification>(newNotification, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } 
    }
}

