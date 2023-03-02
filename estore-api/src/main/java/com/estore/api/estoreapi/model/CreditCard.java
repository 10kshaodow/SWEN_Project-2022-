package com.estore.api.estoreapi.model;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a User adress
 * 
 * @author SWEN 05 Team 4D - Big Development
 * 
 */
public class CreditCard {
    @JsonProperty("number") public String number; 
    @JsonProperty("expirationMonth") public String expirationMonth;     
    @JsonProperty("expirationYear") public String expirationYear;     
    @JsonProperty("cvv") public String cvv;

    public static final String STRING_FORMAT = "Credit Card [number = %s, expiration month = %s, expiration year = %s, cvv = %s]";

    /**
     * Create a user with the given id and name
     * @param number The number credit card
     * @param expirationMonth The expiration month name 
     * @param expirationYear The expirationYear 
     * @param cvv The cvv
     */
    public CreditCard(@JsonProperty("number") String number,
                   @JsonProperty("expirationMonth") String expirationMonth,
                   @JsonProperty("expirationYear") String expirationYear,
                   @JsonProperty("cvv") String cvv) {
        this.number = number;
        this.expirationMonth = expirationMonth; 
        this.expirationYear = expirationYear; 
        this.cvv = cvv;
    }

    /**
     * get the number
     * 
     * @return number
     */
    public String getNumber() { return this.number; }

    /**
     * set the number
     * 
     * @param number the new number value
     */
    public void setNumber(String number) { this.number = number; }

    /**
     * get the expirationMonth
     * 
     * @return expiration month
     */
    public String getExpirationMonth() { return this.expirationMonth; }

    /**
     * set the expiration month
     * 
     * @param expirationMonth the new expiration month value
     */
    public void setExpirationMonth(String expirationMonth) { this.expirationMonth = expirationMonth; }

    /**
     * get the expirationYear 
     * 
     * @return expirationYear
     */
    public String getExpirationYear() { return this.expirationYear; }

    /**
     * set the expirationYear
     * 
     * @param expirationYear the new expirationYear value 
     */
    public void setExpirationYear(String expirationYear) { this.expirationYear = expirationYear; }

    /**
     * get the cvv
     * 
     * @return cvv
     */
    public String getCvv() { return this.cvv; }

    /**
     * set the cvv
     * 
     * @param cvv the new cvv value
     */
    public void setCvv(String cvv) { this.cvv = cvv; }

    

    /**
     * converts an credit card object into a displayable string
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, this.number, this.expirationMonth, this.expirationYear, this.cvv);
    }

    /**
     * checks to see if two creditcard objects are equal
     * 
     * @param other the object to compare to this one
     * @return true if they are equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if(other == this)
            return true; 
        if(!(other instanceof CreditCard))
            return false; 
        CreditCard o = (CreditCard)other; 
        return this.number.equals(o.number) &&
               this.expirationMonth.equals(o.expirationMonth) &&
               this.expirationYear.equals(o.expirationYear) &&
               this.cvv.equals(o.cvv); 
    }



}
