package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.estore.api.estoreapi.exceptions.InvalidProductException;
import com.estore.api.estoreapi.exceptions.ProductNameTakenException;
import com.estore.api.estoreapi.exceptions.ProductTypeChangeException;
import com.estore.api.estoreapi.model.ImageSource;
import com.estore.api.estoreapi.model.products.Accessory;
import com.estore.api.estoreapi.model.products.Product;
import com.estore.api.estoreapi.persistence.FileDAO;
import com.estore.api.estoreapi.persistence.IFileDAO;
import com.estore.api.estoreapi.persistence.IProductDAO;
import com.estore.api.estoreapi.persistence.ProductFileDAO;

public class ProductControllerUnitTest {

  /**
   * Test that get product by id succedes with a found product
   * 
   */
  @Test
  void testGetProductById() throws Exception {
    IProductDAO productDAO = this.mockProduct();
    IFileDAO fileDAO = this.mockFile();
    ProductController productController = new ProductController(productDAO, fileDAO);
    Product product = this.createProduct(4);
    when(productDAO.getProduct(4)).thenReturn(product);

    ResponseEntity<Product> response = productController.getProduct(4);
    Product body = response.getBody();

    String expected = product.toString();

    assertNotNull(body);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(expected, body.toString());
    verify(productDAO).getProduct(4);
  }

  /**
   * Test get product id with an id that does not exist returns null
   * 
   */
  @Test
  void testGetProductByIdNotFound() throws Exception {
    IProductDAO productDAO = this.mockProduct();
    IFileDAO fileDAO = this.mockFile();
    ProductController productController = new ProductController(productDAO, fileDAO);
    when(productDAO.getProduct(4)).thenReturn(null);

    ResponseEntity<Product> response = productController.getProduct(4);
    Product body = response.getBody();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNull(body);
    verify(productDAO).getProduct(4);
  }

  /**
   * Ensure that the get product by id method fails from an internal server error
   */
  @Test
  void testGetProductByIdFailed() throws Exception {
    IProductDAO productDAO = this.mockProduct();
    IFileDAO fileDAO = this.mockFile();
    ProductController productController = new ProductController(productDAO, fileDAO);
    when(productDAO.getProduct(4)).thenAnswer(answer -> {
      throw new IOException("Test Should Fail");
    });

    ResponseEntity<Product> response = productController.getProduct(4);
    Product body = response.getBody();

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertNull(body);
    verify(productDAO).getProduct(4);
  }

  /**
   * Test searching the inventory with an empty query
   */
  @Test
  void testSearchTermEmpty() throws Exception {
    IProductDAO productDAO = this.mockProduct();
    IFileDAO fileDAO = this.mockFile();
    ProductController productController = new ProductController(productDAO, fileDAO);
    Product[] products = this.createProducts(3);
    when(productDAO.findProducts(" ")).thenReturn(products);

    ResponseEntity<Product[]> response = productController.searchProducts(" ");
    Product[] body = response.getBody();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(Arrays.toString(products), Arrays.toString(body));
    verify(productDAO).findProducts(" ");
  }

  /**
   * Test searching the inventory with a valid query
   */
  @Test
  void testSearchTerm() throws Exception {
    IProductDAO productDAO = this.mockProduct();
    IFileDAO fileDAO = this.mockFile();
    ProductController productController = new ProductController(productDAO, fileDAO);
    Product[] products = this.createProducts(1);
    when(productDAO.findProducts("John")).thenReturn(products);

    ResponseEntity<Product[]> response = productController.searchProducts("John");
    Product[] body = response.getBody();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(Arrays.toString(products), Arrays.toString(body));
    verify(productDAO).findProducts("John");
  }

  /**
   * Test searching the inventory fails
   */
  @Test
  void testSearchTermFails() throws Exception {
    IProductDAO productDAO = this.mockProduct();
    IFileDAO fileDAO = this.mockFile();
    ProductController productController = new ProductController(productDAO, fileDAO);
    // Product[] products = this.createProducts(3);
    when(productDAO.findProducts(" ")).thenAnswer(answer -> {
      throw new IOException("Test Should Fail");
    });

    ResponseEntity<Product[]> response = productController.searchProducts(" ");
    Product[] body = response.getBody();

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertNull(body);
    verify(productDAO).findProducts(" ");
  }

