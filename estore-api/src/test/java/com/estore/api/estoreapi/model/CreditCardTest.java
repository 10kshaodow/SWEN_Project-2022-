package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.CreditCard;



@Tag("Model-tier")
public class CreditCardTest {
    
    @Test
    void testGetCvv() {
        CreditCard creditCard = new CreditCard("5664769987120318", "09", "2024", "765");
        String actual = creditCard.getCvv();
        String expected = "765";
        assertEquals(expected, actual);
    }

    @Test
    void testGetExpirationMonth() {
        CreditCard creditCard = new CreditCard("5664769987120318", "09", "2024", "765");
        String actual = creditCard.getExpirationMonth();
        String expected = "09";
        assertEquals(expected, actual);
    }

    @Test
    void testGetExpirationYear() {
        CreditCard creditCard = new CreditCard("5664769987120318", "09", "2022", "765");
        String actual = creditCard.getExpirationYear();
        String expected = "2022";
        assertEquals(expected, actual);
    }

    @Test
    void testGetNumber() {
        CreditCard creditCard = new CreditCard("5664769987120318", "09", "2024", "765");
        String actual = creditCard.getNumber();
        String expected = "5664769987120318";
        assertEquals(expected, actual);
    }

    @Test
    void testSetCvv() {
        CreditCard creditCard = new CreditCard("5664769987120318", "09", "2024", "765");
        creditCard.setCvv("890");
        String actual = creditCard.getCvv();
        String expected = "890";
        assertEquals(expected, actual);
    }

    @Test
    void testSetExpirationMonth() {
        CreditCard creditCard = new CreditCard("5664769987120318", "09", "2024", "765");
        creditCard.setExpirationMonth("12");
        String actual = creditCard.getExpirationMonth();
        String expected = "12";
        assertEquals(expected, actual);
    }

    @Test
    void testSetExpirationYear() {
        CreditCard creditCard = new CreditCard("5664769987120318", "09", "2024", "765");
        creditCard.setExpirationYear("2025");
        String actual = creditCard.getExpirationYear();
        String expected = "2025";
        assertEquals(expected, actual);
    }

    @Test
    void testSetNumber() {
        CreditCard creditCard = new CreditCard("5664769987120318", "09", "2024", "765");
        creditCard.setNumber("7654398267290141");
        String actual = creditCard.getNumber();
        String expected = "7654398267290141";
        assertEquals(expected, actual);
    }

    @Test
    void testToString() {
        CreditCard creditCard = new CreditCard("5664769987120318", "09", "2024", "765");
        String actual = creditCard.toString();
        String expected = String.format(CreditCard.STRING_FORMAT, "5664769987120318", "09", "2024", "765");
        assertEquals(expected, actual);
    }

    @Test
    void testEqualsSameReference() {
        CreditCard creditCard = new CreditCard("5664769987120318", "09", "2024", "765");
        boolean actual = creditCard.equals(creditCard);
        assertTrue(actual);
    }

    @Test
    void testEqualsSameValues() {
        CreditCard creditCard = new CreditCard("5664769987120318", "09", "2024", "765");
        CreditCard creditCardDuplicate = new CreditCard("5664769987120318", "09", "2024", "765");
        boolean actual = creditCard.equals(creditCardDuplicate);
        assertTrue(actual);
    }

    @Test
    void testEqualsSimilarValues() {
        CreditCard creditCard = new CreditCard("5664769987120318", "09", "2024", "765");
        CreditCard creditCardDuplicate = new CreditCard("5664769987120319", "09", "2024", "765");
        boolean actual = creditCard.equals(creditCardDuplicate);
        assertFalse(actual);
    }

    @Test
    void testEqualsNonCreditCard() {
        CreditCard creditCard = new CreditCard("5664769987120318", "09", "2024", "765");
        boolean actual = creditCard.equals(null);
        assertFalse(actual);
    }
    
    @Test 
    void testEqualsDiffMonth() {
        CreditCard creditCard = new CreditCard("5664769987120318", "10", "2024", "765");
        CreditCard creditCard2 = new CreditCard("5664769987120318", "09", "2024", "765");
        assertFalse(creditCard.equals(creditCard2));
    }
    
    @Test 
    void testEqualsDiffYear() {
        CreditCard creditCard = new CreditCard("5664769987120318", "09", "2025", "765");
        CreditCard creditCard2 = new CreditCard("5664769987120318", "09", "2024", "765");
        assertFalse(creditCard.equals(creditCard2));
    }
    
    @Test 
    void testEqualsDiffCvv() {
        CreditCard creditCard = new CreditCard("5664769987120318", "09", "2024", "766");
        CreditCard creditCard2 = new CreditCard("5664769987120318", "09", "2024", "765");
        assertFalse(creditCard.equals(creditCard2));
    }
    
}
