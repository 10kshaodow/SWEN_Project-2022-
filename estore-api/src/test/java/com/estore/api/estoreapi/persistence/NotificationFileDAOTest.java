package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.Notification;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NotificationFileDAOTest {

    @BeforeAll
    static public void setup() throws IOException {
        NotificationFileDAO notificationFileDAO = new NotificationFileDAO("data/testNotifications.json",
                new ObjectMapper());
        Notification[] notis = notificationFileDAO.getAllNotifications();

        for (Notification noti : notis) {
            notificationFileDAO.deleteNotification(noti.getId());
        }
    }

    @Test
    public void testCreateNotification() throws Exception {
        NotificationFileDAO notificationFileDAO = createDao();
        Notification expected = createNotification(1);

        try {
            Notification created = notificationFileDAO.createNotification(expected);

            assertEquals(expected, created);
        } catch (Exception e) {
            assert (false);
        }
    }

    @Test
    public void testGetNotification() {
        try {
            NotificationFileDAO notificationFileDAO = createDao();
            Notification expected = createNotification(1);

            Notification created = notificationFileDAO.createNotification(expected);

            assertEquals(expected, notificationFileDAO.getNotification(created.getId()));

            assertNull(notificationFileDAO.getNotification(-1));

        } catch (Exception e) {
            assert (false);
        }
    }

    @Test
    public void testDeleteNotification() {
        try {
            NotificationFileDAO notificationFileDAO = createDao();
            assertTrue(notificationFileDAO.deleteNotification(1));
            assertFalse(notificationFileDAO.deleteNotification(5));

        } catch (Exception e) {
            assert (false);
        }
    }

    @Test
    public void testGetAllNotifications() throws Exception {
        NotificationFileDAO notificationFileDAO = createDao();
        try {
            assert (notificationFileDAO.getAllNotifications() != null);

        } catch (IOException ioException) {
            assert (false);
        }

    }

    private Notification createNotification(int id) {
        return new Notification(id, "Test-Notification-" + id, "Test-Notification-Message-" + id);
    }

    private NotificationFileDAO createDao() throws IOException {
        return new NotificationFileDAO("data/testNotifications.json", new ObjectMapper());
    }
}
