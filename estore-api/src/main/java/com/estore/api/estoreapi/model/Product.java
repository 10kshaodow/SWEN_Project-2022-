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

    @JsonProperty("id") public int id; 
    @JsonProperty("price") public double price; 
    @JsonProperty("name") public String name; 
    @JsonProperty("description") public String description; 
    @JsonProperty("quantity") public int quantity; 
    

    /**
     * Create a product with the given id and name
     * @param id The id of the product
     * @param name The name of the product
     * 
     */
    public Product(@JsonProperty("id") int id, @JsonProperty("price") double price, 
                   @JsonProperty("name") String name, @JsonProperty("description") String description,
                   @JsonProperty("quantity") int quantity) {
        this.id = id;
        this.price = price; 
        this.name = name;
        this.description = description; 
        this.quantity = quantity; 
    }

    /**
     * Retrieves the id of the product
     * 
     * @return The id of the product
     */
    public int getId() {return id;}

    /**
     * Sets the price of the product - necessary for JSON object to Java object deserialization
     * @param price The price of the product
     */
    public void setPrice(double price) {this.price = price;}

    /**
     * Retrieves the price of the product
     * 
     * @return The price of the product
     */
    public double getPrice() {return this.price;}

    /**
     * Sets the name of the product - necessary for JSON object to Java object deserialization
     * @param name The name of the product
     */
    public void setName(String name) {this.name = name;}

    /**
     * Retrieves the name of the product
     * 
     * @return The name of the product
     */
    public String getName() {return name;}
    
    /**
     * Sets the description of the product - necessary for JSON object to Java object deserialization
     * @param description The description of the product
     */
    public void setDescription(String description) {this.description = description;}

    /**
     * Retrieves the description of the product
     * 
     * @return The description of the product
     */
    public String getDescription() {return description;}
    
    /**
     * Sets the quantity of the product - necessary for JSON object to Java object deserialization
     * @param quantity The quantity of the product
     */
    public void setQuantity(int quantity) {this.quantity = quantity;}

    /**
     * Retrieves the quantity of the product
     * 
     * @return The quantity of the product
     */
    public int getQuantity() {return this.quantity;}

    @Override
    public String toString() {
        return "Product [id=" + this.id +
        ", price=" + this.price + 
        ", name=" + this.name + 
        ", description=" + this.description + "]";
    }

    /**
     * Determines if this product contains search text
     * 
     * @param searchText the text to search this product for
     * 
     * @return true when the searchText is found in this product
     */
    public boolean containsSearchTerm(String searchText)
    {
        return this.name.toLowerCase().contains(searchText.toLowerCase()) ||
               this.description.toLowerCase().contains(searchText.toLowerCase()); 
    }
}
