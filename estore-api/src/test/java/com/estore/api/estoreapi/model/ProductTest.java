package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.products.Accessory;
import com.estore.api.estoreapi.model.products.Product;

class ProductTest {

  /**
   * Test the Product Model Constuctor with no filename or source
   */
  @Test
  void constructionNoFileTest() {
    Product accessory = new Accessory(1, 0, 15.0, "Test", "Test Desc", 20, null, null);

    String actual = accessory.toString();

    String expectedProduct = String.format(Product.PRODUCT_STRING, 1, 0, "Test", 15.0, 20, "Test Desc", null, null);

    assertEquals(expectedProduct, actual);
  }

  /**
   * Test the Product Model Constuctor with no filename
   */
  @Test
  void constructionWithFileSourceTest() {
    ImageSource source = new ImageSource("TestBase64", "TestOriginalName");
    Product accessory = new Accessory(1, 0, 15.0, "Test", "Test Desc", 20, null, source);

    String actual = accessory.toString();

    String expectedSource = String.format(ImageSource.IMAGE_SOURCE_STRING, true,
        "TestOriginalName");
    String expectedProduct = String.format(Product.PRODUCT_STRING, 1, 0, "Test", 15.0, 20, "Test Desc", null,
        expectedSource);

    assertEquals(expectedProduct, actual);
  }

  /**
   * Test the Product Model Constuctor with no source
   */
  @Test
  void constructionWithFileNameTest() {
    Product accessory = new Accessory(1, 0, 15.0, "Test", "Test Desc", 20, "TestFile", null);

    String actual = accessory.toString();

    String expectedProduct = String.format(Product.PRODUCT_STRING, 1, 0, "Test", 15.0, 20, "Test Desc", "TestFile",
        null);

    assertEquals(expectedProduct, actual);
  }

  /**
   * Test Access to a products Id
   */
  @Test
  void getIdTest() {
    Product accessory = new Accessory(1, 0, 0, null, null, 0, null, null);
    Integer actual = accessory.getId();

    Integer expected = 1;

    assertEquals(expected, actual);
  }

  /**
   * Test Access to a products name
   */
  @Test
  void getNameTest() {
    Product accessory = new Accessory(1, 0, 0, "Luke Cage", null, 0, null, null);
    String actual = accessory.getName();

    String expected = "Luke Cage";

    assertEquals(expected, actual);
  }

  /**
   * Test Access to a products price
   */
  @Test
  void getPriceTest() {
    Product accessory = new Accessory(1, 0, 15.0, "Luke Cage", null, 0, null, null);
    Double actual = accessory.getPrice();

    Double expected = 15.0;

    assertEquals(expected, actual);
  }

  /**
   * Test Access to a products quantity
   */
  @Test
  void getQuantityTest() {
    Product accessory = new Accessory(1, 0, 15.0, "Luke Cage", null, 20, null, null);
    int actual = accessory.getQuantity();

    int expected = 20;

    assertEquals(expected, actual);
  }

  /**
   * Test Access to a products description
   */
  @Test
  void getDescriptionTest() {
    Product accessory = new Accessory(1, 0, 15.0, "Luke Cage", "Luke Cage is not form DC", 20, null, null);
    String actual = accessory.getDescription();

    String expected = "Luke Cage is not form DC";

    assertEquals(expected, actual);
  }

  /**
   * Test Access to a products filename
   */
  @Test
  void getFileNameTest() {
    Product accessory = new Accessory(1, 0, 15.0, "Luke Cage", "Luke Cage is not form DC", 20, "Test File Name", null);
    String actual = accessory.getFileName();

    String expected = "Test File Name";

    assertEquals(expected, actual);
  }

  /**
   * Test Access to a products file source
   */
  @Test
  void getFileSourceTest() {
    ImageSource source = new ImageSource("TestBase64", "TestOriginalName");
    Product accessory = new Accessory(1, 0, 15.0, "Luke Cage", "Luke Cage is not form DC", 20, "Test File Name",
        source);

    String actual = accessory.getFileSource().toString();

    String expected = String.format(ImageSource.IMAGE_SOURCE_STRING, true,
        "TestOriginalName");

    assertEquals(expected, actual);
  }

  /**
   * Test searchability of products by name
   */
  @Test
  void containsSearchTermNameTest() {
    Product accessory = new Accessory(1, 0, 15.0, "Test", "Test Desc", 20, "Test File Name", null);

    Boolean actual = accessory.containsSearchTerm("Test");

    Boolean expected = true;

    assertEquals(expected, actual);
  }

  /**
   * Test searchability of products by name
   */
  @Test
  void containsSearchTermDescriptionTest() {
    Product accessory = new Accessory(1, 0, 15.0, "Test", "Test Desc", 20, "Test File Name", null);
    Boolean actual = accessory.containsSearchTerm("Desc");

    Boolean expected = true;

    assertEquals(expected, actual);
  }

  /**
   * Test searchability of products
   */
  @Test
  void notContainsSearchTermTest() {
    Product accessory = new Accessory(1, 0, 15.0, "Test", "Test Desc", 20, "Test File Name", null);

    Boolean actual = accessory.containsSearchTerm("Hello");

    Boolean expected = false;

    assertEquals(expected, actual);
  }

  /**
   * Test that all feilds being filled in will return true
   */
  @Test
  void testAllFeildsRequiredForValid() {
    Product accessory = new Accessory(1, 0, 15.0, "Test", "Test Desc", 20, "Test File Name", null);

    Boolean actual = accessory.isValidProduct();

    Boolean expected = true;

    assertEquals(expected, actual);
  }

