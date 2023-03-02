package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.Address;
import com.estore.api.estoreapi.model.CreditCard;
import com.estore.api.estoreapi.model.Order;
import com.estore.api.estoreapi.model.OrderItem;
import com.estore.api.estoreapi.model.OrderStatus;

public class OrderTest {

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;

  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));

  }

  public void restoreStreams() {
    System.setOut(originalOut);
  }

  /**
   * Test Access to an Addrees street name.
   */
  @Test
  void getIdTest() {

    Order order = new Order(10, null, null, null, null, null, null, 0, 0, 0);

    int actual = order.getId();
    int expected = 10;

    assertEquals(expected, actual);

  }

  /**
   * Test Access to an Addrees street name.
   */
  @Test
  void getOrderDateTest() {
    Date date = new Date(2022 - 02 - 02);
    Order order = new Order(0, null, date, null, null, null, null, 0, 0, 0);
    Date actual = order.getOrderDate();

    Date expected = new Date(2022 - 02 - 02);

    assertEquals(expected, actual);

  }

    /**
   * Test Access to an Addrees street name.
   */
  @Test
  void setOrderDateTest() {
    Date date = new Date(2022 - 02 - 02);
    Order order = new Order(0, null, date, null, null, null, null, 0, 0, 0);
    Date expected = new Date(2022 - 01 - 01);
    order.setOrderDate(expected);
    Date actual = order.getOrderDate();


    assertEquals(expected, actual);

  }

  /**
   * Test Access to an Addrees street name.
   */
  @Test
  void getStatusTest() {
    OrderStatus status = OrderStatus.Ordered;
    Order order = new Order(0, null, null, status, null, null, null, 0, 0, 0);
    OrderStatus actual = order.getStatus();

    OrderStatus expected = OrderStatus.Ordered;

    assertEquals(expected, actual);

  }

  /**
   * Test Access to an Addrees street name.
   */
  @Test
  void setStatusTest() {
    OrderStatus status = OrderStatus.Ordered;
    Order order = new Order(0, null, null, status, null, null, null, 0, 0, 0);

    OrderStatus expected = OrderStatus.Fulfilled;
    order.setStatus(expected);
    OrderStatus actual = order.getStatus();
    
    assertEquals(expected, actual);

  }

  /**
   * Test Access to an Addrees street name.
   */
  @Test
  void getUsernameTest() {
    Order order = new Order(0, null, null, null, "BigD", null, null, 0, 0, 0);
    String actual = order.getUsername();

    String expected = "BigD";

    assertEquals(expected, actual);

  }

  /**
   * Test Access to an Addrees street name.
   */
  @Test
  void setUsernameTest() {
    Order order = new Order(0, null, null, null, "BigD", null, null, 0, 0, 0);
    

    String expected = "Superman";
    order.setUsername(expected);
    String actual = order.getUsername();

    assertEquals(expected, actual);

  }

  /**
   * Test Access to an Addrees street name.
   */
  @Test
  void getAddresssTest() {
    Address address = new Address("Washignton St", "New York", "NY", "United States", "10032");
    Order order = new Order(0, null, null, null, null, address, null, 0, 0, 0);
    Address actual = order.getAddress();

    Address expected = new Address("Washignton St", "New York", "NY", "United States", "10032");

    assertEquals(expected.toString(), actual.toString());

  }

  /**
   * Test Access to an Addrees street name.
   */
  @Test
  void setAddresssTest() {
    Address address = new Address("Washignton St", "New York", "NY", "United States", "10032");
    Order order = new Order(0, null, null, null, null, address, null, 0, 0, 0);

    Address expected = new Address("Henrietta Rd", "New York", "NY", "United States", "10032");
    order.setAddress(expected);
    
    Address actual = order.getAddress();

    assertEquals(expected.toString(), actual.toString());

  }

  /**
   * Test Access to an Addrees street name.
   */
  @Test
  void getCreditCardTest() {
    CreditCard creditCard = new CreditCard("5664769987120318", "04", "2022", "021");
    Order order = new Order(0, null, null, null, null, null, creditCard, 0, 0, 0);
    CreditCard actual = order.getCreditCard();

    CreditCard expected = new CreditCard("5664769987120318", "04", "2022", "021");

    assertEquals(expected.toString(), actual.toString());

  }

  /**
   * Test Access to an Addrees street name.
   */
  @Test
  void setCreditCardTest() {
    CreditCard creditCard = new CreditCard("5664769987120318", "04", "2022", "021");
    Order order = new Order(0, null, null, null, null, null, creditCard, 0, 0, 0);
   

    CreditCard expected = new CreditCard("5664769987120328", "04", "2024", "091");
    order.setCreditCard(expected);

    CreditCard actual = order.getCreditCard();

    assertEquals(expected.toString(), actual.toString());

  }

  /**
   * Test Access to an Addrees street name.
   */
  @Test
  void getSubtotalTest() {
    Order order = new Order(0, null, null, null, null, null, null, 10.99, 0, 0);
    Double actual = order.getSubtotal();

    Double expected = 10.99;

    assertEquals(expected, actual);

  }


  /**
   * Test Access to an Addrees street name.
   */
  @Test
  void setSubtotalTest() {
    Order order = new Order(0, null, null, null, null, null, null, 10.99, 0, 0);

    Double expected = 12.99;
    order.setSubtotal(expected);
    Double actual = order.getSubtotal();

    assertEquals(expected, actual);

  }

  /**
   * Test Access to an Addrees street name.
   */
  @Test
  void getTaxesTest() {
    Order order = new Order(0, null, null, null, null, null, null, 0, 0.875, 0);
    Double actual = order.getTaxes();

    Double expected = 0.875;

    assertEquals(expected, actual);

  }

  /**
   * Test Access to an Addrees street name.
   */
  @Test
  void setTaxesTest() {
    Order order = new Order(0, null, null, null, null, null, null, 0, 0.875, 0);
    
    Double expected = 0.85;
    order.setTaxes(expected);
    Double actual = order.getTaxes();

    assertEquals(expected, actual);

  }


  /**
   * Test Access to an Addrees street name.
   */
  @Test
  void getTotalTest() {
    Order order = new Order(0, null, null, null, null, null, null, 0, 0, 11.76);
    Double actual = order.getTotal();

    Double expected = 11.76;

    assertEquals(expected, actual);

  }


  /**
   * Test Access to an Addrees street name.
   */
  @Test
  void setTotalTest() {
    Order order = new Order(0, null, null, null, null, null, null, 0, 0, 11.76);

    Double expected = 15.76;
    order.setTotal(expected);
    Double actual = order.getTotal();

    assertEquals(expected, actual);

  }

  /**
   * Test Access to get the orderItem order list.
   */
  @Test
  void getOrderItemListTest() {
    ArrayList<OrderItem> arrayList = new ArrayList<OrderItem>();
    OrderItem orderItem = new OrderItem(11, 0, 5);
    arrayList.add(orderItem);
    Order order = new Order(0, arrayList, null, null, null, null, null, 0, 0, 0);
    ArrayList<OrderItem> actual = order.getOrderItemList();

    ArrayList<OrderItem> expected = new ArrayList<OrderItem>();
    OrderItem testitem = new OrderItem(11, 0, 5);
    expected.add(testitem);

    assertEquals(expected.toString(), actual.toString());

  }

  /**
   * Test Access to get the orderItem order list.
   */
  @Test
  void setOrderItemListTest() {
    ArrayList<OrderItem> arrayList = new ArrayList<OrderItem>();
    OrderItem orderItem = new OrderItem(11, 0, 5);
    arrayList.add(orderItem);
    Order order = new Order(0, arrayList, null, null, null, null, null, 0, 0, 0);
    
    ArrayList<OrderItem> expected = new ArrayList<OrderItem>();
    OrderItem testitem = new OrderItem(11, 0, 5);
    expected.add(testitem);
    order.setOrderItemList(expected);
    ArrayList<OrderItem> actual = order.getOrderItemList();

    assertEquals(expected.toString(), actual.toString());

  }

  /**
   * Test Access to get the orderItem order list.
   */
  @Test
  void addOrderItemTest() {
    ArrayList<OrderItem> arrayList = new ArrayList<OrderItem>();
    OrderItem orderItem = new OrderItem(11, 0, 5);
    Order test = new Order(0, arrayList, null, null, null, null, null, 0, 0, 0);

    test.addOrderItem(orderItem);
    OrderItem actual = test.findOrderItemByID(11);

    OrderItem expected = new OrderItem(11, 0, 5);

    assertEquals(expected.toString(), actual.toString());
    
    test.addOrderItem(orderItem);

    OrderItem actual_1 = test.findOrderItemByID(11);
    OrderItem expected_1 = new OrderItem(11, 0, 10);
  

    assertEquals(expected_1.toString(), actual_1.toString());




  }

  /**
   * Test Access to get the orderItem order list.
   */
  @Test
  void findOrderItemByIDTest() {
    ArrayList<OrderItem> arrayList = new ArrayList<OrderItem>();
    OrderItem orderItem = new OrderItem(11, 0, 5);
    Order test = new Order(0, arrayList, null, null, null, null, null, 0, 0, 0);

    test.addOrderItem(orderItem);
    OrderItem actual = test.findOrderItemByID(11);

    OrderItem expected = new OrderItem(11, 0, 5);

    assertEquals(expected.toString(), actual.toString());

  }

  /**
   * Test Access to get the orderItem order list.
   */
  @Test
  void findOrderItemIndexTest() {
    ArrayList<OrderItem> arrayList = new ArrayList<OrderItem>();
    OrderItem orderItem = new OrderItem(11, 0, 5);
    Order test = new Order(0, arrayList, null, null, null, null, null, 0, 0, 0);

    test.addOrderItem(orderItem);
    int actual = test.findOrderItemIndex(11);
    int expected = 0;

    assertEquals(expected, actual);

    

  }

  /**
   * Test Access to get the orderItem order list.
   */
  @Test
  void removeOrderItemTest() {
    ArrayList<OrderItem> arrayList = new ArrayList<OrderItem>();
    OrderItem orderItem = new OrderItem(11, 0, 5);
    Order test = new Order(0, arrayList, null, null, null, null, null, 0, 0, 0);

    test.addOrderItem(orderItem);
    test.removeOrderItem(11);
    int actual = arrayList.size();

    int expected = 0;

    assertEquals(expected, actual);

    arrayList.remove(orderItem);
    test = new Order(0, arrayList, null, null, null, null, null, 0, 0, 0);
    test.removeOrderItem(orderItem.getProductID());


  }

  @Test
  void updateOrderItemQuantityTest() {
    ArrayList<OrderItem> arrayList = new ArrayList<OrderItem>();
    OrderItem orderItem = new OrderItem(11, 0, 5);
    Order test = new Order(0, arrayList, null, null, null, null, null, 0, 0, 0);

    test.addOrderItem(orderItem);
    test.updateOrderItemQuantity(11, 5);
    arrayList.get(0).setQuantity(2);
    int actual = arrayList.get(0).getQuantity();
    int expected = 2;

    assertEquals(expected, actual);

    arrayList.remove(orderItem);
    test = new Order(0, arrayList, null, null, null, null, null, 0, 0, 0);
    test.updateOrderItemQuantity(11, 5);
    // test.updateOrderItemQuantity(11, 5);
    // int neg = -1;

    // assertEquals(expected, actual);



  
  }
  @Test
  void equalsTest() {
    ArrayList<OrderItem> arrayList = new ArrayList<OrderItem>();
    Date date = new Date(2022 - 02 - 02);
    Address address = new Address("Washignton St", "New York", "NY", "United States", "10032");
    Address address_1 = new Address("Washignton St", "Boston", "MA", "United States", "40032");
    CreditCard creditCard = new CreditCard("5664769987120318", "04", "2022", "021");
    OrderItem orderItem = new OrderItem(11, 0, 5);
    Order test = new Order(0, arrayList, date, OrderStatus.Fulfilled, "andrew", address, creditCard, 0, 0, 99);
    Order test_1 = new Order(1, arrayList, date, OrderStatus.Ordered, "Gon", address_1, creditCard, 0, 0, 100);
    Order test_2 = new Order(1, arrayList, date, OrderStatus.Ordered, "Gon", address_1, creditCard, 0, 0, 100);
    test_2.addOrderItem(orderItem);
    test_1.addOrderItem(orderItem);
    boolean expected_F = false;
    boolean expected_T = true; 

    boolean actual_1 = test.equals(test_1);

    assertEquals(expected_F, actual_1);

    boolean actual_2 = test_1.equals(test_2);
    assertEquals(expected_T, actual_2);

    boolean actual_3 = test_1.getUsername().equals(test_2.getUsername());
    assertEquals(expected_T, actual_3);

    boolean actual_4 = test_1.equals(orderItem);
    assertEquals(expected_F, actual_4);

    boolean actual_5 = test_1.getOrderDate().equals(test_2.getOrderDate());
    assertEquals(expected_T, actual_5);

    boolean actual_6 = test_1.getOrderItemList().equals(test_2.getOrderItemList());
    assertEquals(expected_T, actual_6);

    test = new Order(0, arrayList, date, OrderStatus.Fulfilled, "andrew", address, creditCard, 0, 0, 99);
    ArrayList<OrderItem> diffArraylist = new ArrayList<OrderItem>(); 
    diffArraylist.add(new OrderItem(12, 0, 120)); 
    Order diffOrderItemList = new Order(0, diffArraylist, date, OrderStatus.Fulfilled, "andrew", address, creditCard, 0, 0, 99);
    Date diffDat = new Date(3000 - 12 - 20);
    Order diffDate = new Order(0, arrayList, diffDat, OrderStatus.Fulfilled, "andrew", address, creditCard, 0, 0, 99);
    Order diffStatus = new Order(0, arrayList, date, OrderStatus.Cancelled, "andrew", address, creditCard, 0, 0, 99);
    Order diffUsername = new Order(0, arrayList, date, OrderStatus.Fulfilled, "different", address, creditCard, 0, 0, 99);
    Address address2 = new Address("null", "", "","","");
    Order diffaddress = new Order(0, arrayList, date, OrderStatus.Fulfilled, "andrew", address2, creditCard, 0, 0, 99);
    CreditCard card2 = new CreditCard("", "not same", "null", "null");
    Order diffCard = new Order(0, arrayList, date, OrderStatus.Fulfilled, "andrew", address, card2, 0, 0, 99);
    Order diffsubtotal = new Order(0, arrayList, date, OrderStatus.Fulfilled, "andrew", address, creditCard, 4, 0, 99);
    Order difftax = new Order(0, arrayList, date, OrderStatus.Fulfilled, "andrew", address, creditCard, 0, 4, 99);
    Order difftotal = new Order(0, arrayList, date, OrderStatus.Fulfilled, "andrew", address, creditCard, 0, 0, 100);

    assertFalse(test.equals(diffOrderItemList));
    assertFalse(test.equals(diffDate));
    assertFalse(test.equals(diffStatus));
    assertFalse(test.equals(diffUsername));
    assertFalse(test.equals(diffaddress));
    assertFalse(test.equals(diffCard));
    assertFalse(test.equals(diffsubtotal));
    assertFalse(test.equals(difftax));
    assertFalse(test.equals(difftotal));

  }

}