  /**
   * Test getting all available products
   * 
   */
  @Test
  void testGetAllProducts() throws Exception {
    IProductDAO productDAO = this.mockProduct();
    IFileDAO fileDAO = this.mockFile();
    ProductController productController = new ProductController(productDAO, fileDAO);
    Product[] products = this.createProducts(3);
    when(productDAO.getAllProducts()).thenReturn(products);

    ResponseEntity<Product[]> response = productController.getAllProducts();
    Product[] body = response.getBody();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(Arrays.toString(products), Arrays.toString(body));
    verify(productDAO).getAllProducts();

  }

  /**
   * Test that getting all products fails gracefully
   * 
   */
  @Test
  void testGetAllProductsFails() throws Exception {
    IProductDAO productDAO = this.mockProduct();
    IFileDAO fileDAO = this.mockFile();
    ProductController productController = new ProductController(productDAO, fileDAO);
    // Product[] products = this.createProducts(3);
    when(productDAO.getAllProducts()).thenAnswer(answer -> {
      throw new IOException("Test Should Fail");
    });

    ResponseEntity<Product[]> response = productController.getAllProducts();
    Product[] body = response.getBody();

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertNull(body);
    verify(productDAO).getAllProducts();

  }

  /**
   * Test Deleting a product succedes
   * 
   */
  @Test
  void testDeleteProduct() throws Exception {
    IProductDAO productDAO = this.mockProduct();
    IFileDAO fileDAO = this.mockFile();
    ProductController productController = new ProductController(productDAO, fileDAO);
    Product products = this.createProduct(0);
    when(productDAO.getProduct(0)).thenReturn(products);
    when(productDAO.deleteProduct(0)).thenReturn(true);

    ResponseEntity<Product> response = productController.deleteProduct(0);
    Product body = response.getBody();

    assertNotNull(body);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(products.toString(), body.toString());
    verify(productDAO).getProduct(0);
    verify(productDAO).deleteProduct(0);

    products.setFileName("TestFileDelete");
    when(productDAO.getProduct(0)).thenReturn(products);
    when(productDAO.deleteProduct(0)).thenReturn(true);

    response = productController.deleteProduct(0);
    body = response.getBody();

  }

  /**
   * Test deleting a product that is not found
   */
  @Test
  void testDeleteProductNotFound() throws Exception {
    IProductDAO productDAO = this.mockProduct();
    IFileDAO fileDAO = this.mockFile();
    ProductController productController = new ProductController(productDAO, fileDAO);

    when(productDAO.getProduct(0)).thenReturn(null);
    when(productDAO.deleteProduct(0)).thenReturn(false);

    ResponseEntity<Product> response = productController.deleteProduct(0);
    Product body = response.getBody();

    assertNull(body);
    assertEquals(HttpStatus.OK, response.getStatusCode());

    verify(productDAO).getProduct(0);
    verify(productDAO).deleteProduct(0);

  }

  /**
   * Test that deleting a product fails gracefully
   * 
   */
  @Test
  void testDeleteProductFails() throws Exception {
    IProductDAO productDAO = this.mockProduct();
    IFileDAO fileDAO = this.mockFile();
    ProductController productController = new ProductController(productDAO, fileDAO);

    when(productDAO.getProduct(0)).thenAnswer(answer -> {
      throw new IOException("Test is supposed to fail");
    });
    when(productDAO.deleteProduct(0)).thenReturn(false);

    ResponseEntity<Product> response = productController.deleteProduct(0);
    Product body = response.getBody();

    assertNull(body);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    verify(productDAO).getProduct(0);
    verify(productDAO, times(0)).deleteProduct(0);

  }

  /**
   * Test creating a new product with no new file
   */
  @Test
  void testCreatingProductNoNewFile() throws Exception {
    IProductDAO productDAO = this.mockProduct();
    IFileDAO fileDAO = this.mockFile();
    ProductController productController = new ProductController(productDAO, fileDAO);

    Product product = this.createProduct(0);

    when(fileDAO.saveBase64File(null, null, null)).thenReturn(null);
    when(productDAO.createProduct(product)).thenReturn(product);

    ResponseEntity<Product> response = productController.createProduct(product);
    Product body = response.getBody();

    assertNotNull(body);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(product.toString(), body.toString());
    verify(productDAO).createProduct(product);
    verify(fileDAO, times(0)).saveBase64File(null, null, null);

  }

