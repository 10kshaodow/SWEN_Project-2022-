package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.estore.api.estoreapi.model.products.Accessory;
import com.estore.api.estoreapi.model.products.Product;
import com.estore.api.estoreapi.persistence.IFileDAO;
import com.estore.api.estoreapi.persistence.IProductDAO;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
public class ProductControllerIntegrationTest {

  // private static final Logger LOG =
  // Logger.getLogger(ProductControllerIntegrationTest.class.getName());

  // @Autowired
  // private ProductController productController;

  @Autowired
  private MockMvc mvc;

  @MockBean
  private IProductDAO productDao;

  @MockBean
  private IFileDAO fileDAO;

  @Test
  void testCreateProduct() {

  }

  @Test
  void testDeleteProduct() {

  }

  @Test
  void testGetAllProducts() throws Exception {

  }

  @Test
  void testGetProduct() throws Exception {

  }

  @Test
  void testSearchProductsWithNoTerm() throws Exception {
    // Mock the data that we want returned
    Product[] products = this.createProducts(2);
    when(this.productDao.findProducts(" ")).thenReturn(products);

    // call the spring server
    MvcResult result = mvc.perform(get("/products/?searchTerm= "))
        .andDo(print())
        .andExpect(status().isOk()).andReturn();
    // get the content as a string
    String actualContent = result.getResponse().getContentAsString();

    // expected value
    String expected = "[{\"id\":0,\"productType\":2,\"price\":20.0,\"quantity\":20,\"name\":\"John\",\"description\":\"Test Product\",\"fileName\":null,\"fileSource\":null},{\"id\":1,\"productType\":2,\"price\":20.0,\"quantity\":20,\"name\":\"Jane\",\"description\":\"Test Product\",\"fileName\":null,\"fileSource\":null}]";

    // verify that the seam was properly mocked
    verify(productDao).findProducts(" ");

    // assert we got the expected value back
    assertEquals(expected, actualContent);
  }

  @Test
  void testSearchProductsWithTerm() throws Exception {
    // Setup
    Product[] products = this.createProducts(1);
    when(this.productDao.findProducts("John")).thenReturn(products);

    // Call
    MvcResult result = mvc.perform(get("/products/?searchTerm=John"))
        .andDo(print())
        .andExpect(status().isOk()).andReturn();
    String actualContent = result.getResponse().getContentAsString();

    String expected = "[{\"id\":0,\"productType\":2,\"price\":20.0,\"quantity\":20,\"name\":\"John\",\"description\":\"Test Product\",\"fileName\":null,\"fileSource\":null}]";

    // Assert
    verify(productDao).findProducts("John");
    assertEquals(expected, actualContent);
  }

  @Test
  void testUpdateProduct() {

  }

  private Product[] createProducts(int amount) {
    Product[] array = new Product[amount];

    for (int i = 0; i < amount; i++) {
      Product product = this.createProduct(i);
      array[i] = product;
    }

    return array;

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
