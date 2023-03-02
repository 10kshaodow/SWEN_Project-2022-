package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Notification;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Implements the functionality for JSON file-based peristance for notifications
 * 
 * @author SWEN Faculty
 */
@Component
public class NotificationFileDAO implements INotificationDAO {

    // local cache of notifications
    Map<Integer, Notification> notifications;

    // provides conversion between Notification object and their JSON representation
    private ObjectMapper objectMapper;

    // next Id to assign
    private static int nextId;

    // Filename to read from and write to
    private String filename;

    /**
     * Creates a Notification File Data Access Object
     * 
     * @param filename     Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization
     *                     and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public NotificationFileDAO(@Value("${notifications.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        // load notifications from the file
        load();
    }

    /**
     * Generates the next id for a new {@linkplain Notification Notification}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Saves the notifications from the map into the file as an array of JSON objects
     * 
     * @return true if the notifications were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Notification[] NotificationArray = this.getNotificationsArray();

        for (int i = 0; i < NotificationArray.length; i++) {
            Notification Notification = NotificationArray[i];
            NotificationArray[i] = Notification;
        }

        // Serializes the notifications to JSON format and write to a file
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.writeValue(new File(filename), NotificationArray);
        return true;
    }

    /**
     * Loads notifications from the JSON file into the map and sets the next id to
     * the largest found id
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        notifications = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of notifications
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Notification[] NotificationArray = objectMapper.readValue(new File(filename), Notification[].class);

        // Add each Notification to the tree map and keep track of the greatest id
        for (Notification notification : NotificationArray) {
            notifications.put(notification.getId(), notification);
            if (notification.getId() > nextId)
                nextId = notification.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    /**
     * Collects all the values from the tree of notifications
     * and puts them into an array of notifications
     * 
     * @returns array of Notification objects, could be empty
     */
    private Notification[] getNotificationsArray() {
        ArrayList<Notification> notifications = new ArrayList<>();

        for (Notification Notification : this.notifications.values()) {
            notifications.add(Notification);
        }

        Notification[] NotificationArray = new Notification[notifications.size()];
        notifications.toArray(NotificationArray);
        return NotificationArray;
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Notification getNotification(int id) {
        synchronized (notifications) {
            if (notifications.containsKey(id))
                return notifications.get(id);
            else
                return null;
        }
    }

    /**
     * Retrieves all notifications
     * 
     * @return An array of Notification objects, may be empty
     * 
     */
    @Override
    public Notification[] getAllNotifications() throws IOException {
        synchronized (notifications) {
            return this.getNotificationsArray();
        }
    };

    /**
     ** {@inheritDoc}
     * 
     * @throws InvalidNotificationException
     */
    @Override
    public Notification createNotification(Notification notification) throws IOException {
        synchronized (notifications) {
            // We create a new Notification object because the id field is immutable
            // and we need to assign the next unique id
            Notification newNotification = new Notification(nextId(), notification.getTitle(), notification.getMessage());
            notifications.put(newNotification.getId(), newNotification);
            save(); // may throw an IOException
            return newNotification;
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public boolean deleteNotification(int id) throws IOException {
        synchronized (notifications) {
            if (notifications.containsKey(id)) {
                notifications.remove(id);
                return save();
            } else
                return false;
        }
    }

}
