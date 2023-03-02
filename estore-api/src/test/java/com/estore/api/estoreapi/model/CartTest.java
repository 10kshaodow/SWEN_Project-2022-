package com.estore.api.estoreapi.model;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CartTest {
    private Cart cart1 = new Cart(new ArrayList<OrderItem>()); 
    private Cart cart2 = new Cart(new ArrayList<OrderItem>()); 

    @BeforeEach
    void setup() {
        this.cart1 = new Cart(new ArrayList<OrderItem>()); 
        this.cart2 = new Cart(new ArrayList<OrderItem>()); 
    }

    @Test
    void testAddOrderItem() {
        this.cart1.addOrderItem(new OrderItem(0, 0, 1));
        assert(this.cart1.orderItemList.size() == 1); 
        
        this.cart1.addOrderItem(new OrderItem(0, 0, 1));
        assert(this.cart1.orderItemList.size() == 1); 
        
    }

    @Test
    void testEquals() {
        assertNotEquals(cart1, new Object());
        assertEquals(cart1, cart1);
        this.cart1.addOrderItem(new OrderItem(0, 0, 1));
        this.cart2.addOrderItem(new OrderItem(0, 0, 1));
        this.cart2.addOrderItem(new OrderItem(1, 0, 1));
        assertNotEquals(cart1, cart2);
        this.cart1.addOrderItem(new OrderItem(1, 0, 1));
        assertEquals(this.cart1, this.cart2);
        this.cart1.addOrderItem(new OrderItem(2, 0, 1));
        this.cart2.addOrderItem(new OrderItem(3, 0, 2));
        assertNotEquals(this.cart1, this.cart2);
    }

    @Test
    void testFindOrderItemByID() {
        assertNull(cart1.findOrderItemByID(-1));
        OrderItem item = new OrderItem(0, 0, 1);
        this.cart1.addOrderItem(item);
        assertNotNull(this.cart1.findOrderItemByID(0)); 
    }

    @Test
    void testFindOrderItemIndex() {
        OrderItem item = new OrderItem(0, 0, 1);
        this.cart1.addOrderItem(item);
        assertEquals(item, this.cart1.findOrderItemByID(0)); 
    }

    @Test
    void testGetOrderItemList() {
        OrderItem item = new OrderItem(0, 0, 1);
        this.cart1.addOrderItem(item);
        ArrayList<OrderItem> expected = new ArrayList<OrderItem>(); 
        expected.add(item); 
        assertEquals(expected, this.cart1.getOrderItemList());
    }

    @Test
    void testRemoveOrderItem() {
        OrderItem item = new OrderItem(0, 0, 1);
        this.cart1.addOrderItem(item);
        this.cart1.removeOrderItem(item.productID);
        assertEquals(0, this.cart1.orderItemList.size());
        this.cart1.addOrderItem(item);
        this.cart1.removeOrderItem(1);
        assertEquals(1, this.cart1.orderItemList.size());

    }

    @Test
    void testToString() {
        assertEquals("{}", cart1.toString()); 
        OrderItem item = new OrderItem(0, 0, 1);
        this.cart1.addOrderItem(item);
        assertEquals("{"+item.toString()+" | }", cart1.toString());
    }

    @Test
    void testUpdateOrderItemQuantity() {
        cart1.updateOrderItemQuantity(-1, 1);
        OrderItem item = new OrderItem(0, 12.00, 1);
        this.cart1.addOrderItem(item);
        this.cart1.updateOrderItemQuantity(0, 3);
        assertEquals(3, this.cart1.orderItemList.get(0).quantity);
    }
}
