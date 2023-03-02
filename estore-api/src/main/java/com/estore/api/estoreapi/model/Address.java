package com.estore.api.estoreapi.model;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a User adress
 * 
 * @author SWEN 05 Team 4D - Big Development
 * 
 */
public class Address {
    @JsonProperty("street") public String street; 
    @JsonProperty("city") public String city;     
    @JsonProperty("state") public String state;     
    @JsonProperty("country") public String country;
    @JsonProperty("zipcode") public String zipcode; 
    

    /**
     * Create a user with the given id and name
     * @param street The street address
     * @param city The city name 
     * @param state The state 
     * @param country The country
     * @param zipcode The zipcode
     */
    public Address(@JsonProperty("street") String street,
                   @JsonProperty("city") String city,
                   @JsonProperty("state") String state,
                   @JsonProperty("country") String country,
                   @JsonProperty("zipcode") String zipcode ) {
        this.street = street;
        this.city = city; 
        this.state = state; 
        this.country = country; 
        this.zipcode = zipcode; 
    }

    /**
     * get the street
     * 
     * @return street
     */
    public String getStreet() { return this.street; }

    /**
     * set the street
     * 
     * @param street the new street value
     */
    public void setStreet(String street) { this.street = street; }

    /**
     * get the city
     * 
     * @return city
     */
    public String getCity() { return this.city; }

    /**
     * set the city
     * 
     * @param city the new city value
     */
    public void setCity(String city) { this.city = city; }

    /**
     * get the state 
     * 
     * @return state
     */
    public String getState() { return this.state; }

    /**
     * set the state
     * 
     * @param state the new state value 
     */
    public void setState(String state) { this.state = state; }

    /**
     * get the country
     * 
     * @return country
     */
    public String getCountry() { return this.country; }

    /**
     * set the country
     * 
     * @param country the new country value
     */
    public void setCountry(String country) { this.country = country; }

    /**
     * get the zipcode
     * 
     * @return zipcode
     */
    public String getZipcode() { return this.zipcode; }

    /**
     * set the zipcode
     * 
     * @param zipcode the new zipcode value
     */
    public void setZipcode(String zipcode) { this.zipcode = zipcode; }

    /**
     * converts an address object into a displayable string
     */
    @Override
    public String toString() {
        return "Address [street=" + this.street +
        ", city=" + this.city + 
        ", state=" + this.state + 
        ", country=" + this.country + 
        ", zipcode=" + this.zipcode +"]";
    }

    /**
     * checks to see if two address objects are equal
     * 
     * @param other the object to compare to this one
     * @return true if they are equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if(other == this)
            return true; 
        if(!(other instanceof Address))
            return false; 
        Address o = (Address)other; 
        return this.street.equals(o.street) &&
               this.city.equals(o.city) &&
               this.state.equals(o.state) &&
               this.country.equals(o.country) &&
               this.zipcode.equals(o.zipcode); 
    }



}
