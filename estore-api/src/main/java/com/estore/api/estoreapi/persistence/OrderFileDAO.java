package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Implements the functionality for JSON file-based peristance for Orders
 * 
 * @author SWEN Faculty
 */
@Component
public class OrderFileDAO implements IOrderDAO {
    private static final Logger LOG = Logger.getLogger(OrderFileDAO.class.getName());

    // local cache of orders
    Map<Integer, Order> orders;

    // provides conversion between Order object and their JSON representation
    private ObjectMapper objectMapper;

    // next Id to assign
    private static int nextId;

    // Filename to read from and write to
    private String filename;

    /**
     * Creates a Order File Data Access Object
     * 
     * @param filename     Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization
     *                     and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public OrderFileDAO(@Value("${orders.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        objectMapper.setDateFormat(df);

        // load orders from the file
        load();
    }

    /**
     * Generates the next id for a new {@linkplain Order order}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Saves the orders from the map into the file as an array of JSON objects
     * 
     * @return true if the orders were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        // TODO: when getOrdersArray is implemented this needs to work
        Order[] orderArray = this.getOrdersArray();

        // Serializes the orders to JSON format and write to a file
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.writeValue(new File(filename), orderArray);
        return true;
    }

    /**
     * Loads orders from the JSON file into the map and sets the next id to
     * the largest found id
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        orders = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of orders
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Order[] orderArray = objectMapper.readValue(new File(filename), Order[].class);

        // Add each order to the tree map and keep track of the greatest id
        for (Order order : orderArray) {
            orders.put(order.getId(), order);
            if (order.getId() > nextId)
                nextId = order.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    /**
     * Collects all the values from the tree of orders
     * and puts them into an array of orders
     * 
     * @returns array of Order objects, could be empty
     */
    private Order[] getOrdersArray() {
        ArrayList<Order> orders = new ArrayList<>();

        for (Order order : this.orders.values()) {
            orders.add(order);
        }

        Order[] orderArray = new Order[orders.size()];
        orders.toArray(orderArray);
        return orderArray;
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Order getOrder(int id) {
        synchronized (orders) {
            if (orders.containsKey(id))
                return orders.get(id);
            else
                return null;
        }
    }

    /**
     * Retrieves all Orders
     * 
     * @return An array of Order objects, may be empty
     * 
     */
    @Override
    public Order[] getAllOrders() throws IOException {
        synchronized (orders) {
            return this.getOrdersArray();
        }
    };

    /**
     ** {@inheritDoc}
     */
    @Override
    public Order createOrder(Order order) throws IOException {
        synchronized (orders) {
            // We create a new order object because the id field is immutable
            // and we need to assign the next unique id
            Order newOrder = new Order(nextId(), order.getOrderItemList(), new Date(), order.getStatus(),
                    order.getUsername(), order.getAddress(), order.getCreditCard(), order.getSubtotal(),
                    order.getTaxes(), order.getTotal());
            orders.put(newOrder.getId(), newOrder);
            save(); // may throw an IOException
            return newOrder;
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Order updateOrder(Order order) throws IOException {
        synchronized (orders) {
            if (orders.containsKey(order.getId()) == false)
                return null; // order does not exist

            orders.put(order.getId(), order);
            save(); // may throw an IOException
            return order;
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public boolean deleteOrder(int id) throws IOException {
        synchronized (orders) {
            if (orders.containsKey(id)) {
                orders.remove(id);
                return save();
            } else
                return true;
        }
    }

}
