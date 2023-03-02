package com.estore.api.estoreapi.model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a User
 * 
 * @author SWEN 05 Team 4D - Big Development
 * 
 */
public class User {
    @JsonProperty("username")
    public String username;
    @JsonProperty("name")
    public String name;
    @JsonProperty("creditCard")
    public CreditCard creditCard;
    @JsonProperty("type")
    public UserType type;
    @JsonProperty("address")
    public Address address;
    @JsonProperty("orderHistory")
    public ArrayList<String> orderHistory; // list of order id's
    @JsonProperty("cart")
    public Cart cart;
    @JsonProperty("notifications")
    private ArrayList<Integer> notifications;
    @JsonProperty("notificationHistory")
    private ArrayList<Integer> notificationHistory;

    /**
     * Create a user
     * 
     * @param username     The username of the user
     * @param name         The name of the user
     * @param creditCard   The payment info of the user
     * @param type         The access level of the user
     * @param address      The address of the user
     * @param orderHistory the user order history
     * @param cart
     * 
     */
    public User(@JsonProperty("username") String username,
            @JsonProperty("name") String name,
            @JsonProperty("creditCard") CreditCard creditCard,
            @JsonProperty("type") UserType type,
            @JsonProperty("address") Address address,
            @JsonProperty("orderHistory") ArrayList<String> orderHistory,
            @JsonProperty("cart") Cart cart,
            @JsonProperty("notifications") ArrayList<Integer> notifications,
            @JsonProperty("notificationHistory") ArrayList<Integer> notificationHistory) {
        this.username = username;
        this.name = name;
        this.creditCard = creditCard;
        this.type = type;
        this.address = address;
        this.orderHistory = orderHistory;
        this.cart = cart;
        this.notifications = notifications;
        this.notificationHistory = notificationHistory;
    }

    /**
     * Retrieves the id of the user
     * 
     * @return The id of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the name of the user - necessary for JSON object to Java object
     * deserialization
     * 
     * @param name The name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the name of the user
     * 
     * @return The name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the creditCard of the user - necessary for JSON object to Java object
     * deserialization
     * 
     * @param creditCard The creditCard of the user
     */
    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    /**
     * Retrieves the creditCard of the user
     * 
     * @return The creditCard of the user
     */
    public CreditCard getCreditCard() {
        return this.creditCard;
    }

    /**
     * gets the admin status
     * 
     * @return true if the user is an admin
     */
    public UserType getType() {
        return this.type;
    }

    /**
     * sets the admin status
     * 
     * @param the new status ttypeo set
     */
    public void setAdminStatus(UserType type) {
        this.type = type;
    }

    /**
     * gets the user address
     * 
     * @return the users address
     */
    public Address getAddress() {
        return this.address;
    }

    /**
     * sets the user address
     * 
     * @param address the new address for the user
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * gets the order history
     * 
     * @return orderHistory
     */
    public ArrayList<String> getOrderHistory() {
        return this.orderHistory;
    }

    /**
     * adds an item to the order history
     * 
     * @param orderId the id to add to the order history
     */
    public void addToOrderHistory(String orderId) {
        this.orderHistory.add(orderId);
    }

    /**
     * removes an item from the order history
     * 
     * @param orderId the id of the order to remove
     */
    public void removeFromOrderHistory(String orderId) {
        this.orderHistory.remove(orderId);
    }

    /**
     * Sets the cart
     * 
     * @param cart The order's id number
     */
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    /**
     * Retrieves the user's cart cart
     * 
     * @return The ID of the current order in the user's cart
     */
    public Cart getCart() {
        return this.cart;
    }

    /**
     * get the notifications for the user
     * 
     * @return the unseen notifications from the user
     */
    public ArrayList<Integer> getNotifications() {
        return this.notifications;
    }

    /**
     * add a notification to the user
     * 
     * @param id the notification id to add to the user
     */
    public void addNotification(int id) {
        if (this.notifications == null)
            this.notifications = new ArrayList<Integer>();
        this.notifications.add(id);
    }

    /**
     * get the notificationHistory for the user
     * 
     * @return the history of seen notifications from the user
     */
    public ArrayList<Integer> getNotificationHistory() {
        return this.notificationHistory;
    }

    /**
     * converts a user to a string
     */
    @Override
    public String toString() {
        return "User [username=" + this.username +
                ", name=" + this.name +
                ", creditCard=" + this.creditCard +
                ", type=" + this.type +
                ", address=" + this.address.toString() +
                ", orderHistory" + this.orderHistory.toString() + "]";
    }

    /**
     * checks to see if two user objects are equal
     * 
     * @param other the object to compare to this one
     * @return true if they are equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this)
            return true;
        if (!(other instanceof User))
            return false;
        User o = (User) other;
        return this.username.equals(o.username) &&
                this.name.equals(o.name) &&
                this.creditCard.equals(o.creditCard) &&
                this.type == o.type &&
                this.address.equals(o.address) &&
                this.orderHistory.equals(o.orderHistory) &&
                this.cart.equals(o.cart);
    }

}
