package com.estore.api.estoreapi.controller;

import java.io.IOException;
import java.util.Arrays;
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

import com.estore.api.estoreapi.exceptions.InvalidProductException;
import com.estore.api.estoreapi.exceptions.ProductNameTakenException;
import com.estore.api.estoreapi.exceptions.ProductTypeChangeException;
import com.estore.api.estoreapi.model.products.Product;
import com.estore.api.estoreapi.persistence.IFileDAO;
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
    private IFileDAO imageFileDao;
    private static final Logger LOG = Logger.getLogger(ProductController.class.getName());

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param productDao The {@link productDAO product Data Access Object} to
     *                   perform CRUD operations
     */
    public ProductController(IProductDAO productDao, IFileDAO imageFileDao) {
        this.productDao = productDao;
        this.imageFileDao = imageFileDao;
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
     * Gets the entire products array from the DAO
     * 
     * @return ResponseEntity with a okay status and the products array or
     *         a ResponseEntity with a Internal Server Error if something went wrong
     *         loading the data
     */
    @GetMapping("")
    public ResponseEntity<Product[]> getAllProducts() {
        LOG.info("GET /products");

        try {
            Product[] products = productDao.getAllProducts();
            products = Arrays.stream(products)
                    .map((_product) -> {
                        _product.normalize();
                        return _product;
                    }).toArray(Product[]::new);
            return new ResponseEntity<Product[]>(products, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return this.errorHandler(e);
        }
    }

    /**
     * Retrieves a {@linkplain Product product} with the given id
     * 
     * @param id The id of the {@link Product product} to get
     * 
     * @return a {@link Product product} object with the matching id
     *         <br>
     *         null if no {@link Product product} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id) {
        LOG.info("GET /products/" + id);

        try {
            Product product = productDao.getProduct(id);
            if (product != null) {
                product.normalize();
                return new ResponseEntity<Product>(product, HttpStatus.OK);
            } else
                return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all products whose name contains
     * the text in name
     * 
     * @param id The id of the {@link Product product} to search for
     * 
     * @return ResponseEntity with array of product objects (may be empty) and
     *         HTTP status of OK<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     *         Example: Find all product that contain the text "fa"
     *         GET http://localhost:8080/products/?searchTerm=fa
     */
    //@GetMapping("/")
    @RequestMapping(value = "/", params = "searchTerm")
    public ResponseEntity<Product[]> searchProducts(@RequestParam String searchTerm) {
        LOG.info("GET /products/?searchTerm=" + searchTerm);
        try {
            Product[] products = Arrays.stream(productDao.findProducts(searchTerm))
                    .map((_product) -> {
                        _product.normalize();
                        return _product;
                    }).toArray(Product[]::new);

            return new ResponseEntity<Product[]>(products, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Gets all products of a certain type
     * @param type  The product type to filter by
     * @return      All products of the type given
     */
    @RequestMapping(value = "/", params = "type")
    public ResponseEntity<Product[]> getProductsOfType(@PathVariable int type){
        LOG.info("GET /products/type=" + type);

        try{
            Product[] products = Arrays.stream(productDao.getProductsOfType(type))
                .map((_product) -> {
                    _product.normalize();
                    return _product;
                }).toArray(Product[]::new);

            return new ResponseEntity<Product[]>(products, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Product product} with a given id
     * 
     * @param product - The {@link Product product} to create
     * 
     * @return a {@link Product product} object with the matching id
     *         <br>
     *         will be removed from the product list
     * 
     * @throws IOException if an issue with underlying storage, such as an invalid
     *                     id.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable int id) {
        LOG.info("DELETE /products/" + id);
        try {
            Product product = productDao.getProduct(id);

            if (product != null && product.hasOldImage()) {
                product.unNormalize();
                this.imageFileDao.removeFile(product.getFileName());
            }

            Boolean product_void = productDao.deleteProduct(id);
            if (product_void != false) {

                return new ResponseEntity<Product>(product, HttpStatus.OK);
            } else
                return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Product product} with the provided product object
     * 
     * @param product - The {@link Product product} to create
     * 
     * @return ResponseEntity with created {@link Product product} object and HTTP
     *         status of CREATED<br>
     *         ResponseEntity with HTTP status of CONFLICT if {@link Product
     *         product} object already exists<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * @throws IOException
     */
    @PostMapping("")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) throws IOException {
        LOG.info("POST /products " + product);
        String backupFileName = null;
        try {

            if (product.hasNewImage()) {
                if (product.fileSource.isValid()) {
                    String fileName = this.imageFileDao.saveBase64File(product.fileSource.base64,
                            product.name.replace(" ", "_")
                            // + "-" + new Date().getTime()
                            , product.fileSource.getImageType());
                    backupFileName = fileName;
                    product.setFileName(fileName);
                    product.setFileSource(null);
                } else {
                    LOG.info("\n\n");
                    LOG.info("Product Has Invalid new image");
                    LOG.info("\n\n");
                }

            }

            Product newProduct = productDao.createProduct(product);

            LOG.info("Created Product: " + newProduct);

            if (newProduct == null) {
                if (backupFileName != null) {
                    this.imageFileDao.removeFile(backupFileName);
                }
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }

            newProduct.normalize();
            return new ResponseEntity<Product>(newProduct, HttpStatus.CREATED);

        } catch (IOException e) {
            if (backupFileName != null) {
                this.imageFileDao.removeFile(backupFileName);
            }
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (InvalidProductException er) {
            if (backupFileName != null) {
                this.imageFileDao.removeFile(backupFileName);
            }
            LOG.log(Level.SEVERE, er.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        } catch (ProductNameTakenException err) {
            if (backupFileName != null) {
                this.imageFileDao.removeFile(backupFileName);
            }
            LOG.log(Level.SEVERE, err.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

    }

    /**
     * Updates the {@linkplain Product product} with the provided
     * {@linkplain Product product} object, if it exists
     * 
     * @param product The {@link Product product} to update
     * 
     * @return ResponseEntity with updated {@link Product product} object and HTTP
     *         status of OK if updated<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * @throws IOException
     */
    @PutMapping("")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) throws IOException {
        LOG.info("PUT /products " + product);
        String backupFileName = null;
        try {

            Product oldProduct = productDao.getProduct(product.id);

            if (product.hasNewImage() && product.fileSource.isValid()) {
                // if there is an image allready on the server delete it
                if (oldProduct.hasOldImage()) {
                    oldProduct.unNormalize();
                    this.imageFileDao.removeFile(oldProduct.fileName);
                }
                String fileName = this.imageFileDao.saveBase64File(product.fileSource.base64,
                        product.name
                        // + "-" + new Date().getTime()
                        , product.fileSource.getImageType());
                backupFileName = fileName;
                product.setFileName(fileName);
                product.setFileSource(null);
            }

            Product newProduct = productDao.updateProduct(product);

            if (newProduct == null) {
                if (backupFileName != null) {
                    this.imageFileDao.removeFile(backupFileName);
                }
                return new ResponseEntity<>(HttpStatus.OK);
            }

            newProduct.normalize();
            return new ResponseEntity<Product>(newProduct, HttpStatus.OK);
        } catch (IOException e) {
            if (backupFileName != null) {
                this.imageFileDao.removeFile(backupFileName);
            }
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (InvalidProductException e) {
            if (backupFileName != null) {
                this.imageFileDao.removeFile(backupFileName);
            }
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ProductTypeChangeException e) {
            if (backupFileName != null) {
                this.imageFileDao.removeFile(backupFileName);
            }
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
