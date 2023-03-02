package com.estore.api.estoreapi.model;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.Address;
import com.estore.api.estoreapi.model.OrderItem;


public class AddressTest {
   /**
   * Test Access to an Addrees street name. 
   */
  @Test
  void getStreetTest() {
    Address address = new Address("Washignton St", "New York", "NY", "US", "10032");
    String actual = address.getStreet();

    String expected = "Washignton St";

    assertEquals(expected, actual);

  }

  /**
   * Test Access to an Addrees street name. 
   */
  @Test
  void setStreetTest() {
    Address address = new Address(null, null, null, null, null);
    

    String expected = "Washignton St";
    address.setStreet(expected);
    String actual = address.getStreet();

    assertEquals(expected, actual);

  }

   /**
   * Test Access to an Addrees city name. 
   */
  @Test
  void getCityTest() {
    Address address = new Address("Washignton St", "New York", "NY", "US", "10032");
    String actual = address.getCity();

    String expected = "New York";

    assertEquals(expected, actual);

  }
   /**
   * Test Access to an Addrees city name. 
   */
  @Test
  void setCityTest() {
    Address address = new Address(null, null, null, null, null);
    String expected = "New York";
    address.setCity(expected);
    String actual = address.getCity();

    assertEquals(expected, actual);

  }

   /**
   * Test Access to an Addrees city name. 
   */
  @Test
  void getStateTest() {
    Address address = new Address("Washignton St", "New York", "NY", "US", "10032");
    String actual = address.getState();

    String expected = "NY";

    assertEquals(expected, actual);

  }
/**
   * Test Access to an Addrees city name. 
   */
  @Test
  void setStateTest() {
    Address address = new Address(null, null, null, null, null);
    

    String expected = "NY";
    address.setState(expected);

    String actual = address.getState();

    assertEquals(expected, actual);

  }

  /**
   * Test Access to an Adrees city name. 
   */
  @Test
  void getCountryTest() {
    Address address = new Address("Washignton St", "New York", "NY", "United States", "10032");
    String actual = address.getCountry();

    String expected = "United States";

    assertEquals(expected, actual);

  }
    /**
   * Test Access to an Adrees city name. 
   */
  @Test
  void setCountryTest() {
    Address address = new Address(null, null, null, null, null);

    String expected = "United States";
    address.setCountry(expected);
    String actual = address.getCountry();

    assertEquals(expected, actual);

  }
    /**
   * Test Access to an Adrees city name. 
   */
  @Test
  void getZipcodeTest() {
    Address address = new Address("Washignton St", "New York", "NY", "United States", "10032");
    String actual = address.getZipcode();

    String expected = "10032";

    assertEquals(expected, actual);

  }
  
    /**
   * Test Access to an Adrees city name. 
   */
  @Test
  void setZipcodeTest() {
    Address address = new Address(null, null, null, null, null);

    String expected = "10032";
    address.setZipcode(expected);
    String actual = address.getZipcode();

    

    assertEquals(expected, actual);

  }

  @Test
  void equalsTest() {
    Address address = new Address("RIT", "Newark", "NY", "United States", "10042");
    Address address1 = new Address("Washignton St", "New York", "NY", "United States", "10032");
    Address address2 = new Address("Washignton St", "New York", "NY", "United States", "10040");
    OrderItem orderItem = new OrderItem(11, 0, 5);
    boolean expected_F = false;
    boolean expected_T = true; 

    
    boolean actual_1 = address1.equals(address2);
    assertEquals(expected_F, actual_1);

    address2.setZipcode("10032");
    address2.setCity("Boston");

    actual_1 = address1.equals(address2);
    assertEquals(expected_F, actual_1);

    address2.setCity("Boston");
    address2.setState("MA");

    actual_1 = address1.equals(address2);
    assertEquals(expected_F, actual_1);

    address2.setState("NY");
    address2.setCountry("Canada");

    actual_1 = address1.equals(address2);
    assertEquals(expected_F, actual_1);

    // address1.sets

    // boolean actual_2 = address1.equals(address2);
    // assertEquals(expected_T, actual_2);

    // boolean actual_3 = address1.getCity().equals(address2.getCity());
    // assertEquals(expected_T, actual_3);

    boolean actual_4 = address1.equals(orderItem);

    assertEquals(expected_F, actual_4);

    


  }
  
    
}
