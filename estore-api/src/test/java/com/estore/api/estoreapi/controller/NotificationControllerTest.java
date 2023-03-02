package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.estore.api.estoreapi.model.Notification;
import com.estore.api.estoreapi.persistence.INotificationDAO;
import com.estore.api.estoreapi.persistence.IUserDAO;
import com.estore.api.estoreapi.persistence.NotificationFileDAO;
import com.estore.api.estoreapi.persistence.UserFileDAO;

public class NotificationControllerTest {

    private Notification testNotification;

    private Notification Notification1;
    private Notification Notification2;
    private INotificationDAO notificationDao;
    private IUserDAO userDAO;
    private SimpMessagingTemplate simple;

    @BeforeEach
    void setup() {
        this.notificationDao = Mockito.mock(NotificationFileDAO.class);
        this.userDAO = Mockito.mock(UserFileDAO.class);
        this.simple = Mockito.mock(SimpMessagingTemplate.class);
        this.testNotification = new Notification(1, "test_Title", "test message");
        this.Notification1 = new Notification(2, "test_Title2", "test message2");
        this.Notification2 = new Notification(3, "test_Title3", "test message3");

    }

    @Test
    void testCreateNotification() throws IOException {
        NotificationController test = new NotificationController(notificationDao, userDAO, simple);

        when(notificationDao.createNotification(testNotification)).thenReturn(testNotification);
        assertEquals(test.createNotification(testNotification).getStatusCode(), HttpStatus.CREATED);

        when(notificationDao.createNotification(Notification1)).thenReturn(null);
        assertEquals(HttpStatus.CONFLICT, test.createNotification(Notification1).getStatusCode());

        when(notificationDao.createNotification(Notification2)).thenThrow(new IOException());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, test.createNotification(Notification2).getStatusCode());

    }

    @Test
    void testDeleteNotification() throws IOException {

        NotificationController test = new NotificationController(notificationDao, userDAO, simple);

        when(notificationDao.deleteNotification(1345)).thenReturn(false);
        assertEquals(test.deleteNotification(testNotification.getId()).getStatusCode(),
                HttpStatus.OK);

        when(notificationDao.deleteNotification(testNotification.getId())).thenReturn(true);
        assertEquals(test.deleteNotification(testNotification.getId()).getStatusCode(),
                HttpStatus.OK);

        when(notificationDao.deleteNotification(1345)).thenAnswer((answer) -> {
            throw new IOException("Test Should Fail");
        });
        assertEquals(test.deleteNotification(1345).getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Test
    void testGetAllNotifications() throws IOException {

        NotificationController test = new NotificationController(notificationDao, userDAO, simple);

        when(notificationDao.getAllNotifications()).thenReturn(new Notification[] { testNotification, Notification1 });
        assertEquals(HttpStatus.OK, test.getAllNotifications().getStatusCode());

        when(notificationDao.getAllNotifications()).thenThrow(new IOException());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, test.getAllNotifications().getStatusCode());

    }

    @Test
    void testGetNotification() throws IOException {
        NotificationController test = new NotificationController(notificationDao, userDAO, simple);

        when(notificationDao.getNotification(testNotification.getId())).thenReturn(testNotification);
        assertEquals(HttpStatus.OK, test.getNotification(testNotification.getId()).getStatusCode());

        when(notificationDao.getNotification(Notification1.getId())).thenReturn(null);
        assertEquals(HttpStatus.OK, test.getNotification(Notification1.getId()).getStatusCode());

        when(notificationDao.getNotification(Notification2.getId())).thenAnswer(answer -> {
            throw new IOException();
        });
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, test.getNotification(Notification2.getId()).getStatusCode());

    }
}
