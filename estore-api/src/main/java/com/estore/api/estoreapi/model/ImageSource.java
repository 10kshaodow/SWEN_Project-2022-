package com.estore.api.estoreapi.model;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a New Image Source
 * 
 * @author SWEN 05 Team 4D - Big Development
 * 
 */
public class ImageSource {

  // private static final Logger LOG =
  // Logger.getLogger(ImageSource.class.getName());
  public static final String IMAGE_SOURCE_STRING = "{base64=%b, originalName=%s}";

  @JsonProperty("base64")
  public String base64;
  @JsonProperty("orginalName")
  public String originalName;

  /**
   * Create An Image Source
   * 
   * @param base64       String containing the base64 image
   * @param originalName String containing the originalName of the image
   */
  public ImageSource(@JsonProperty("base64") String base64, @JsonProperty("orginalName") String originalName) {
    this.base64 = base64;
    this.originalName = originalName;
  }

  /**
   * Set the base64 property
   * 
   * @param base64 The base64 string of the Image Source
   */
  public void setBase64(String base64) {
    this.base64 = base64;
  }

  /**
   * Set the originalName property
   * 
   * @param originalName The originalName string of the Image Source
   */
  public void setOriginalName(String originalName) {
    this.originalName = originalName;
  }

  /**
   * Retrieves the base64 string of the Image Source
   * 
   * @return The base64 string of the Image Source
   */
  public String getBase64() {
    return this.base64;
  }

  /**
   * Retrieves the originalName of the Image Source
   * 
   * @return The originalName of the Image Source
   */
  public String getOriginalName() {
    return this.originalName;
  }

  /**
   * Retrieves the image type of the Image Source
   * 
   * @return The image type of the Image Source
   */
  @JsonIgnore
  public String getImageType() {
    String[] split = this.originalName.split("\\.(?=[^\\.]+$)");
    if (split.length > 1)
      return split[split.length - 1];
    return split[0];
  }

  /**
   * Retrieves the ImageName of the Image Source
   * 
   * @return The ImageName of the Image Source
   */
  @JsonIgnore
  public String getImageName() {
    String[] split = this.originalName.split("\\.(?=[^\\.]+$)");
    return split[0].replace(" ", "_");
  }

  /**
   * 
   * @return True if all fields are present
   */
  @JsonIgnore
  public Boolean isValid() throws IOException {
    if (this.base64 == null || this.base64.length() <= 0)
      return false;
    if (this.originalName == null || this.originalName.length() <= 0)
      return false;

    return true;
  }

  @Override
  public String toString() {
    return String.format(IMAGE_SOURCE_STRING, this.base64 != null, this.originalName);
  }
}
