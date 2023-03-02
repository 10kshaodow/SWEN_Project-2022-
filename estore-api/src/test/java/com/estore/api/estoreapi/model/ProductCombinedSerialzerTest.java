package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.products.BirdProduct;
import com.estore.api.estoreapi.model.products.Product;
import com.estore.api.estoreapi.model.products.ProductCombinedSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class ProductCombinedSerialzerTest {

  @Test
  public void testSerialization() throws JsonMappingException,
      JsonProcessingException {
    String json = "{\"id\" : 2,\"productType\" : 1,\"price\" : 15.0,\"quantity\"" +
        ": 5,\"name\" : \"Big Bird\",\"description\" : \"Test" +
        " Description\",\"fileName\" : null,\"fileSource\" : {\"base64\": \"test64\"," +
        "\"orginalName\": \"testOrigin\"},\"sponsors\"" +
        ": [ \"1\", \"4\", \"2\"]}";
    ObjectMapper mapper = buildMapper();

    String[] sponsors = new String[] { "1", "4", "2" };
    ImageSource source = new ImageSource("test64", "testOrigin");
    BirdProduct expected = new BirdProduct(
        2,
        1,
        15,
        "Big Bird",
        "Test Description",
        5,
        null,
        source,
        sponsors);

    Product value = mapper.readValue(json, Product.class);

    assertEquals(expected.toString(), value.toString());
    assertEquals(expected.getClass(), value.getClass());

  }

  @Test
  public void testDeserilization() throws JsonProcessingException {
    String expected = "{\"id\":2,\"productType\":1,\"price\":15.0,\"quantity\"" +
        ":5,\"name\":\"Big Bird\",\"description\":\"Test" +
        " Description\",\"fileName\":null,\"fileSource\":{\"base64\":\"test64\"," +
        "\"orginalName\":\"testOrigin\"},\"sponsors\"" +
        ":[\"1\",\"4\",\"2\"]}";
    ObjectMapper mapper = buildMapper();

    String[] sponsors = new String[] { "1", "4", "2" };
    ImageSource source = new ImageSource("test64", "testOrigin");
    Product product = new BirdProduct(2, 1, 15, "Big Bird", "Test Description",
        5, null, source,
        sponsors);

    String value = mapper.writeValueAsString(product);

    assertEquals(expected, value);

  }

  static public ObjectMapper buildMapper() {
    ObjectMapper mapper = new ObjectMapper();
    SimpleModule simpleModule = new SimpleModule("SimpleModule");
    simpleModule.addSerializer(Product.class, new ProductCombinedSerializer.ProductSerializer());
    simpleModule.addDeserializer(Product.class, new ProductCombinedSerializer.ProductDeserializer());
    mapper.registerModule(simpleModule);
    return mapper;
  }

}
