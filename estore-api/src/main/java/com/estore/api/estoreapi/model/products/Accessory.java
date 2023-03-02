package com.estore.api.estoreapi.model.products;

import com.estore.api.estoreapi.model.ImageSource;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Accessory extends Product {

  public Accessory(@JsonProperty("id") int id,
      @JsonProperty("productType") int productType,
      @JsonProperty("price") double price,
      @JsonProperty("name") String name,
      @JsonProperty("description") String description,
      @JsonProperty("quantity") int quantity,
      @JsonProperty("fileName") String fileName,
      @JsonProperty("fileSource") ImageSource fileSource) {
    super(id, productType, price, name, description, quantity, fileName, fileSource);
    // TODO Auto-generated constructor stub
  }

}
