package com.estore.api.estoreapi.model;

import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * represents an order
 * 
 * @author SWEN 05 Team 4D - Big Development
 */
public class Order {
    @JsonProperty("id")
    public int id;
    @JsonProperty("orderItemList")
    public ArrayList<OrderItem> orderItemList = new ArrayList<OrderItem>(); // dictionary of <orderItemId, quantity>
    @JsonProperty("orderDate")
    public Date orderDate;
    @JsonProperty("status")
    public OrderStatus status;
    @JsonProperty("username")
    public String username;
    @JsonProperty("address")
    public Address address;
    @JsonProperty("creditCard")
    public CreditCard creditCard;
    @JsonProperty("subtotal")
    public double subtotal;
    @JsonProperty("taxes")
    public double taxes;
    @JsonProperty("total")
    public double total;

    /**
     * creates a new order object
     * 
     * @param id        the order id
     * @param arrayList the dictionary of <orderItem id, quantity>
     * @param orderDate the date of the order
     */
    public Order(@JsonProperty("id") int id,
            @JsonProperty("orderItemList") ArrayList<OrderItem> orderItemList,
            @JsonProperty("orderDate") Date orderDate,
            @JsonProperty("status") OrderStatus status,
            @JsonProperty("username") String username,
            @JsonProperty("address") Address address,
            @JsonProperty("creditCard") CreditCard creditCard,
            @JsonProperty("subtotal") double subtotal,
            @JsonProperty("taxes") double taxes,
            @JsonProperty("total") double total) {
        this.id = id;
        this.orderItemList = orderItemList;
        this.orderDate = orderDate;
        this.status = status;
        this.username = username;
        this.address = address;
        this.creditCard = creditCard;
        this.subtotal = subtotal;
        this.taxes = taxes;
        this.total = total;
    }

    /**
     * get the id of the order
     * 
     * @return id
     */
    public int getId() {
        return this.id;
    }

    /**
     * get the orderItem order list
     * 
     * @return the orderItem order list
     */
    public ArrayList<OrderItem> getOrderItemList() {
        return this.orderItemList;
    }

    /**
     * set the orderItem order list
     */
    public void setOrderItemList(ArrayList<OrderItem> newOrderItemList) {
        this.orderItemList = newOrderItemList;
    }

    /**
     * add a orderItem to the dictionary
     * 
     * @param orderItemId the orderItem id
     * @param quantity    the quantity of the orderItemList purchased
     */
    public void addOrderItem(OrderItem newOrderItem) {
        int newID = newOrderItem.getProductID();

        if (findOrderItemByID(newID) != null) {
            OrderItem existingOrderItem = findOrderItemByID(newID);
            existingOrderItem.combineOrderItems(newOrderItem);
        } else {
            this.orderItemList.add(newOrderItem);
        }
    }

    public OrderItem findOrderItemByID(int productID) {

        for (int i = 0; i < orderItemList.size(); i++) {
            if (orderItemList.get(i).getProductID() == productID) {
                return orderItemList.get(i);
            }
        }

        return null;
    }

    public int findOrderItemIndex(int productID) {
        int index = -1;

        for (int i = 0; i < this.orderItemList.size(); i++) {
            int itemProductID = this.orderItemList.get(i).getProductID();
            if (itemProductID == productID) {
                index = i;
            }
        }

        return index;
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
        } else {
            this.orderItemList.remove(itemIndex);
        }
    }

    /**
     * update the quantity of a orderItem purchased
     * 
     * @param orderItemId the orderItem to update
     * @param quantity    the new quantity of the item
     */
    public void updateOrderItemQuantity(int productID, int quantity) {
        int itemIndex = this.findOrderItemIndex(productID);
        if (itemIndex == -1) {
            System.out.print("Product not found");
        } else {
            this.orderItemList.get(itemIndex).setQuantity(quantity);
        }
    }

    /**
     * get the order date
     * 
     * @return order date
     */
    public Date getOrderDate() {
        return this.orderDate;
    }

    /**
     * set the order date
     * 
     * @param orderDate the order date
     */
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * gets the order status
     * 
     * @return the order status
     */
    public OrderStatus getStatus() {
        return this.status;
    }

    /**
     * set the order status
     * 
     * @param status the new order status
     */
    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    /**
     * gets the username
     * 
     * @return the username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * set the order username
     * 
     * @param username the new order username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * gets the address
     * 
     * @return the address
     */
    public Address getAddress() {
        return this.address;
    }

    /**
     * set the order address
     * 
     * @param address the new order address
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * gets the creditCard
     * 
     * @return the creditCard
     */
    public CreditCard getCreditCard() {
        return this.creditCard;
    }

    /**
     * set the order creditCard
     * 
     * @param creditCard the new order creditCard
     */
    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    /**
     * gets the order total
     *
     * @return the total
     */
    public double getTotal() {
        return this.total;
    }

    /**
     * set the order total
     * 
     * @param address the new order total
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * gets the subtotal
     * 
     * @return the subtotal
     */
    public double getSubtotal() {
        return this.subtotal;
    }

    /**
     * set the order subtotal
     * 
     * @param subtotal the new order subtotal
     */
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    /**
     * gets the taxes
     * 
     * @return the taxes
     */
    public double getTaxes() {
        return this.taxes;
    }

    /**
     * set the order taxes
     * 
     * @param taxes the new order taxes
     */
    public void setTaxes(double taxes) {
        this.taxes = taxes;
    }

    /**
     * converts an order object into a displayable string
     */
    @Override
    public String toString() {
        return "Order [id=" + this.id +
                ", Order Items=" + this.orderItemList +
                ", orderDate=" + this.orderDate +
                ", status=" + this.status +
                ", username=" + this.username +
                ", address=" + this.address +
                ", credit card =" + this.creditCard +
                "]";
    }

    /**
     * checks to see if two order objects are equal
     * 
     * @param other the object to compare to this one
     * @return true if they are equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this)
            return true;
        if (!(other instanceof Order))
            return false;
        Order o = (Order) other;
        return this.id == o.id &&
                this.orderItemList.equals(o.orderItemList) &&
                this.orderDate.equals(o.orderDate) &&
                this.status == o.status &&
                this.username.equals(o.username) &&
                this.address.equals(o.address) &&
                this.creditCard.equals(o.creditCard) &&
                this.subtotal == o.subtotal &&
                this.taxes == o.taxes &&
                this.total == o.total;

    }

}
