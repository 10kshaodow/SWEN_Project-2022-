package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.Notification;

/**
 * Defines the interface for Notification object persistence
 * 
 * @author SWEN 05 Team 4D - Big Development
 * 
 */
public interface INotificationDAO {

    /**
     * Retrieves all Notifications
     * 
     * @return An array of Notification objects, may be empty
     * 
     */
    Notification[] getAllNotifications() throws IOException;


    /**
     * Retrieves a {@linkplain Notification Notification} with the given id
     * 
     * @param id The id of the {@link Notification Notification} to get
     * 
     * @return a {@link Notification Notification} object with the matching id
     * <br>
     * null if no {@link Notification Notification} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Notification getNotification(int id) throws IOException;

     /**
     * Creates and saves a {@linkplain Notification Notification}
     * 
     * @param Notification {@linkplain Notification Notification} object to be created and saved
     * <br>
     * The id of the Notification object is ignored and a new uniqe id is assigned
     *
     * @return new {@link Notification Notification} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     * @throws NotificationNameTakenException
     * @throws InvalidNotificationException
     */
    Notification createNotification(Notification Notification) throws IOException;

     /**
     * Deletes a Notification with the given id
     * 
     * @param id The id of the {@link Notification Notification}
     * 
     * @return true if the {@link Notification Notification} was deleted
     * <br>
     * false if Notification with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteNotification(int id) throws IOException;



}
