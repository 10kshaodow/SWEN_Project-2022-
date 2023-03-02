package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.estore.api.estoreapi.model.Order;
import com.estore.api.estoreapi.model.OrderItem;
import com.estore.api.estoreapi.model.OrderStatus;
import com.estore.api.estoreapi.persistence.OrderFileDAO;

@Tag("Controller-tier")
public class OrderControllerTest {
    private OrderFileDAO mockOrderFileDAO;
    private Order[] ordersArray;
    private Order order;

    @BeforeEach
    public void setup() {
        this.mockOrderFileDAO = mock(OrderFileDAO.class);
        this.order = new Order(10, new ArrayList<OrderItem>(), new Date(), OrderStatus.Ordered, "username", null, null,
                0, 0, 0);
        this.ordersArray = new Order[2];
        this.ordersArray[0] = new Order(11, new ArrayList<OrderItem>(), new Date(), OrderStatus.Ordered, "username",
                null, null, 0, 0, 0);
        this.ordersArray[1] = new Order(12, new ArrayList<OrderItem>(), new Date(), OrderStatus.Ordered, "username",
                null, null, 0, 0, 0);
    }

    @Test
    void testConstructor() {
        OrderFileDAO mockOrderFileDAO = mock(OrderFileDAO.class);
        OrderController orderController = new OrderController(mockOrderFileDAO);
        assertNotNull(orderController);
    }

    @Test
    void testCreateOrder() throws IOException {
        OrderController orderController = new OrderController(mockOrderFileDAO);
        when(this.mockOrderFileDAO.createOrder(this.order)).thenReturn(this.order);

        ResponseEntity<Order> createOrderResponseEntity = orderController.createOrder(this.order);
        HttpStatus actualStatusCode = createOrderResponseEntity.getStatusCode();
        HttpStatus expectedStatusCode = HttpStatus.CREATED;
        Order body = createOrderResponseEntity.getBody();

        verify(mockOrderFileDAO).createOrder(this.order);
        assertEquals(expectedStatusCode, actualStatusCode);
        assertNotNull(body);
        assertEquals(this.order.toString(), body.toString());
    }

    @Test
    void testDeleteOrder() throws IOException {
        OrderController orderController = new OrderController(this.mockOrderFileDAO);
        when(this.mockOrderFileDAO.deleteOrder(this.order.getId())).thenReturn(true);

        ResponseEntity<Order> deleteOrderResponseEntity = orderController.deleteOrder(this.order.getId());
        HttpStatus actualStatusCode = deleteOrderResponseEntity.getStatusCode();
        HttpStatus expectedStatusCode = HttpStatus.OK;
        Order body = deleteOrderResponseEntity.getBody();

        verify(mockOrderFileDAO).deleteOrder(this.order.getId());
        assertEquals(expectedStatusCode, actualStatusCode);
        assertNull(body);
    }

    @Test
    void testGetAllOrders() throws IOException {
        OrderController orderController = new OrderController(this.mockOrderFileDAO);
        when(this.mockOrderFileDAO.getAllOrders()).thenReturn(ordersArray);

        ResponseEntity<Order[]> allOrdersResponseEntity = orderController.getAllOrders();
        HttpStatus actualStatusCode = allOrdersResponseEntity.getStatusCode();
        HttpStatus expectedStatusCode = HttpStatus.OK;
        Order[] body = allOrdersResponseEntity.getBody();

        verify(mockOrderFileDAO).getAllOrders();
        assertEquals(expectedStatusCode, actualStatusCode);
        assertNotNull(body);
        assertEquals(ordersArray.length, body.length);
    }

