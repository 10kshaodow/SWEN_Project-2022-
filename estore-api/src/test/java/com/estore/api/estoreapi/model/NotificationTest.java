package com.estore.api.estoreapi.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class NotificationTest {
    @Test
    void testEquals() {
        Notification notification1 = new Notification(3, "Test_Title", "test message");
        Notification notification2 = new Notification(3, "Test_Title", "test message");
        assertNotEquals(notification1, new Object());
        assertEquals(notification1, notification2);
        assertEquals(notification1, notification1);
        notification1.setTitle("new_title");
        assertNotEquals(notification1, notification2);
        notification1.setTitle("Test_Title");
        notification2.setMessage("new message");
        assertNotEquals(notification1, notification2);

    }

    @Test
    void testGetId() {
        Notification notification = new Notification(3, null, null);
        assertEquals(3, notification.getId());

    }

    @Test
    void testGetMessage() {
        Notification notification = new Notification(3, null, "test message");
        assertEquals("test message", notification.getMessage());

    }
   

    @Test
    void testGetTitle() {
        Notification notification = new Notification(3, "Test_Title", null);
        assertEquals("Test_Title", notification.getTitle());

    }

    @Test
    void testSetMessage() {
        Notification notification = new Notification(0, null, null);
        String test = "test message";
        notification.setMessage(test);
        assertEquals(test, notification.getMessage());

    }

    @Test
    void testSetTitle() {
        Notification notification = new Notification(0, null, null);
        String test = "Title_Title";
        notification.setTitle(test);
        assertEquals(test, notification.getTitle());
    }

    @Test
    void testToString() {
        Notification notification = new Notification(3, "Test_Title", "test message");

        String expected = "Notification [id=" + notification.getId() +
        ", title=" + notification.getTitle() +
        ", message=" + notification.getMessage()+ "]";

        String actual = notification.toString();
        assertEquals(expected, actual);
    
    }
}
