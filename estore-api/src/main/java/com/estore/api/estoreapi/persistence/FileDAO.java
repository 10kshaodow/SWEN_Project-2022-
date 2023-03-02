package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

@Component
public class FileDAO implements IFileDAO {

  private static final Logger LOG = Logger.getLogger(FileDAO.class.getName());

  // ServletContext context;
  private String absoluteUploadPath;

  /**
   * Creates a File Data Access Object
   * 
   */
  public FileDAO() {
    // Specific to allow for any operating system it runs on
    this.absoluteUploadPath = new FileSystemResource("").getFile().getAbsolutePath()
        + "/src/main/resources/uploads".replace("/", File.separator);
  }

  /**
   * Saves the new file with the same type onto the filesystem
   * 
   */
  @Override
  public String saveBase64File(String base64, String newFileName, String oldType) throws IOException {

    if (this.isBase64(base64) == false)
      throw new IOException("Incoming Image is not Base64");

    byte[] imageBytes = Base64.decodeBase64(base64);
    String fullName = newFileName + "." + oldType;
    String fullPath = this.absoluteUploadPath + File.separator + fullName;

    FileOutputStream fileWriter = new FileOutputStream(new File(fullPath));

    fileWriter.write(imageBytes);
    fileWriter.close();

    return fullName;

  }

  public Boolean isBase64(String base64) {
    // String pattern =
    // "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";
    // Pattern r = Pattern.compile(pattern);
    // Matcher m = r.matcher(base64);
    // LOG.info("Evaluating Base 64\n");
    // LOG.info("Result: " + m.find());
    // LOG.info("\n");
    // return m.find();

    LOG.info("Evaluating Base 64\n");
    LOG.info("Result: " + Base64.isBase64(base64));
    LOG.info("\n");

    if (base64.length() < 50)
      return false;

    return Base64.isBase64(base64);
  }

  /**
   * Remove the file from the filesystem by the file name
   * 
   * @throws IOException if file could not be deleted
   */
  @Override
  public void removeFile(String fileName) throws IOException {
    String filePath = this.getFilePath(fileName);
    this.removeFileByPath(filePath);
  }

  /**
   * Remove the file from the filesystem by the file name
   * 
   * @throws IOException if file could not be deleted
   */
  @Override
  public void removeFiles(List<String> fileNameList) throws IOException {
    for (int i = 0; i < fileNameList.size(); i++) {
      String fileName = fileNameList.get(i);
      String filePath = this.getFilePath(fileName);
      this.removeFileByPath(filePath);
    }
  }

  /**
   * Construct the full path of the given fileName
   * 
   * @param fileName string of the fileName
   * 
   */
  public String getFilePath(String fileName) {
    return this.absoluteUploadPath + File.separator + fileName;
  }

  /**
   * Remove the file from the filesystem by the absolute path of the file
   * 
   * @throws IOException if file could not be deleted
   */
  public void removeFileByPath(String filePath) throws IOException {
    File file = new File(filePath);
    if (file.delete()) {
      LOG.info(filePath + " Deleted");
    } else {
      throw new IOException("File: " + filePath + " could not be deleted");
    }
  }

}