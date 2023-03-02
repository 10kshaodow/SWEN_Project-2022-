package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.estore.api.estoreapi.model.Address;
import com.estore.api.estoreapi.model.CreditCard;
import com.estore.api.estoreapi.model.Order;
import com.estore.api.estoreapi.model.OrderItem;
import com.estore.api.estoreapi.model.OrderStatus;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OrderFileDAOTest {

    OrderFileDAO orderFileDao;
    OrderFileDAO mockUserFileDao;
    Order existingOrder;
    Order testOrder;

    @BeforeEach
    void setup() throws IOException {

        this.orderFileDao = new OrderFileDAO("data/testOrder.json", new ObjectMapper());
        this.mockUserFileDao = Mockito.mock(OrderFileDAO.class);

        CreditCard creditCard = new CreditCard("testnum", "testeyr", "testem", "testcvv");
        Address address = new Address("teststreet", "testcity", "teststate", "testcountry", "testzip");
        Calendar cal = Calendar.getInstance();
        cal.set(1969, 11, 31, 0, 0, 0);
        Date date = cal.getTime();

        ArrayList<OrderItem> arrayList = new ArrayList<OrderItem>();
        OrderItem orderItem = new OrderItem(11, 0, 5);// only item it has
        arrayList.add(orderItem);
        existingOrder = new Order(38, arrayList, date, OrderStatus.Ordered, "test_username", address, creditCard, 0, 0,
                0);
        testOrder = new Order(100, arrayList, date, OrderStatus.Ordered, "test_username", address, creditCard, 0, 0, 0);
    }

    @Test
    void testCreateDeleteOrder() {
        try {
            orderFileDao.createOrder(existingOrder);
            assert (true);
        } catch (IOException e) {
            assert (true);
        }
        try {
            Order order = orderFileDao.createOrder(testOrder);
            int IDO = order.getId();
            Order test = orderFileDao.getOrder(IDO);
            assertEquals(order, test);
        } catch (IOException e) {
            assert (true);
        }
        try {
            Order order = orderFileDao.createOrder(testOrder);
            int IDO = order.getId();
            Order test = orderFileDao.getOrder(IDO);

            assertTrue(orderFileDao.deleteOrder(test.getId()));
        } catch (IOException e) {
            assert (true);
        }

        try {
            assertTrue(orderFileDao.deleteOrder(111));

        } catch (IOException e) {
            assert (false);
        }

        try {

            assertTrue(orderFileDao.deleteOrder(existingOrder.getId()));
        } catch (IOException e) {
            assert (false);

        }
    }

    @Test
    void testGetAllOrders() {
        try {
            assert (orderFileDao.getAllOrders() != null);

        } catch (IOException ioException) {
            assert (false);
        }

    }

    @Test
    void testGetOrder() {
        try {
            Order order = orderFileDao.createOrder(testOrder);
            int IDO = order.getId();
            Order test = orderFileDao.getOrder(IDO);
            assertEquals(test, orderFileDao.getOrder(test.getId()));
            assert (orderFileDao.getOrder(111) == null);
        } catch (IOException ioException) {
            assert (false);
        }

    }

    @Test
    void testUpdateOrder() {

        try {
            Order order = orderFileDao.createOrder(testOrder);
            int IDO = order.getId();
            Order test = orderFileDao.getOrder(IDO);
            assertEquals(orderFileDao.updateOrder(test), test);
        } catch (IOException e) {
            assert (false);
        }
        try {
            Order TEST = new Order(-1, null, null, OrderStatus.Ordered, "test_username", null, null, 0, 0, 0);
            assert (orderFileDao.updateOrder(TEST) == null);
        } catch (IOException ioException) {
            assert (false);
        }

    }

    @AfterAll
    public static void resetData() {
        try {
            FileInputStream in = new FileInputStream(new File("data/testOrderCopy.json"));
            FileOutputStream out = new FileOutputStream(new File("data/testOrder.json"));
            try {
                int n;
                try {
                    while ((n = in.read()) != -1) {
                        out.write(n);
                    }
                } catch (IOException e) {
                    // do nothing
                }
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    // do nothing
                }
            }
        } catch (FileNotFoundException e) {
            // do nothing data needs to be reset somehow tho.
        }
    }
}
