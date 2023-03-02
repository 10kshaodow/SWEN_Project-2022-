package com.estore.api.estoreapi.model.products;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.estore.api.estoreapi.model.ImageSource;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Product
 * 
 * @author SWEN 05 Team 4D - Big Development
 * 
 */

public abstract class Product {
    private static final Logger LOG = Logger.getLogger(Product.class.getName());
    public static final String PRODUCT_STRING = "\nProduct {id=%d, productType=%d, name=%s, price=%f, quantity=%d, description=%s, fileName=%s, fileSource=%s}\n";

    @JsonProperty("id")
    public int id;
    @JsonProperty("productType")
    public int productType;
    @JsonProperty("price")
    public double price;
    @JsonProperty("name")
    public String name;
    @JsonProperty("description")
    public String description;
    @JsonProperty("quantity")
    public int quantity;
    @JsonProperty("fileName")
    public String fileName;
    @JsonProperty("fileSource")
    public ImageSource fileSource;

    /**
     * Create a product with the given id and name
     * 
     * @param id          The id of the product
     * @param name        The name of the product
     * @param price       The price of the product
     * @param quantity    The available amount of the product
     * @param description The description of the product
     * @param fileName    The fileName for the image assigned to the product
     * @param fileSource  The fileName for the image assigned to the product
     * 
     */

    public Product(@JsonProperty("id") int id,

            @JsonProperty("productType") int productType,
            @JsonProperty("price") double price,
            @JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("quantity") int quantity,
            @JsonProperty("fileName") String fileName,
            @JsonProperty("fileSource") ImageSource fileSource) {
        this.id = id;
        this.productType = productType;
        this.price = price;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.fileName = fileName;
        this.fileSource = fileSource;

    }

    /**
     * Retrieves the id of the product
     * 
     * @return The id of the product
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the price of the product - necessary for JSON object to Java object
     * deserialization
     * 
     * @param price The price of the product
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Retrieves the price of the product
     * 
     * @return The price of the product
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * Sets the name of the product - necessary for JSON object to Java object
     * deserialization
     * 
     * @param name The name of the product
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the fileName of the product - necessary for JSON object to Java object
     * deserialization
     * 
     * @param fileName The fileName of the product
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Sets the fileSource of the product - necessary for JSON object to Java object
     * deserialization
     * 
     * @param fileSource The fileSource of the product
     */
    public void setFileSource(ImageSource fileSource) {
        this.fileSource = fileSource;
    }

    /**
     * Retrieves the name of the product
     * 
     * @return The name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the description of the product - necessary for JSON object to Java
     * object deserialization
     * 
     * @param description The description of the product
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves the description of the product
     * 
     * @return The description of the product
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the quantity of the product - necessary for JSON object to Java object
     * deserialization
     * 
     * @param quantity The quantity of the product
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Retrieves the quantity of the product
     * 
     * @return The quantity of the product
     */
    public int getQuantity() {
        return this.quantity;
    }

    /**
     * Retrieves the filename of the product
     * 
     * @return The filename of the product
     */
    public String getFileName() {
        return this.fileName;
    }

    /**
     * Retrieves the file source object of the product
     * 
     * @return The file source object of the product
     */
    public ImageSource getFileSource() {
        return this.fileSource;
    }

    /**
     * Retrieves the Product Type object of the product
     * 
     * @return The Product Type object of the product
     */
    public int getProductType() {
        return this.productType;
    }

    /**
     * Sets the productType of the product - necessary for JSON object to Java
     * object
     * deserialization
     * 
     * @param productType The productType of the product
     */
    public void setProductType(int type) {
        this.productType = type;
    }

    @Override
    public String toString() {
        return String.format(PRODUCT_STRING, this.id, this.productType, this.name, this.price, this.quantity,
                this.description,
                this.fileName, this.fileSource);
    }

    /**
     * Determines if this product contains search text
     * 
     * @param searchText the text to search this product for
     * 
     * @return true when the searchText is found in this product
     */
    public boolean containsSearchTerm(String searchText) {
        return this.name.toLowerCase().contains(searchText.toLowerCase()) ||
                this.description.toLowerCase().contains(searchText.toLowerCase());
    }

    /***
     * Checks if the product is properly
     * filled out logs what is missing if not properly filled out
     * 
     * @return True If the product is valid
     *         false if the product is invalid
     */
    @JsonIgnore
    public boolean isValidProduct() {
        if (this.quantity <= 0) {
            LOG.log(Level.WARNING, "Product is missing: quantity <= 0");
            return false;
        }
        if (this.price <= 0) {
            LOG.log(Level.WARNING, "Product is missing: price <= 0");
            return false;
        }
        if (this.name == null || this.name.length() <= 0) {
            LOG.log(Level.WARNING, "Product is missing: name.length() <= 0");
            return false;
        }
        if (this.description == null || this.description.length() <= 0) {
            LOG.log(Level.WARNING, "Product is missing: description.length() <= 0");
            return false;
        }
        return true;
    }

    /**
     * See if the product has a new fileSource
     * 
     * @return True if it does false if not
     */
    public Boolean hasNewImage() {
        if (this.fileSource == null)
            return false;
        return true;
    }

    /**
     * See if the product has a old fileName saved
     * 
     * @return True if it does false if not
     */
    public Boolean hasOldImage() {
        if (this.fileName == null)
            return false;
        return true;
    }

    /**
     * Turn the file name into a url for the front end
     */
    public void normalize() {
        if (this.fileName == null)
            return;
        String[] split = this.fileName.split("/");

        if (split.length > 1)
            return;

        String pureFileName = this.fileName;
        String fullPath = "http://localhost:8080/static/" + pureFileName;
        this.fileName = fullPath;

    }

    /**
     * Turn the filename from a url into the actual files name
     */
    public void unNormalize() {
        if (this.fileName == null)
            return;

        String[] split = this.fileName.split("/");

        if (split.length <= 1)
            return;

        String pureFileName = this.fileName.replace("http://localhost:8080/static/", "");

        this.fileName = pureFileName;
    }

}
