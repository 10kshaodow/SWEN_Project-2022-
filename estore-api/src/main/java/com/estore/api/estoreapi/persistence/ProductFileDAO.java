package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Implements the functionality for JSON file-based peristance for Products
 * 
 * @author SWEN Faculty
 */
@Component
public class ProductFileDAO implements IProductDAO {
    private static final Logger LOG = Logger.getLogger(ProductFileDAO.class.getName());

    // local cache of products
    Map<Integer, Product> products;

    // provides conversion between Product object and their JSON representation
    private ObjectMapper objectMapper;

    // next Id to assign
    private static int nextId;

    // Filename to read from and write to
    private String filename;

    /**
     * Creates a Product File Data Access Object
     * 
     * @param filename     Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization
     *                     and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public ProductFileDAO(@Value("${products.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        // load products from the file
        load();
    }

    /**
     * Generates the next id for a new {@linkplain Product product}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Saves the products from the map into the file as an array of JSON objects
     * 
     * @return true if the products were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        // TODO: when getProductsArray is implemented this needs to work
        Product[] productArray = this.getProductsArray();

        // Serializes the products to JSON format and write to a file
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.writeValue(new File(filename), productArray);
        return true;
    }

    /**
     * Loads products from the JSON file into the map and sets the next id to
     * the largest found id
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        products = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of products
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Product[] productArray = objectMapper.readValue(new File(filename), Product[].class);

        // Add each product to the tree map and keep track of the greatest id
        for (Product product : productArray) {
            products.put(product.getId(), product);
            if (product.getId() > nextId)
                nextId = product.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    /**
     * Collects all the values from the tree of products
     * and puts them into an array of products
     * 
     * @returns array of Product objects, could be empty
     */
    private Product[] getProductsArray() {
        ArrayList<Product> products = new ArrayList<>();

        for (Product product : this.products.values()) {
            products.add(product);
        }

        Product[] productArray = new Product[products.size()];
        products.toArray(productArray);
        return productArray;
    }


     /**
    ** {@inheritDoc}
     */
    @Override
    public Product getProduct(int id) {
        synchronized(products) {
            if (products.containsKey(id))
                return products.get(id);
            else
                return null;
        }
    }


    /**
     * Generates an array of prodcuts from the tree map for any
     * products that contains the text specified by containsText
     * 
     * If containsText is null, the array contains all of the products
     * in the tree map
     * 
     * @return  The array of oroducts, may be empty
     */
    private Product[] getProductsArray(String containsText) { // if containsText == null, no filter
        ArrayList<Product> heroArrayList = new ArrayList<>();

        for (Product product : products.values()) {
            if (containsText == null || product.containsSearchTerm(containsText)) {
                heroArrayList.add(product);
            }
        }

        Product[] heroArray = new Product[heroArrayList.size()];
        heroArrayList.toArray(heroArray);
        return heroArray;
    }

    /**
     * Retrieves all Products
     * 
     * @return An array of Product objects, may be empty
     * 
     */
    @Override
    public Product[] getAllProducts() throws IOException {
        synchronized (products) {
            return this.getProductsArray();
        }
    };


    /**
    ** 
     */
    @Override
    public Product[] findProducts(String searchText) {
        synchronized(products) {
            return getProductsArray(searchText);
        }
    }

        /**
    ** {@inheritDoc}
     */
    @Override
    public Product createProduct(Product product) throws IOException {
        synchronized(products) {
            // We create a new product object because the id field is immutable
            // and we need to assign the next unique id
            Product newProduct = new Product(nextId(), product.getPrice(), product.getName(), product.getDescription(), product.getQuantity());
            products.put(newProduct.getId(),newProduct);
            save(); // may throw an IOException
            return newProduct;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Product updateProduct(Product product) throws IOException {
        synchronized(products) {
            if (products.containsKey(product.getId()) == false)
                return null;  // product does not exist

            products.put(product.getId(),product);
            save(); // may throw an IOException
            return product;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteProduct(int id) throws IOException {
        synchronized(products) {
            if (products.containsKey(id)) {
                products.remove(id);
                return save();
            }
            else
                return false;
        }
    }



}
