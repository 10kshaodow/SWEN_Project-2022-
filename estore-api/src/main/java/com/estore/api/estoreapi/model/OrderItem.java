package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderItem {
    
    @JsonProperty("productID") public int productID; 
    @JsonProperty("quantity") public int quantity; 
    @JsonProperty("price") public double price;

    public OrderItem(@JsonProperty("productID") int productID, @JsonProperty("price") double price, @JsonProperty("quantity") int quantity){
        this.productID = productID;
        this.price = price;
        this.quantity = quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public double getPrice(){
        return this.price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public int getProductID(){
        return this.productID;
    }

    public OrderItem combineOrderItems(OrderItem newOrderItem){
        if(newOrderItem.getProductID() == this.productID){
            int combinedQuantity = newOrderItem.getQuantity() + this.quantity;
            this.setQuantity(combinedQuantity);
        }

        return this;
    }

    @Override
    public String toString(){
        String outString = "[ prooductID: " + this.productID + ", quantity: " + this.quantity + "]";
        return outString;
    }

    /**
     * checks to see if two orderitem objects are equal
     * 
     * @param other the object to compare to this one
     * @return true if they are equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if(other == this)
            return true; 
        if(!(other instanceof OrderItem))
            return false; 
        OrderItem o = (OrderItem)other; 
        return this.productID == o.productID &&
               this.quantity == o.quantity; 
    }
}
