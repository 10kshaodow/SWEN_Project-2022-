package com.estore.api.estoreapi.controller;

import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.persistence.IProductDAO;

/**
 * Handles the REST API requests for the Product resource
 * 
 * @author SWEN 05 Team 4D - Big Development
 */
@RestController
@RequestMapping("products")
public class ProductController {
    private IProductDAO productDao;
    private static final Logger LOG = Logger.getLogger(ProductController.class.getName());

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param productDao The {@link productDAO product Data Access Object} to
     *                   perform CRUD operations
     */
    public ProductController(IProductDAO productDao) {
        this.productDao = productDao;
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

    @GetMapping("")
    public ResponseEntity<Product[]> getAllProducts() {
        LOG.info("GET /products");

        try {
            Product[] products = productDao.getAllProducts();

            return new ResponseEntity<Product[]>(products, HttpStatus.OK);
        } catch (IOException e) {
            return this.errorHandler(e);
        }
    }

}
