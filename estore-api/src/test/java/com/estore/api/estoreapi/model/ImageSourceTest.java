package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

// import com.estore.api.estoreapi.model.ImageSource;

public class ImageSourceTest {

  @Test
  void testFullConstruction() {
    ImageSource expected = new ImageSource("base", "old");
    ImageSource actual = new ImageSource("base", "old");

    assertEquals(expected.toString(), actual.toString());
  }

  @Test
  void testMissingBaseConstruction() {
    ImageSource expected = new ImageSource(null, "old");
    ImageSource actual = new ImageSource(null, "old");

    assertEquals(expected.toString(), actual.toString());
  }

  @Test
  void testMissingNameConstruction() {
    ImageSource expected = new ImageSource("base", null);
    ImageSource actual = new ImageSource("base", null);

    assertEquals(expected.toString(), actual.toString());
  }

  @Test
  void testGetSetName() {
    ImageSource actual = new ImageSource("base", null);
    actual.setOriginalName("Setting");

    String expected = "Setting";

    assertEquals(expected, actual.getOriginalName());
  }

  @Test
  void testGetSetBase() {
    ImageSource actual = new ImageSource(null, "init");
    actual.setBase64("Setting");

    String expected = "Setting";

    assertEquals(expected, actual.getBase64());
  }

  @Test
  void testGetImageTypeSucceds() {
    ImageSource actual = new ImageSource("Test64", "Test.jpg");
    String type = actual.getImageType();

    String expected = "jpg";

    assertEquals(expected, type);
  }

  @Test
  void testGetImageTypeFails() {
    ImageSource actual = new ImageSource("Test64", "Testjpg");
    String type = actual.getImageType();

    String expected = "Testjpg";

    assertEquals(expected, type);
  }

  @Test
  void testGetImageName() {
    ImageSource actual = new ImageSource("Test64", "Test.jpg");
    String type = actual.getImageName();

    String expected = "Test";

    assertEquals(expected, type);
  }

  @Test
  void testIsValid() throws Exception {
    ImageSource actual = new ImageSource("Test64", "Test.jpg");
    Boolean valid = actual.isValid();

    Boolean expected = true;

    assertEquals(expected, valid);
  }

  @Test
  void testIsValidMissingName() throws Exception {
    Boolean actual = new ImageSource("Test64", null).isValid();

    Boolean expected = false;
    assertEquals(expected, actual);

    actual = new ImageSource("Test64", "").isValid();

    expected = false;
    assertEquals(expected, actual);

  }

  @Test
  void testIsValidMissingBase() throws Exception {
    Boolean actual = new ImageSource(null, "TestName.jpg").isValid();

    Boolean expected = false;
    assertEquals(expected, actual);

    actual = new ImageSource("", "TestName.jpg").isValid();

    expected = false;
    assertEquals(expected, actual);

  }

}
