package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;

// import java.io.IOException;
// import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.estore.api.estoreapi.exceptions.DeleteAdminException;
import com.estore.api.estoreapi.exceptions.UserExistsException;
import com.estore.api.estoreapi.model.Address;
import com.estore.api.estoreapi.model.Cart;
import com.estore.api.estoreapi.model.CreditCard;
import com.estore.api.estoreapi.model.OrderItem;
import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.persistence.IUserDAO;
import com.estore.api.estoreapi.persistence.UserFileDAO;

public class UserControllerTest {
        private User testUser;
        private User anotherUser;
        private User anotherUser2;
        private User anotherUser3;
        private IUserDAO userDao;
        private SimpMessagingTemplate simp;

        @BeforeEach
        void setup() {
                this.userDao = Mockito.mock(UserFileDAO.class);
                this.simp = Mockito.mock(SimpMessagingTemplate.class);
                CreditCard creditCard = new CreditCard("testnum", "testeyr", "testem",
                                "testcvv");
                Address address = new Address("teststreet", "testcity", "teststate",
                                "testcountry", "testzip");
                Cart cart = new Cart(new ArrayList<OrderItem>());
                this.testUser = new User("test_username", "test_name", creditCard, null,
                                address, new ArrayList<String>(), cart, new ArrayList<Integer>(),
                                new ArrayList<Integer>());
                this.anotherUser = new User("test_another_username",
                                "test_another_name", creditCard, null, address, new ArrayList<String>(), cart,
                                new ArrayList<Integer>(), new ArrayList<Integer>());
                this.anotherUser2 = new User("test_another_username_2",
                                "test_another_name_2", creditCard, null, address, new ArrayList<String>(),
                                cart, new ArrayList<Integer>(), new ArrayList<Integer>());
                this.anotherUser3 = new User("test_another_username_3",
                                "test_another_name_3", creditCard, null, address, new ArrayList<String>(),
                                cart, new ArrayList<Integer>(), new ArrayList<Integer>());
        }

        @Test
        void testCreateUser() throws IOException, UserExistsException {
                UserController test = new UserController(userDao, simp);

                when(userDao.createUser(testUser)).thenReturn(testUser);
                assertEquals(test.createUser(testUser).getStatusCode(), HttpStatus.CREATED);

                when(userDao.createUser(anotherUser)).thenReturn(null);
                assertEquals(HttpStatus.CONFLICT,
                                test.createUser(anotherUser).getStatusCode());

                when(userDao.createUser(anotherUser2)).thenThrow(new IOException());
                assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
                                test.createUser(anotherUser2).getStatusCode());

                when(userDao.createUser(anotherUser3)).thenThrow(new UserExistsException(""));
                assertEquals(HttpStatus.CONFLICT,
                                test.createUser(anotherUser3).getStatusCode());
        }

        @Test
        void testDeleteUser() throws IOException, DeleteAdminException {
                UserController test = new UserController(userDao, simp);

                when(userDao.deleteUser(testUser.username)).thenReturn(true);
                assertEquals(test.deleteUser(testUser.username).getStatusCode(),
                                HttpStatus.OK);

                when(userDao.deleteUser("")).thenReturn(false);
                assertEquals(test.deleteUser("").getStatusCode(), HttpStatus.OK);

                when(userDao.deleteUser("")).thenAnswer((answer) -> {
                        throw new IOException("Test Should Fail");
                });
                assertEquals(test.deleteUser("").getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);

                when(userDao.deleteUser(testUser.username)).thenAnswer((answer) -> {
                        throw new DeleteAdminException("Test Should Fail");
                });
                assertEquals(test.deleteUser(testUser.username).getStatusCode(),
                                HttpStatus.UNAUTHORIZED);
        }

        @Test
        void testGetAllUsers() throws IOException {
                UserController test = new UserController(userDao, simp);

                when(userDao.getAllUsers()).thenReturn(new User[] { testUser, anotherUser });
                assertEquals(HttpStatus.OK, test.getAllUsers().getStatusCode());

                when(userDao.getAllUsers()).thenThrow(new IOException());
                assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
                                test.getAllUsers().getStatusCode());
        }

        @Test
        void testGetUser() throws IOException {
                UserController test = new UserController(userDao, simp);

                when(userDao.getUser(testUser.username)).thenReturn(testUser);
                assertEquals(HttpStatus.OK,
                                test.getUser(testUser.getUsername()).getStatusCode());

                when(userDao.getUser(anotherUser.username)).thenReturn(null);
                assertEquals(HttpStatus.OK,
                                test.getUser(anotherUser.getUsername()).getStatusCode());

                when(userDao.getUser(anotherUser2.username)).thenThrow(new IOException());
                assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
                                test.getUser(anotherUser2.getUsername()).getStatusCode());
        }

        @Test
        void testUpdateUser() throws IOException {
                UserController test = new UserController(userDao, simp);

                when(userDao.updateUser(testUser)).thenReturn(testUser);
                assertEquals(HttpStatus.OK, test.updateUser(testUser).getStatusCode());

                when(userDao.updateUser(anotherUser)).thenReturn(null);
                assertEquals(HttpStatus.OK, test.updateUser(anotherUser).getStatusCode());

                when(userDao.updateUser(anotherUser2)).thenThrow(new IOException());
                assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
                                test.updateUser(anotherUser2).getStatusCode());
        }

        @Test
        void testUserExists() throws IOException {
                UserController test = new UserController(userDao, null);

                when(userDao.userExists(testUser.getUsername())).thenReturn(true);
                assertEquals(HttpStatus.OK,
                                test.userExists(testUser.getUsername()).getStatusCode());

                when(userDao.userExists(anotherUser.getUsername())).thenReturn(false);
                assertEquals(HttpStatus.OK,
                                test.userExists(anotherUser.getUsername()).getStatusCode());

                when(userDao.userExists(anotherUser2.username)).thenThrow(new IOException());
                assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
                                test.userExists(anotherUser2.getUsername()).getStatusCode());
        }

        @Test
        public void testAddNotification() throws Exception {
                UserController test = new UserController(userDao, simp);

                when(userDao.addNotificationToUser(testUser.username, 0)).thenReturn(testUser);
                assertEquals(HttpStatus.OK, test.addNotificationToUser(testUser.username, 0).getStatusCode());

                when(userDao.addNotificationToUser(testUser.username, 0)).thenReturn(null);
                assertEquals(HttpStatus.NOT_FOUND, test.addNotificationToUser(testUser.username, 0).getStatusCode());

                when(userDao.addNotificationToUser(testUser.username, 0)).thenAnswer(answer -> {
                        throw new IOException("Test Should Fail");
                });
                assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
                                test.addNotificationToUser(testUser.username, 0).getStatusCode());

        }
}