    @Test
    void testGetOrder() {
        OrderController orderController = new OrderController(this.mockOrderFileDAO);
        when(this.mockOrderFileDAO.getOrder(this.order.getId())).thenReturn(this.order);

        ResponseEntity<Order> getOrderResponseEntity = orderController.getOrder(this.order.getId());
        HttpStatus actualStatusCode = getOrderResponseEntity.getStatusCode();
        HttpStatus expectedStatusCode = HttpStatus.OK;
        Order body = getOrderResponseEntity.getBody();

        verify(mockOrderFileDAO).getOrder(this.order.getId());
        assertEquals(expectedStatusCode, actualStatusCode);
        assertNotNull(body);
        assertEquals(this.order.toString(), body.toString());

        when(this.mockOrderFileDAO.getOrder(-1)).thenAnswer(answer -> {
            throw new IOException("Test Should Fail");
        });

        getOrderResponseEntity = orderController.getOrder(-1);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, getOrderResponseEntity.getStatusCode());
    }

    @Test
    void testUpdateOrder() throws IOException {
        OrderController orderController = new OrderController(this.mockOrderFileDAO);
        this.order.setStatus(OrderStatus.Cancelled);
        when(this.mockOrderFileDAO.updateOrder(this.order)).thenReturn(this.order);

        ResponseEntity<Order> updateOrderResponseEntity = orderController.updateOrder(this.order);
        HttpStatus actualStatusCode = updateOrderResponseEntity.getStatusCode();
        HttpStatus expectedStatusCode = HttpStatus.OK;
        Order body = updateOrderResponseEntity.getBody();

        verify(mockOrderFileDAO).updateOrder(this.order);
        assertEquals(expectedStatusCode, actualStatusCode);
        assertNotNull(body);
        assertEquals(this.order.toString(), body.toString());
    }

    @Test
    void testCreateOrderIOException() throws IOException {
        OrderController orderController = new OrderController(mockOrderFileDAO);
        when(this.mockOrderFileDAO.createOrder(this.order)).thenThrow(IOException.class);

        ResponseEntity<Order> createOrderResponseEntity = orderController.createOrder(this.order);
        HttpStatus actualStatusCode = createOrderResponseEntity.getStatusCode();
        HttpStatus expectedStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        Order body = createOrderResponseEntity.getBody();

        verify(mockOrderFileDAO).createOrder(this.order);
        assertEquals(expectedStatusCode, actualStatusCode);
    }

    @Test
    void testDeleteOrderIOException() throws IOException {
        OrderController orderController = new OrderController(this.mockOrderFileDAO);
        when(this.mockOrderFileDAO.deleteOrder(this.order.getId())).thenThrow(IOException.class);

        ResponseEntity<Order> deleteOrderResponseEntity = orderController.deleteOrder(this.order.getId());
        HttpStatus actualStatusCode = deleteOrderResponseEntity.getStatusCode();
        HttpStatus expectedStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        Order body = deleteOrderResponseEntity.getBody();

        verify(mockOrderFileDAO).deleteOrder(this.order.getId());
        assertEquals(expectedStatusCode, actualStatusCode);
    }

    @Test
    void testGetAllOrdersIOException() throws IOException {
        OrderController orderController = new OrderController(this.mockOrderFileDAO);
        when(this.mockOrderFileDAO.getAllOrders()).thenThrow(IOException.class);

        ResponseEntity<Order[]> allOrdersResponseEntity = orderController.getAllOrders();
        HttpStatus actualStatusCode = allOrdersResponseEntity.getStatusCode();
        HttpStatus expectedStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        Order[] body = allOrdersResponseEntity.getBody();

        verify(mockOrderFileDAO).getAllOrders();
        assertEquals(expectedStatusCode, actualStatusCode);
    }

    // @Test
    // void testGetOrderIOException() {
    // OrderController orderController = new OrderController(this.mockOrderFileDAO);
    // when(this.mockOrderFileDAO.getOrder(this.order.getId())).thenThrow(IOException.class);

    // ResponseEntity<Order> getOrderResponseEntity =
    // orderController.getOrder(this.order.getId());
    // HttpStatus actualStatusCode = getOrderResponseEntity.getStatusCode();
    // HttpStatus expectedStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
    // Order body = getOrderResponseEntity.getBody();

    // verify(mockOrderFileDAO).getOrder(this.order.getId());
    // assertEquals(expectedStatusCode, actualStatusCode);
    // }

    @Test
    void testUpdateOrderIOException() throws IOException {
        OrderController orderController = new OrderController(this.mockOrderFileDAO);
        this.order.setStatus(OrderStatus.Cancelled);
        when(this.mockOrderFileDAO.updateOrder(this.order)).thenThrow(IOException.class);

        ResponseEntity<Order> updateOrderResponseEntity = orderController.updateOrder(this.order);
        HttpStatus actualStatusCode = updateOrderResponseEntity.getStatusCode();
        HttpStatus expectedStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        Order body = updateOrderResponseEntity.getBody();

        verify(mockOrderFileDAO).updateOrder(this.order);
        assertEquals(expectedStatusCode, actualStatusCode);
    }

    @Test
    void testCreateOrderReturnNull() throws IOException {
        OrderController orderController = new OrderController(mockOrderFileDAO);
        when(this.mockOrderFileDAO.createOrder(this.order)).thenReturn(null);

        ResponseEntity<Order> createOrderResponseEntity = orderController.createOrder(this.order);
        HttpStatus actualStatusCode = createOrderResponseEntity.getStatusCode();
        HttpStatus expectedStatusCode = HttpStatus.CONFLICT;
        Order body = createOrderResponseEntity.getBody();

        verify(mockOrderFileDAO).createOrder(this.order);
        assertEquals(expectedStatusCode, actualStatusCode);
    }

    @Test
    void testDeleteOrderReturnNull() throws IOException {
        OrderController orderController = new OrderController(this.mockOrderFileDAO);
        when(this.mockOrderFileDAO.deleteOrder(this.order.getId())).thenReturn(false);

        ResponseEntity<Order> deleteOrderResponseEntity = orderController.deleteOrder(this.order.getId());
        HttpStatus actualStatusCode = deleteOrderResponseEntity.getStatusCode();
        HttpStatus expectedStatusCode = HttpStatus.OK;
        Order body = deleteOrderResponseEntity.getBody();

        verify(mockOrderFileDAO).deleteOrder(this.order.getId());
        assertEquals(expectedStatusCode, actualStatusCode);
    }

    @Test
    void testGetOrderReturnNull() {
        OrderController orderController = new OrderController(this.mockOrderFileDAO);
        when(this.mockOrderFileDAO.getOrder(this.order.getId())).thenReturn(null);

        ResponseEntity<Order> getOrderResponseEntity = orderController.getOrder(this.order.getId());
        HttpStatus actualStatusCode = getOrderResponseEntity.getStatusCode();
        HttpStatus expectedStatusCode = HttpStatus.OK;
        Order body = getOrderResponseEntity.getBody();

        verify(mockOrderFileDAO).getOrder(this.order.getId());
        assertEquals(expectedStatusCode, actualStatusCode);
    }

    @Test
    void testUpdateOrderReturnNull() throws IOException {
        OrderController orderController = new OrderController(this.mockOrderFileDAO);
        this.order.setStatus(OrderStatus.Cancelled);
        when(this.mockOrderFileDAO.updateOrder(this.order)).thenReturn(null);

        ResponseEntity<Order> updateOrderResponseEntity = orderController.updateOrder(this.order);
        HttpStatus actualStatusCode = updateOrderResponseEntity.getStatusCode();
        HttpStatus expectedStatusCode = HttpStatus.OK;
        Order body = updateOrderResponseEntity.getBody();

        verify(mockOrderFileDAO).updateOrder(this.order);
        assertEquals(expectedStatusCode, actualStatusCode);
    }

}
