package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Product
 * 
 * @author SWEN 05 Team 4D - Big Development
 * 
 */
public class Product {
    // private static final Logger LOG = Logger.getLogger(Product.class.getName());

    @JsonProperty("Id")
    public int Id;
    @JsonProperty("Price")
    public double Price;
    @JsonProperty("Name")
    public String Name;
    @JsonProperty("Description")
    public String Description;

    /**
     * Create a product with the given id and name
     * 
     * @param Id   The id of the product
     * @param Name The name of the product
     * 
     */
    public Product(@JsonProperty("Id") int Id, @JsonProperty("Price") double Price,
            @JsonProperty("Name") String Name, @JsonProperty("Description") String Description) {
        this.Id = Id;
        this.Price = Price;
        this.Name = Name;
        this.Description = Description;
    }

    /**
     * Retrieves the id of the product
     * 
     * @return The id of the product
     */
    public int getId() {
        return Id;
    }

    /**
     * Sets the price of the product - necessary for JSON object to Java object
     * deserialization
     * 
     * @param Price The price of the product
     */
    public void setPrice(double Price) {
        this.Price = Price;
    }

    /**
     * Retrieves the price of the product
     * 
     * @return The price of the product
     */
    public double getPrice() {
        return this.Price;
    }

    /**
     * Sets the name of the product - necessary for JSON object to Java object
     * deserialization
     * 
     * @param Name The name of the product
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     * Retrieves the name of the product
     * 
     * @return The name of the product
     */
    public String getName() {
        return Name;
    }

    /**
     * Sets the description of the product - necessary for JSON object to Java
     * object deserialization
     * 
     * @param Description The description of the product
     */
    public void setDescription(String Description) {
        this.Description = Description;
    }

    /**
     * Retrieves the description of the product
     * 
     * @return The description of the product
     */
    public String getDescription() {
        return Description;
    }

    @Override
    public String toString() {
        return "Product [ID=" + this.Id +
                ", Price=" + this.Price +
                ", Name=" + this.Name +
                ", Description=" + this.Description + "]";
    }
}
