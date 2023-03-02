package com.estore.api.estoreapi.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estore.api.estoreapi.model.Order;
import com.estore.api.estoreapi.persistence.IOrderDAO;

/**
 * Handles the REST API requests for the Order resource
 * 
 * @author SWEN 05 Team 4D - Big Development
 */
@RestController
@RequestMapping("orders")
public class OrderController {
    private IOrderDAO orderDao;
    private static final Logger LOG = Logger.getLogger(OrderController.class.getName());

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param orderDao The {@link orderDAO order Data Access Object} to
     *                 perform CRUD operations
     */
    public OrderController(IOrderDAO orderDao) {
        this.orderDao = orderDao;
    }

    /**
     * simple error handler for requests that may throw an error
     * logs to console and returns a response of specified type
     * 
     * @param <T> takes in a generalized type
     * @param e   takes in the error message
     * @return a response entity of the specified typ
     */
    private <T> ResponseEntity<T> errorHandler(Exception e) {
        LOG.severe(e.getLocalizedMessage());
        return new ResponseEntity<T>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // insert your methods here

    /**
     * Gets the entire orders array from the DAO
     * 
     * @return ResponseEntity with a okay status and the orders array or
     *         a ResponseEntity with a Internal Server Error if something went wrong
     *         loading the data
     */
    @GetMapping("")
    public ResponseEntity<Order[]> getAllOrders() {
        LOG.info("GET /orders");

        try {
            Order[] orders = orderDao.getAllOrders();
            for (Order order : orders) {
                System.out.println(order);
            }

            return new ResponseEntity<Order[]>(orders, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return this.errorHandler(e);
        }
    }

    /**
     * Retrieves a {@linkplain Order order} with the given id
     * 
     * @param id The id of the {@link Order order} to get
     * 
     * @return a {@link Order order} object with the matching id
     *         <br>
     *         null if no {@link Order order} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable int id) {
        LOG.info("GET /orders/" + id);

        try {
            Order order = orderDao.getOrder(id);
            if (order != null)
                return new ResponseEntity<Order>(order, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Order order} with a given id
     * 
     * @param order - The {@link Order order} to create
     * 
     * @return a {@link Order order} object with the matching id
     *         <br>
     *         will be removed from the order list
     * 
     * @throws IOException if an issue with underlying storage, such as an invalid
     *                     id.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Order> deleteOrder(@PathVariable int id) {
        LOG.info("DELETE /orders/" + id);
        try {
            Boolean order_void = orderDao.deleteOrder(id);
            Order order = orderDao.getOrder(id);
            if (order_void != false)
                return new ResponseEntity<Order>(order, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Order order} with the provided order object
     * 
     * @param order - The {@link Order order} to create
     * 
     * @return ResponseEntity with created {@link Order order} object and HTTP
     *         status of CREATED<br>
     *         ResponseEntity with HTTP status of CONFLICT if {@link Order
     *         order} object already exists<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        LOG.info("POST /orders " + order);

        try {
            Order newOrder = orderDao.createOrder(order);
            if (newOrder != null)
                return new ResponseEntity<Order>(newOrder, HttpStatus.CREATED);
            else
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Updates the {@linkplain Order order} with the provided
     * {@linkplain Order order} object, if it exists
     * 
     * @param order The {@link Order order} to update
     * 
     * @return ResponseEntity with updated {@link Order order} object and HTTP
     *         status of OK if updated<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Order> updateOrder(@RequestBody Order order) {
        LOG.info("PUT /orders " + order);

        try {
            Order newOrder = orderDao.updateOrder(order);
            if (newOrder != null)
                return new ResponseEntity<Order>(newOrder, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