  /**
   * Test creating a new product with a new filesource
   */
  @Test
  void testCreatingProductNewFile() throws Exception {
    IProductDAO productDAO = this.mockProduct();
    IFileDAO fileDAO = this.mockFile();
    ProductController productController = new ProductController(productDAO,
        fileDAO);

    ImageSource source = new ImageSource("Test64", "TestName.jpg");
    Product product = this.createProduct(0);
    product.setFileSource(source);

    when(fileDAO.saveBase64File(source.base64, "John-1665453670602",
        product.fileSource.getImageType())).thenReturn("TestFile");
    when(productDAO.createProduct(product)).thenAnswer(answer -> {
      product.setFileName("TestServerFile");
      product.setFileSource(null);
      return product;
    });

    ResponseEntity<Product> response = productController.createProduct(product);
    Product body = response.getBody();

    assertNotNull(body);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(product.toString(), body.toString());
    assertEquals("http://localhost:8080/static/TestServerFile",
        body.getFileName());
    verify(productDAO).createProduct(product);

  }

  /**
   * Test that creating a product with a invalid file does not upload the file
   */
  @Test
  void testCreateProductFileSourceValidationFails() throws Exception {
    IProductDAO productDAO = this.mockProduct();
    IFileDAO fileDAO = this.mockFile();
    ProductController productController = new ProductController(productDAO,
        fileDAO);

    ImageSource source = new ImageSource("Test64", "");

    Product product = this.createProduct(0);
    product.setFileSource(source);

    assertEquals(true, product.hasNewImage());
    assertEquals(false, source.isValid());
    assertEquals(false, product.fileSource.isValid());

    when(productDAO.createProduct(product)).thenReturn(null);

    ResponseEntity<Product> response = productController.createProduct(product);
    Product body = response.getBody();

    verify(productDAO, times(1)).createProduct(product);
    verify(fileDAO, times(0)).saveBase64File(null, null, null);

    assertNull(body);
    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
  }

  /**
   * Test creating a product that has a duplicate id
   * should return null
   */
  @Test
  void testCreatingProductWithConflictingId() throws Exception {
    IProductDAO productDAO = this.mockProduct();
    IFileDAO fileDAO = this.mockFile();
    ProductController productController = new ProductController(productDAO, fileDAO);

    Product product = this.createProduct(0);

    when(fileDAO.saveBase64File(null, null, null)).thenReturn(null);
    when(productDAO.createProduct(product)).thenReturn(null);

    ResponseEntity<Product> response = productController.createProduct(product);
    Product body = response.getBody();

    assertNull(body);
    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());

    verify(productDAO).createProduct(product);
    verify(fileDAO, times(0)).saveBase64File(null, null, null);

    // testing the source gets deleted if added when controller fails

    ImageSource source = new ImageSource("Test64", "TestName.jpg");
    product = this.createProduct(0);
    product.setFileSource(source);

    assertEquals(true, product.fileSource.isValid());

    when(fileDAO.saveBase64File(source.base64, product.name,
        product.fileSource.getImageType())).thenReturn("TestFile.jpg");
    when(productDAO.createProduct(product)).thenReturn(null);

