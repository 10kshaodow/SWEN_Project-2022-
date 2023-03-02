package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import java.util.List;

public interface IFileDAO {

  /**
   * Saves an image from base64 format on the server
   * Our Implementation saves to the local machines filesystem
   * 
   * @param base64 a base64 encoded string
   * 
   * @throws IOException
   * @returns If no error has been thrown it will return the newly saved files
   *          name
   * 
   */
  String saveBase64File(String base64, String newFileName, String oldType) throws IOException;

  /**
   * Remove an image from where its being stored
   * in this case the filesystem
   * 
   * @param fileName the fileName of the file to be removed
   * 
   * @throws IOException
   * 
   */
  void removeFile(String fileName) throws IOException;

  /**
   * Remove an image from where its being stored
   * in this case the filesystem
   * 
   * @param fileName the fileName of the file to be removed
   * 
   * @throws IOException
   * 
   */
  void removeFiles(List<String> fileName) throws IOException;

}