  /**
   * Test validation of missing or invalid name
   */
  @Test
  void testValidMissingName() {
    Product accessory = new Accessory(1, 0, 15.0, "Test", "Test Desc", 20, "Test File Name", null);

    accessory.setName(null);

    assertEquals(false, accessory.isValidProduct());

    accessory.setName("");
    assertEquals(false, accessory.isValidProduct());

  }

  /**
   * Test validation of missing or invalid description
   */
  @Test
  void testValidMissingDesc() {
    Product accessory = new Accessory(1, 0, 15.0, "Test", "Test Desc", 20, "Test File Name", null);

    accessory.setDescription(null);

    assertEquals(false, accessory.isValidProduct());

    accessory.setDescription("");
    assertEquals(false, accessory.isValidProduct());

  }

  /**
   * Test validation of missing or invalid quantity
   */
  @Test
  void testValidMissingQuantity() {
    Product accessory = new Accessory(1, 0, 15.0, "Test", "Test Desc", 20, "Test File Name", null);

    accessory.setQuantity(0);

    assertEquals(false, accessory.isValidProduct());
  }

  /**
   * Test validation of missing or invalid price
   */
  @Test
  void testValidMissingPrice() {
    Product accessory = new Accessory(1, 0, 15.0, "Test", "Test Desc", 20, "Test File Name", null);

    accessory.setPrice(0);

    assertEquals(false, accessory.isValidProduct());
  }

  /**
   * Test if a product has a new image to be proccessed
   */
  @Test
  void hasNewImageTest() {
    ImageSource source = new ImageSource("TestBase64", "TestOriginalName");
    Product accessory = new Accessory(1, 0, 15.0, "Test", "Test Desc", 20, "Test File Name", source);

    Boolean actual = accessory.hasNewImage();

    Boolean expected = true;

    assertEquals(expected, actual);
  }

  /**
   * Test if a product has a new image to be proccessed
   */
  @Test
  void notHasNewImageTest() {

    Product accessory = new Accessory(1, 0, 15.0, "Test", "Test Desc", 20, "Test File Name", null);

    Boolean actual = accessory.hasNewImage();

    Boolean expected = false;

    assertEquals(expected, actual);
  }

  /**
   * Test if a product has a an old image
   */
  @Test
  void hasOldImageTest() {
    Product accessory = new Accessory(1, 0, 15.0, "Test", "Test Desc", 20, "Test File Name", null);

    Boolean actual = accessory.hasOldImage();

    Boolean expected = true;

    assertEquals(expected, actual);
  }

  /**
   * Test if a product does not have a an old image
   */
  @Test
  void notHasOldImageTest() {
    Product accessory = new Accessory(1, 0, 15.0, "Test", "Test Desc", 20, null, null);

    Boolean actual = accessory.hasOldImage();

    Boolean expected = false;

    assertEquals(expected, actual);
  }

  /**
   * Test the normal function for sending product to the ui
   * test that the filename attribute is formatted to be accessed on the
   * internet
   */
  @Test
  void normalizeServerPhotoTest() {
    Product accessory = new Accessory(1, 0, 15.0, "Test", "Test Desc", 20, "TestFile", null);

    accessory.normalize();
    String actual = accessory.getFileName();

    String expected = "http://localhost:8080/static/TestFile";

    assertEquals(expected, actual);
  }

  /**
   * Test the normal function when the file name is allready formated for the ui
   */
  @Test
  void normalizeUIPhotoTest() {

    Product accessory = new Accessory(1, 0, 15.0, "Test", "Test Desc", 20, "http://localhost:8080/static/TestFile",
        null);
    accessory.normalize();
    String actual = accessory.getFileName();

    String expected = "http://localhost:8080/static/TestFile";

    assertEquals(expected, actual);
  }

  /**
   * Test the unnormal function for recieving a product from the ui
   * test that the filename attribute is formatted to be NOT accessed on the
   * internet
   */
  @Test
  void unNormalizeUIFileTest() {
    Product accessory = new Accessory(1, 0, 15.0, "Test", "Test Desc", 20, "http://localhost:8080/static/TestFile",
        null);

    accessory.unNormalize();
    String actual = accessory.getFileName();

    String expected = "TestFile";

    assertEquals(expected, actual);
  }

  /**
   * Test the unormal function when the file is allready in the format of the
   * server
   */
  @Test
  void unNormalizeServerFileTest() {
    Product accessory = new Accessory(1, 0, 15.0, "Test", "Test Desc", 20, "TestFile", null);

    accessory.unNormalize();
    String actual = accessory.getFileName();

    String expected = "TestFile";

    assertEquals(expected, actual);
  }

  /**
   * Test the unnormalized function when there is no value for a filename
   */
  @Test
  void unNormalizeMissingFileTest() {
    Product accessory = new Accessory(1, 0, 15.0, "Test", "Test Desc", 20, null, null);

    accessory.unNormalize();
    String actual = accessory.getFileName();

    String expected = null;

    assertEquals(expected, actual);
  }

  /**
   * Test Setting a products file name
   */
  @Test
  void testSettingFileName() {
    Product accessory = new Accessory(1, 0, 15.0, "Test", "Test Desc", 20, null, null);

    accessory.setFileName("TestFile");

    String expected = "TestFile";

    assertEquals(expected, accessory.getFileName());
  }

  /**
   * Test Setting a products file source
   */
  @Test
  void testSettingFileSource() {
    ImageSource source = new ImageSource("Test64", "65");
    Product accessory = new Accessory(1, 0, 15.0, "Test", "Test Desc", 20, null, null);

    accessory.setFileSource(source);

    String expected = source.toString();

    assertEquals(expected, accessory.getFileSource().toString());
  }

  @Test
  void setPriceTest() {
    Product accessory = new Accessory(1, 0, 15.0, "Luke Cage", null, 0, null, null);
    accessory.setProductType(1);
    assertEquals(1, accessory.getProductType());

  }
}