    response = productController.createProduct(product);
    body = response.getBody();

    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());

    // assertEquals(true, product.fileSource);

    verify(fileDAO, times(1)).saveBase64File(source.base64, product.name,
        source.getImageType());
    verify(fileDAO, times(1)).removeFile("TestFile.jpg");
  }

  /**
   * Test that creating an invalid product throws the correct error
   * 
   */
  @Test
  void testCreatingProductThrowsInvalidProduct() throws Exception {
    IProductDAO productDAO = this.mockProduct();
    IFileDAO fileDAO = this.mockFile();
    ProductController productController = new ProductController(productDAO, fileDAO);

    Product product = this.createProduct(0);

    when(fileDAO.saveBase64File(null, null, null)).thenReturn(null);
    when(productDAO.createProduct(product)).thenAnswer(answer -> {
      throw new InvalidProductException("Test Should Fail");
    });

    ResponseEntity<Product> response = productController.createProduct(product);
    Product body = response.getBody();

    assertNull(body);
    assertEquals(HttpStatus.PRECONDITION_FAILED, response.getStatusCode());

    verify(productDAO).createProduct(product);
    verify(fileDAO, times(0)).saveBase64File(null, null, null);

    // testing that the file is deleted

    ImageSource source = new ImageSource("Test64", "TestName.jpg");
    product = this.createProduct(0);
    product.setFileSource(source);

    assertEquals(true, product.fileSource.isValid());

    when(fileDAO.saveBase64File(source.base64, product.name,
        product.fileSource.getImageType())).thenReturn("TestFile.jpg");
    when(productDAO.createProduct(product)).thenAnswer(answer -> {
      throw new InvalidProductException("Test Should Fail");
    });

    response = productController.createProduct(product);
    body = response.getBody();
    assertEquals(HttpStatus.PRECONDITION_FAILED, response.getStatusCode());

    verify(fileDAO, times(1)).saveBase64File(source.base64, product.name,
        source.getImageType());
    verify(fileDAO, times(1)).removeFile("TestFile.jpg");

  }

  /**
   * Test that creating a product whose name is allready taken throws the correct
   * error
   * 
   */
  @Test
  void testCreatingProductThrowsNameTaken() throws Exception {
    IProductDAO productDAO = this.mockProduct();
    IFileDAO fileDAO = this.mockFile();
    ProductController productController = new ProductController(productDAO, fileDAO);

    Product product = this.createProduct(0);

    when(fileDAO.saveBase64File(null, null, null)).thenReturn(null);
    when(productDAO.createProduct(product)).thenAnswer(answer -> {
      throw new ProductNameTakenException("Test Should Fail");
    });

    ResponseEntity<Product> response = productController.createProduct(product);
    Product body = response.getBody();

    assertNull(body);
    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());

    verify(productDAO).createProduct(product);
    verify(fileDAO, times(0)).saveBase64File(null, null, null);

    // test that the file is deleted

    ImageSource source = new ImageSource("Test64", "TestName.jpg");
    product = this.createProduct(0);
    product.setFileSource(source);

    assertEquals(true, product.fileSource.isValid());

    when(fileDAO.saveBase64File(source.base64, product.name,
        product.fileSource.getImageType())).thenReturn("TestFile.jpg");
    when(productDAO.createProduct(product)).thenAnswer(answer -> {
      throw new ProductNameTakenException("Test Should Fail");
    });

    response = productController.createProduct(product);
    body = response.getBody();
    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());

    verify(fileDAO, times(1)).saveBase64File(source.base64, product.name,
        source.getImageType());
    verify(fileDAO, times(1)).removeFile("TestFile.jpg");

  }

  /**
   * Test that creating a new product fails gracefully
   * 
   */
  @Test
  void testCreatingProductThrowsException() throws Exception {
    IProductDAO productDAO = this.mockProduct();
    IFileDAO fileDAO = this.mockFile();
    ProductController productController = new ProductController(productDAO, fileDAO);

    Product product = this.createProduct(0);

    when(fileDAO.saveBase64File(null, null, null)).thenReturn(null);
    when(productDAO.createProduct(product)).thenAnswer(answer -> {
      throw new IOException("Test Should Fail");
    });

    ResponseEntity<Product> response = productController.createProduct(product);
    Product body = response.getBody();

    assertNull(body);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    verify(productDAO).createProduct(product);
    verify(fileDAO, times(0)).saveBase64File(null, null, null);

    // test deleting file

    ImageSource source = new ImageSource("Test64", "TestName.jpg");
    product = this.createProduct(0);
    product.setFileSource(source);

    assertEquals(true, product.fileSource.isValid());

    when(fileDAO.saveBase64File(source.base64, product.name,
        product.fileSource.getImageType())).thenReturn("TestFile.jpg");
    when(productDAO.createProduct(product)).thenAnswer(answer -> {
      throw new IOException("Test Should Fail");
    });

    response = productController.createProduct(product);
    body = response.getBody();
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    verify(fileDAO, times(1)).saveBase64File(source.base64, product.name,
        source.getImageType());
    verify(fileDAO, times(1)).removeFile("TestFile.jpg");

  }

  /**
   * Test that updating an existing product succeds
   */
  @Test
  void testUpdatingProductSuccedes() throws Exception {
    IProductDAO productDAO = this.mockProduct();
    IFileDAO fileDAO = this.mockFile();
    ProductController productController = new ProductController(productDAO, fileDAO);

    Product product = this.createProduct(0);

    when(productDAO.getProduct(0)).thenReturn(product);
    when(fileDAO.saveBase64File(null, null, null)).thenReturn(null);
    when(productDAO.updateProduct(product)).thenReturn(product);

    ResponseEntity<Product> response = productController.updateProduct(product);
    Product body = response.getBody();

    verify(productDAO).getProduct(0);
    verify(productDAO).updateProduct(product);
    verify(fileDAO, times(0)).saveBase64File(null, null, null);

    product.normalize();

    assertNotNull(body);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(product.toString(), body.toString());

  }

  /**
   * Test that a prudct updates successfully with a new file and no old file
   */
  @Test
  void testUpdatingProductSuccedesWithNewFile() throws Exception {
    IProductDAO productDAO = this.mockProduct();
    IFileDAO fileDAO = this.mockFile();
    ProductController productController = new ProductController(productDAO, fileDAO);

    ImageSource source = new ImageSource("Test64", "Test.jpg");
    Product product = this.createProduct(0);
    product.setFileSource(source);

    when(productDAO.getProduct(0)).thenReturn(product);
    when(fileDAO.saveBase64File(source.base64, "JohnName", ".jpg")).thenReturn("TestFile.jpg");
    when(productDAO.updateProduct(any(Product.class))).thenAnswer(answer -> {
      product.setFileName("TestFile.jpg");
      product.setFileSource(null);
      return product;
    });

    ResponseEntity<Product> response = productController.updateProduct(product);
    Product body = response.getBody();

    verify(productDAO).getProduct(0);
    verify(productDAO).updateProduct(product);
    // verify(fileDAO, times(1)).saveBase64File(source.base64, anyString(), "jpg");

    product.normalize();

    assertNotNull(body);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(product.toString(), body.toString());

  }

  /**
   * Test that a product successfully updates with a new and old file
   */
  @Test
  void testUpdatingProductSuccedesWithNewFileAndOld() throws Exception {
    IProductDAO productDAO = this.mockProduct();
    IFileDAO fileDAO = this.mockFile();
    ProductController productController = new ProductController(productDAO, fileDAO);

    ImageSource source = new ImageSource("Test64", "Test.jpg");
    Product product = this.createProduct(0);
    product.setFileSource(source);
    product.setFileName("TestDelete");

    when(productDAO.getProduct(0)).thenReturn(product);
    when(fileDAO.saveBase64File(source.base64, "JohnName", ".jpg")).thenReturn("TestFile.jpg");
    when(productDAO.updateProduct(any(Product.class))).thenAnswer(answer -> {
      product.setFileName("TestFile.jpg");
      product.setFileSource(null);
      return product;
    });

    ResponseEntity<Product> response = productController.updateProduct(product);
    Product body = response.getBody();

    verify(productDAO).getProduct(0);
    verify(productDAO).updateProduct(product);
    verify(fileDAO, times(1)).removeFile("TestDelete");
    // verify(fileDAO, times(1)).saveBase64File(source.base64, anyString(), "jpg");

    product.normalize();

    assertNotNull(body);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(product.toString(), body.toString());

  }

  /**
   * Test that the correct status code is returned if the product could not be
   * updated
   */
  @Test
  void testUpdatingProductReturnsNull() throws Exception {
    IProductDAO productDAO = this.mockProduct();
    IFileDAO fileDAO = this.mockFile();
    ProductController productController = new ProductController(productDAO, fileDAO);

    Product product = this.createProduct(0);

    when(productDAO.getProduct(0)).thenReturn(product);
    when(fileDAO.saveBase64File(null, null, null)).thenReturn(null);
    when(productDAO.updateProduct(product)).thenReturn(null);

    ResponseEntity<Product> response = productController.updateProduct(product);
    Product body = response.getBody();

    verify(productDAO).getProduct(0);
    verify(productDAO).updateProduct(product);
    verify(fileDAO, times(0)).saveBase64File(null, null, null);

    assertNull(body);
    assertEquals(HttpStatus.OK, response.getStatusCode());

    // test that file gets deleted

    ImageSource source = new ImageSource("Test64", "TestName.jpg");
    product = this.createProduct(0);
    product.setFileSource(source);

    assertEquals(true, product.fileSource.isValid());

    when(productDAO.getProduct(0)).thenReturn(product);
    when(fileDAO.saveBase64File(source.base64, product.name,
        product.fileSource.getImageType())).thenReturn("TestFile.jpg");
    when(productDAO.updateProduct(product)).thenReturn(null);

    response = productController.updateProduct(product);
    body = response.getBody();
    assertEquals(HttpStatus.OK, response.getStatusCode());

    verify(fileDAO, times(1)).saveBase64File(source.base64, product.name,
        source.getImageType());
    verify(fileDAO, times(1)).removeFile("TestFile.jpg");

  }

  /**
   * Test that updating a product fails gracefully
   */
  @Test
  void testUpdatingProductFails() throws Exception {
    IProductDAO productDAO = this.mockProduct();
    IFileDAO fileDAO = this.mockFile();
    ProductController productController = new ProductController(productDAO, fileDAO);

    Product product = this.createProduct(0);

    when(productDAO.getProduct(0)).thenAnswer(answer -> {
      throw new IOException("Test Should Fail");
    });
    when(fileDAO.saveBase64File(null, null, null)).thenReturn(null);
    when(productDAO.updateProduct(product)).thenReturn(null);

    ResponseEntity<Product> response = productController.updateProduct(product);
    Product body = response.getBody();

    verify(productDAO).getProduct(0);
    verify(productDAO, times(0)).updateProduct(product);
    verify(fileDAO, times(0)).saveBase64File(null, null, null);

    assertNull(body);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    // test file gets deleted

  }

  @Test
  public void testUpdateProductThrowIoException() throws Exception {
    IProductDAO productDAO = this.mockProduct();
    IFileDAO fileDAO = this.mockFile();
    ProductController productController = new ProductController(productDAO, fileDAO);

    ImageSource source = new ImageSource("Test64", "TestName.jpg");
    Product product = this.createProduct(0);
    product.setFileSource(source);

    assertEquals(true, product.fileSource.isValid());

    when(productDAO.getProduct(0)).thenReturn(product);
    when(fileDAO.saveBase64File(source.base64, product.name,
        product.fileSource.getImageType())).thenReturn("TestFile.jpg");
    when(productDAO.updateProduct(product)).thenAnswer(answer -> {
      throw new IOException("Test Should Fail");
    });

    ResponseEntity<Product> response = productController.updateProduct(product);
    Product body = response.getBody();
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    verify(fileDAO, times(1)).saveBase64File(source.base64, product.name,
        source.getImageType());
    verify(fileDAO, times(1)).removeFile("TestFile.jpg");

  }

  @Test
  public void testUpdateProductThrowsInvalidProduct() throws Exception {
    IProductDAO productDAO = this.mockProduct();
    IFileDAO fileDAO = this.mockFile();
    ProductController productController = new ProductController(productDAO, fileDAO);

    Product product = this.createProduct(0);

    when(productDAO.getProduct(0)).thenReturn(product);
    when(fileDAO.saveBase64File(null, null, null)).thenReturn(null);
    when(productDAO.updateProduct(product)).thenAnswer(answer -> {
      throw new InvalidProductException("Test Should Fail");
    });

    ResponseEntity<Product> response = productController.updateProduct(product);
    Product body = response.getBody();

    verify(productDAO).getProduct(0);
    verify(productDAO, times(1)).updateProduct(product);
    verify(fileDAO, times(0)).saveBase64File(null, null, null);

    assertNull(body);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    // test file deleted

    ImageSource source = new ImageSource("Test64", "TestName.jpg");
    product = this.createProduct(0);
    product.setFileSource(source);

    assertEquals(true, product.fileSource.isValid());

    when(productDAO.getProduct(0)).thenReturn(product);
    when(fileDAO.saveBase64File(source.base64, product.name,
        product.fileSource.getImageType())).thenReturn("TestFile.jpg");
    when(productDAO.updateProduct(product)).thenAnswer(answer -> {
      throw new InvalidProductException("Test Should Fail");
    });

    response = productController.updateProduct(product);
    body = response.getBody();
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    verify(fileDAO, times(1)).saveBase64File(source.base64, product.name,
        source.getImageType());
    verify(fileDAO, times(1)).removeFile("TestFile.jpg");

  }

  @Test
  public void testUpdateProductThrowsProductTypeChangeException() throws Exception {
    IProductDAO productDAO = this.mockProduct();
    IFileDAO fileDAO = this.mockFile();
    ProductController productController = new ProductController(productDAO, fileDAO);

    Product product = this.createProduct(0);

    when(productDAO.getProduct(0)).thenReturn(product);
    when(fileDAO.saveBase64File(null, null, null)).thenReturn(null);
    when(productDAO.updateProduct(product)).thenAnswer(answer -> {
      throw new ProductTypeChangeException("Test Should Fail");
    });

    ResponseEntity<Product> response = productController.updateProduct(product);
    Product body = response.getBody();

    verify(productDAO).getProduct(0);
    verify(productDAO, times(1)).updateProduct(product);
    verify(fileDAO, times(0)).saveBase64File(null, null, null);

    assertNull(body);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    // test file deleted

    ImageSource source = new ImageSource("Test64", "TestName.jpg");
    product = this.createProduct(0);
    product.setFileSource(source);

    assertEquals(true, product.fileSource.isValid());

    when(productDAO.getProduct(0)).thenReturn(product);
    when(fileDAO.saveBase64File(source.base64, product.name,
        product.fileSource.getImageType())).thenReturn("TestFile.jpg");
    when(productDAO.updateProduct(product)).thenAnswer(answer -> {
      throw new ProductTypeChangeException("Test Should Fail");
    });

    response = productController.updateProduct(product);
    body = response.getBody();
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    verify(fileDAO, times(1)).saveBase64File(source.base64, product.name,
        source.getImageType());
    verify(fileDAO, times(1)).removeFile("TestFile.jpg");

  }

  /**
   * Test that an invalid file source will not be uploaded when product is being
   * updated
   */
  @Test
  void testUpdatingProductFileSourceValidationFails() throws Exception {
    IProductDAO productDAO = this.mockProduct();
    IFileDAO fileDAO = this.mockFile();
    ProductController productController = new ProductController(productDAO,
        fileDAO);

    ImageSource source = new ImageSource("Test64", "");

    Product product = this.createProduct(0);
    product.setFileSource(source);

    assertEquals(true, product.hasNewImage());
    assertEquals(false, source.isValid());
    assertEquals(false, product.fileSource.isValid());

    when(productDAO.updateProduct(product)).thenReturn(null);

    ResponseEntity<Product> response = productController.updateProduct(product);
    Product body = response.getBody();

    verify(productDAO, times(1)).updateProduct(product);
    verify(fileDAO, times(0)).saveBase64File(null, null, null);

    assertNull(body);
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void testGetProductsByType() {
    try {
      IProductDAO productDAO = this.mockProduct();
      IFileDAO fileDAO = this.mockFile();
      ProductController productController = new ProductController(productDAO,
          fileDAO);

      Product[] expected;
      List<Product> list = new ArrayList<>();
      list.add(this.createProduct(1));
      list.add(this.createProduct(2));
      expected = new Product[list.size()];
      expected = list.toArray(expected);

      when(productDAO.getProductsOfType(2)).thenReturn(expected);

      ResponseEntity<Product[]> response = productController.getProductsOfType(2);
      Product[] actual = response.getBody();
      assertEquals(HttpStatus.OK, response.getStatusCode());
      assertNotNull(actual);

      for (Product prod : actual) {
        assertEquals(2, prod.getProductType());
      }

    } catch (Exception e) {
      assert (false);
    }
  }

  @Test
  public void testGetProductsByTypeFails() {
    try {
      IProductDAO productDAO = this.mockProduct();
      IFileDAO fileDAO = this.mockFile();
      ProductController productController = new ProductController(productDAO,
          fileDAO);

      when(productDAO.getProductsOfType(2)).thenAnswer(answer -> {
        throw new IOException("Test should fail");
      });

      ResponseEntity<Product[]> response = productController.getProductsOfType(2);
      assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    } catch (Exception e) {
      assert (false);
    }
  }

  private IProductDAO mockProduct() {
    return Mockito.mock(ProductFileDAO.class);
  }

  private IFileDAO mockFile() {
    return Mockito.mock(FileDAO.class);
  }

  private Product[] createProducts(int amount) {
    Product[] array = new Product[amount];

    for (int i = 0; i < amount; i++) {
      Product product = this.createProduct(i);
      array[i] = product;
    }

    return array;

  }

  private ImageSource createSource() {
    return new ImageSource("Test", "Test.jpg");
  }

  private Product createProduct(int id) {
    String name;

    if (id % 2 == 0) {
      name = "John";
    } else {
      name = "Jane";
    }

    Product product = new Accessory(id, 2, 20, name, "Test Product", 20, null, null);

    return product;
  }

}
