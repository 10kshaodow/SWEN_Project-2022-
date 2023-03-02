package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import com.estore.api.estoreapi.model.OrderStatus;

@Tag("Model-tier")
public class OrderStatusTest {
    
    @Test
    public void testToString(){
        OrderStatus orderStatus = OrderStatus.UnderReview;
        String expected = "Under Review";
        String actual = orderStatus.toString();
        assertEquals(expected, actual);

        orderStatus = OrderStatus.Ordered;
        expected = "Ordered";
        actual = orderStatus.toString();
        assertEquals(expected, actual);

        orderStatus = OrderStatus.Cancelled;
        expected = "Cancelled";
        actual = orderStatus.toString();
        assertEquals(expected, actual);

        orderStatus = OrderStatus.Fulfilled;
        expected = "Fulfilled";
        actual = orderStatus.toString();
        assertEquals(expected, actual);
    }
}
