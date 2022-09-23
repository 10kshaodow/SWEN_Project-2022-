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
import org.springframework.web.bind.annotation.RequestParam;
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
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return this.errorHandler(e);
        }
    }


    /**
     * Responds to the GET request for all products whose name contains
     * the text in name
     * 
     * @param name The search term for the product
     * 
     * @return ResponseEntity with array of product objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     * Example: Find all product that contain the text "fa"
     * GET http://localhost:8080/heroes/?searchTerm=fa
     */
    @GetMapping("/")
    public ResponseEntity<Product[]> searchProducts(@RequestParam String searchTerm) {
        LOG.info("GET /heroes/?searchTerm="+searchTerm);
        try {
            Product[] heros = productDao.findProducts(searchTerm);
            return new ResponseEntity<Product[]>(heros, HttpStatus.OK);
        } catch(IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable int id) {
        LOG.info("DELETE /products/" + id);
        try {
            Boolean product_void = productDao.deleteProduct(id);
            Product product = productDao.getProduct(id);
            if (product_void != false)
                return new ResponseEntity<Product>(product,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
