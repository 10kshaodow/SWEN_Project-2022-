package com.estore.api.estoreapi.model.products;

import java.util.ArrayList;
import java.util.Arrays;

import com.estore.api.estoreapi.model.ImageSource;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BirdProduct extends Product {

  public static final String PRODUCT_STRING = "\nProduct {id=%d, productType=%d, name=%s, price=%f, quantity=%d, description=%s, fileName=%s, fileSource=%s, sponsors=%s}\n";

  @JsonProperty("sponsors")
  public String[] sponsors;

  public BirdProduct(@JsonProperty("id") int id,
      @JsonProperty("productType") int productType,
      @JsonProperty("price") double price,
      @JsonProperty("name") String name,
      @JsonProperty("description") String description,
      @JsonProperty("quantity") int quantity,
      @JsonProperty("fileName") String fileName,
      @JsonProperty("fileSource") ImageSource fileSource,
      @JsonProperty("sponsors") String[] sponsors) {
    super(id, productType, price, name, description, quantity, fileName, fileSource);
    this.sponsors = sponsors;

    // TODO Auto-generated constructor stub
  }

  /**
   * Retrieves the Sponsors object of the product
   * 
   * @return The Sponsors object of the product
   */
  public String[] getSponsors() {
    return this.sponsors;
  }

  public void setSponsors(String[] sponsors) {
    this.sponsors = sponsors;
  }

  /**
   * Returns True if successfully added
   */
  public Boolean addSponsor(String sponsor) {

    if (this.sponsors == null) {
      return false;
    }

    ArrayList<String> newSponsors = new ArrayList<>();

    for (int i = 0; i < this.sponsors.length; i++) {
      String checkingId = this.sponsors[i];

      if (checkingId.equals(sponsor)) {
        return false;
      }

      newSponsors.add(checkingId);
    }

    newSponsors.add(sponsor);

    String[] stringArray = new String[newSponsors.size()];
    this.sponsors = newSponsors.toArray(stringArray);

    return true;
  }

  /**
   * Returns True if successfully removed
   */
  public Boolean removeSponsor(String sponsor) {

    if (this.sponsors == null) {
      return false;
    }

    int index = -1;
    ArrayList<String> sponsorList = new ArrayList<String>();

    for (int i = 0; i < this.sponsors.length; i++) {
      String checkingId = this.sponsors[i];
      if (checkingId.equals(sponsor)) {
        index = i;
      }
      sponsorList.add(checkingId);
    }

    if (index == -1) {
      return false;
    }

    sponsorList.remove(index);

    String[] stringArray = new String[sponsorList.size()];
    this.sponsors = sponsorList.toArray(stringArray);
    return true;
  }

  @Override
  public String toString() {
    return String.format(PRODUCT_STRING, this.id, this.productType, this.name, this.price, this.quantity,
        this.description,
        this.fileName, this.fileSource, Arrays.toString(this.sponsors));
  }

}
