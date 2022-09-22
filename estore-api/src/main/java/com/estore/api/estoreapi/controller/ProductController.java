package com.estore.api.estoreapi.controller;

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

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.estore.api.estoreapi.persistence.IProductDAO;
import com.estore.api.estoreapi.model.Product;

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
     * @param productDao The {@link productDAO product Data Access Object} to perform CRUD operations
     */
    public ProductController(IProductDAO productDao) {
        this.productDao = productDao;
    }


    // insert your methods here


}
