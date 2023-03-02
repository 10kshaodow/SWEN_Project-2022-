package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.exceptions.InvalidProductException;
import com.estore.api.estoreapi.exceptions.ProductNameTakenException;
import com.estore.api.estoreapi.exceptions.ProductTypeChangeException;
import com.estore.api.estoreapi.model.ImageSource;
import com.estore.api.estoreapi.model.products.Accessory;
import com.estore.api.estoreapi.model.products.BirdProduct;
import com.estore.api.estoreapi.model.products.Product;
import com.estore.api.estoreapi.model.products.ProductCombinedSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class ProductFileDAOTest {

    @BeforeAll
    static private void resetJson() throws IOException, ProductNameTakenException, InvalidProductException {
        ProductFileDAO productFileDAO = buildDAO(null);
        Product bird = staticBird(1);
        bird.setName("Yessir");
        productFileDAO.createProduct(bird);
        Product[] products = productFileDAO.getAllProducts();
        for (Product prod : products) {
            productFileDAO.deleteProduct(prod.id);
        }
    }

    @Test
    void testCreateProductSuccedes() {
        try {
            ProductFileDAO productFileDAO = this.buildDao(null);
            Product accessory = this.createAccessory(1);
            Product bird = this.createBird(2);

            Product createdAcc = productFileDAO.createProduct(accessory);
            Product createdBird = productFileDAO.createProduct(bird);

            if (createdAcc == null) {
                assertFalse(true);
                return;
            }

            Product foundAcc = productFileDAO.getProduct(1);
            Product foundBird = productFileDAO.getProduct(2);

            assertEquals(createdAcc.id, foundAcc.id);
            assertEquals(createdBird.id, foundBird.id);
        } catch (Exception e) {
            assertFalse(true);
        }

    }

    @Test
    public void testCreateProductFailsProductNameTakenException() {
        try {
            ProductFileDAO productFileDAO = this.buildDao(null);
            Product accessory = this.createAccessory(1);

            Product createdAcc = productFileDAO.createProduct(accessory);

            if (createdAcc == null) {
                assertFalse(true);
                return;
            }

            assertFalse(true);
        } catch (ProductNameTakenException e) {
            assertTrue(true);
        } catch (Exception e) {
            assertFalse(false);
        }
    }

    @Test
    public void testCreateProductFailsInvalidProductException() {
        try {
            ProductFileDAO productFileDAO = this.buildDao(null);
            Product accessory = this.createAccessory(1);
            accessory.setName("hello");
            accessory.setQuantity(-1);

            Product createdAcc = productFileDAO.createProduct(accessory);

            if (createdAcc == null) {
                assertFalse(true);
                return;
            }

            assertFalse(true);
        } catch (InvalidProductException e) {
            assertTrue(true);
        } catch (Exception e) {
            assertFalse(false);
        }
    }

    @Test
    public void testGetProductByIdFails() {
        try {
            ProductFileDAO productFileDAO = this.buildDao(null);

            Product found = productFileDAO.getProduct(-1);

            if (found == null) {
                assertTrue(true);
                return;
            }

            assertFalse(false);

        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void testDeleteProductFails() {
        try {
            ProductFileDAO productFileDAO = this.buildDao(null);

            Boolean found = productFileDAO.deleteProduct(-1);

            if (found == false) {
                assertTrue(true);
                return;
            }

            assertFalse(false);

        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void testGetProductsByText() {
        try {
            ProductFileDAO productFileDAO = this.buildDao(null);

            Product[] allProducts = productFileDAO.getAllProducts();

            Product[] nullProducts = productFileDAO.getProductsArray(null);
            Product[] singularProduct = productFileDAO.getProductsArray("Bird");

            assertEquals(allProducts.length, nullProducts.length);
            assertEquals(1, singularProduct.length);

        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void testGetProductByType() {
        try {
            ProductFileDAO productFileDAO = this.buildDao(null);
            Product[] products = productFileDAO.getProductsOfType(1);

            for (Product prod : products) {
                assertEquals(1, prod.getProductType());
            }

        } catch (Exception e) {
            assert (false);
        }
    }

    @Test
    public void testUpdateProductSuccedes() {
        try {

            ProductFileDAO productFileDAO = this.buildDao(null);
            Product product = this.createAccessory(2);
            product.setName("Test Updateing");
            Product created = productFileDAO.createProduct(product);

            Product found = productFileDAO.getProduct(created.id);

            if (found == null) {
                assertFalse(true);
                return;
            }

            found.setName("Updated Product");

            productFileDAO.updateProduct(found);

            Product foundAgain = productFileDAO.getProduct(created.id);

            assertEquals(found.name, foundAgain.name);

        } catch (Exception e) {
            assertEquals(true, e.getMessage());
        }
    }

    @Test
    public void testUpdateProductNotFound() {
        try {

            ProductFileDAO productFileDAO = this.buildDao(null);

            Product found = this.createAccessory(-1);

            Product created = productFileDAO.updateProduct(found);

            if (created == null) {
                assertFalse(false);
                return;
            }

            assertTrue(false);

        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void testUpdateProductInvalid() {
        try {

            ProductFileDAO productFileDAO = this.buildDao(null);

            Product created = productFileDAO.createProduct(this.createAccessory(2));
            Product found = productFileDAO.getProduct(created.id);

            if (found == null) {
                assertFalse(true);
                return;
            }

            found.setName("Updated Product");
            found.setPrice(-1);

            productFileDAO.updateProduct(found);

            Product foundAgain = productFileDAO.getProduct(created.id);

            assertEquals(found.name, foundAgain.name);

            productFileDAO.deleteProduct(3);

        } catch (InvalidProductException e) {
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void testUpdateProductInvalidTypeChange() {
        try {

            ProductFileDAO productFileDAO = this.buildDao(null);

            Product old = productFileDAO.getProduct(2);

            Product changed = new BirdProduct(old.id, 2, 15, "Testing Type Change", "Hello", 5, null, null,
                    new String[0]);

            productFileDAO.updateProduct(changed);

            assert (false);

        } catch (ProductTypeChangeException e) {
            assertTrue(true);
        } catch (Exception e) {
            assertEquals(true, e.getMessage());
        }
    }

    static public ObjectMapper buildMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule("SimpleModule");
        simpleModule.addSerializer(Product.class, new ProductCombinedSerializer.ProductSerializer());
        simpleModule.addDeserializer(Product.class, new ProductCombinedSerializer.ProductDeserializer());
        mapper.registerModule(simpleModule);
        return mapper;
    }

    static public ProductFileDAO buildDAO(ObjectMapper nMapper) throws IOException {
        ObjectMapper mapper = buildMapper();
        if (nMapper == null)
            return new ProductFileDAO("data/testProducts.json", mapper);
        return new ProductFileDAO("data/testProducts.json", nMapper);

    }

    private ProductFileDAO buildDao(ObjectMapper nMapper) throws IOException {
        return buildDAO(nMapper);
    }

    /**
     * Overloaded Constructors for easy set up of tests involving a Bird Product
     */
    private BirdProduct createBird(int id, String name, String fileName, ImageSource fileSource,

            String[] sponsors) {
        return new BirdProduct(id, 1, 15, name, "Test Description", 5, fileName, fileSource, sponsors);
    }

    /**
     * Overloaded Constructors for easy set up of tests involving a Bird Product
     */
    private BirdProduct createBird(int id, String name, String[] sponsors) {
        return createBird(id, name, null, null, sponsors);
    }

    /**
     * Overloaded Constructors for easy set up of tests involving a Bird Product
     */
    private BirdProduct createBird(int id) {
        String name;
        if (id % 2 == 0) {
            name = "Big Bird";
        } else {
            name = "Little Bird";
        }
        return createBird(id, name, new String[0]);
    }

    /**
     * Overloaded Constructors for easy set up of tests involving a Accessory
     * Product
     */
    private Accessory createAccessory(int id, String name, String fileName, ImageSource fileSource) {
        return new Accessory(id, 2, 15, name, "Test Description", 5, fileName, fileSource);
    }

    /**
     * Overloaded Constructors for easy set up of tests involving a Bird Product
     */
    private Accessory createAccessory(int id, String name) {
        return createAccessory(id, name, null, null);
    }

    /**
     * Overloaded Constructors for easy set up of tests involving a Bird Product
     */
    private Accessory createAccessory(int id) {
        String name;
        if (id % 2 == 0) {
            name = "Big Braclet";
        } else {
            name = "Little Braclet";
        }
        return createAccessory(id, name);
    }

    /* Static Creators */

    /**
     * Overloaded Constructors for easy set up of tests involving a Bird Product
     */
    static public BirdProduct staticBird(int id, String name, String fileName, ImageSource fileSource,

            String[] sponsors) {
        return new BirdProduct(id, 1, 15, name, "Test Description", 5, fileName, fileSource, sponsors);
    }

    /**
     * Overloaded Constructors for easy set up of tests involving a Bird Product
     */
    static public BirdProduct staticBird(int id, String name, String[] sponsors) {
        return staticBird(id, name, null, null, sponsors);
    }

    /**
     * Overloaded Constructors for easy set up of tests involving a Bird Product
     */
    static public BirdProduct staticBird(int id) {
        String name;
        if (id % 2 == 0) {
            name = "Big Bird";
        } else {
            name = "Little Bird";
        }

        String[] sponsors = new String[1];
        sponsors[0] = "1";
        return staticBird(id, name,
                sponsors);
    }

    /**
     * Overloaded Constructors for easy set up of tests involving a Accessory
     * Product
     */
    static public Accessory staticAccessory(int id, String name, String fileName, ImageSource fileSource) {
        return new Accessory(id, 2, 15, name, "Test Description", 5, fileName, fileSource);
    }

    /**
     * Overloaded Constructors for easy set up of tests involving a Bird Product
     */
    static public Accessory staticAccessory(int id, String name) {
        return staticAccessory(id, name, null, null);
    }

    /**
     * Overloaded Constructors for easy set up of tests involving a Bird Product
     */
    static public Accessory staticAccessory(int id) {
        String name;
        if (id % 2 == 0) {
            name = "Big Braclet";
        } else {
            name = "Little Braclet";
        }
        return staticAccessory(id, name);
    }

}
