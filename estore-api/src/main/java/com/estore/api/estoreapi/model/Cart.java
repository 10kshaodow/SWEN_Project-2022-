package com.estore.api.estoreapi.model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Cart {
    
    @JsonProperty("orderItemList") public ArrayList<OrderItem> orderItemList;
    public Cart( @JsonProperty("orderItemList") ArrayList<OrderItem> orderItemList){

        this.orderItemList = new ArrayList<OrderItem>();
        for(OrderItem newItem : orderItemList){
            this.addOrderItem(newItem);
        }
    }

    public ArrayList<OrderItem> getOrderItemList(){
        return this.orderItemList;
    }

    public void addOrderItem(OrderItem newOrderItem){
        int newID = newOrderItem.getProductID();

        if(findOrderItemByID(newID) != null){
            OrderItem existingOrderItem = findOrderItemByID(newID);
            existingOrderItem.combineOrderItems(newOrderItem);
        }
        else{
            this.orderItemList.add(newOrderItem);
        }
    }

    /**
     * update the quantity of a orderItem purchased
     * 
     * @param orderItemId the orderItem to update
     * @param quantity the new quantity of the item
     */
    public void updateOrderItemQuantity(int productID, int quantity) {
        int itemIndex = this.findOrderItemIndex(productID);
        if (itemIndex == -1) {
            System.out.print("Product not found");
        }
        else{
            this.orderItemList.get(itemIndex).setQuantity(quantity);
        }
    }

     /**
     * remove a orderItem from the order
     * 
     * @param orderItemId the orderItem to remove from the order
     */
    public void removeOrderItem(int productID) { 
        int itemIndex = this.findOrderItemIndex(productID);
        if (itemIndex == -1) {
            System.out.print("Delete: Product not found");
        }
        else{
            this.orderItemList.remove(itemIndex);
        }
    }

    public OrderItem findOrderItemByID(int productID){

        for(int i = 0; i < orderItemList.size(); i++){
            if(orderItemList.get(i).getProductID() == productID){
                return orderItemList.get(i);
            }
        }

        return null; 
    }

    public int findOrderItemIndex(int productID){
        int index = -1;
    
        for (int i = 0; i < this.orderItemList.size(); i++) {
          int itemProductID = this.orderItemList.get(i).getProductID();
          if (itemProductID == productID) {
            index = i;
          }
        }
    
        return index;
      }

    public String toString(){
        String outString = "{";
        for(OrderItem item : orderItemList){
            outString += item + " | ";
        }

        return outString + "}";
    }

    /**
     * checks to see if two cart objects are equal
     * 
     * @param other the object to compare to this one
     * @return true if they are equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if(other == this)
            return true; 
        if(!(other instanceof Cart))
            return false; 
        Cart o = (Cart)other; 
        if(!(this.orderItemList.size() == o.orderItemList.size()))
            return false; 
        for(int i = 0; i < orderItemList.size(); i++) {
            if(!orderItemList.get(i).equals(o.orderItemList.get(i)))
                return false; 
        }
        return true; 
    }


}

