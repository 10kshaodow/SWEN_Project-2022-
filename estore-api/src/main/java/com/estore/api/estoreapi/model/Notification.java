package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Notification {
    @JsonProperty("id")
    private int id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("message")
    private String message;

    /**
     * Create a Notification
     * 
     * @param id          id of the Notification
     * @param title       title of the Notification
     * @param message     message for the Notification
     * @param unseenUsers list of users who have not seen this Notification
     * @param seenUsers   list of users who have seen this Notification
     */
    public Notification(@JsonProperty("id") int id,
            @JsonProperty("title") String title,
            @JsonProperty("message") String message) {
        this.id = id;
        this.title = title;
        this.message = message;
    }

    /**
     * get the id of this object
     * 
     * @return id
     */
    public int getId() {
        return this.id;
    }

    /**
     * get the title of the notification
     * 
     * @return title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * set the title of this notification
     * 
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * get the message of this notification
     * 
     * @return message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * set the message of this notification
     * 
     * @param message message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * converts a user to a string
     */
    @Override
    public String toString() {
        return "Notification [id=" + this.id +
                ", title=" + this.title +
                ", message=" + this.message + "]";
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
        if (!(other instanceof Notification))
            return false;
        Notification o = (Notification) other;
        return this.id == o.id &&
                this.title.equals(o.title) &&
                this.message.equals(o.message);
    }

}
