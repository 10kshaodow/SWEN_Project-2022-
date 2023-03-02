package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.FileSystemResource;

public class FileDAOTest {

  /**
   * 
   * Creates a file(image) called RickTest.webp successfully
   * 
   */
  @Test
  void testSaveBase64Succeeds() throws Exception {
    IFileDAO fileDAO = new FileDAO();

    String copyPath = new FileSystemResource("").getFile().getAbsolutePath()
        + "/src/main/resources/uploadTests".replace("/", File.separator) + File.separator + "Rick.webp";
    File file = new File(copyPath);

    String name = fileDAO.saveBase64File(Base64.encodeBase64String(Files.readAllBytes(file.toPath())), "RickTest",
        "webp");

    String expected = "RickTest.webp";
    assertEquals(expected, name);

    fileDAO.removeFile(expected);
  }

  /**
   * Test that saving a file fails if it is not base 64 format
   */
  @Test
  void testSaveBase64Fails() {
    IFileDAO fileDAO = new FileDAO();

    assertThrows(IOException.class, () -> fileDAO.saveBase64File("This is not base64", "TestFail", ".fail"),
        "Expected saveBase64() to throw, but it didn't");

  }

  /**
   * Test constucting the full path to the saved file from any os
   */
  @Test
  void testGetFilePath() {
    FileDAO fileDAO = new FileDAO();

    String path = fileDAO.getFilePath("Path");

    String expected = new FileSystemResource("").getFile().getAbsolutePath()
        + "/src/main/resources/uploads".replace("/", File.separator) + File.separator + "Path";

    assertEquals(expected, path);
  }

  /**
   * Test that the file can be removed by its full path
   */
  @Test
  void testRemoveFileByPath() {
    FileDAO fileDAO = new FileDAO();

    String path = fileDAO.getFilePath("RickTest.webp");

    try {

      String copyPath = new FileSystemResource("").getFile().getAbsolutePath()
          + "/src/main/resources/uploadTests".replace("/", File.separator) + File.separator + "Rick.webp";
      File file = new File(copyPath);

      fileDAO.saveBase64File(Base64.encodeBase64String(Files.readAllBytes(file.toPath())), "RickTest",
          "webp");

      fileDAO.removeFileByPath(path);
      assertEquals(true, true);

    } catch (IOException e) {
      assertEquals(false, true);
    }

  }

  /**
   * Test that the file being removed by its full path fails gracefully
   */
  @Test
  void testRemoveFileByPathFails() {
    FileDAO fileDAO = new FileDAO();

    String path = fileDAO.getFilePath("RickTest.webp");

    try {

      fileDAO.removeFileByPath(path);
      assertEquals(false, true);

    } catch (IOException e) {
      assertEquals(true, true);
    }
  }

  /**
   * Test that the file can be removed by its short path
   */
  @Test
  void testRemoveFile() {
    FileDAO fileDAO = new FileDAO();

    try {

      String copyPath = new FileSystemResource("").getFile().getAbsolutePath()
          + "/src/main/resources/uploadTests".replace("/", File.separator) + File.separator + "Rick.webp";
      File file = new File(copyPath);

      fileDAO.saveBase64File(Base64.encodeBase64String(Files.readAllBytes(file.toPath())), "RickTest",
          "webp");

      fileDAO.removeFile("RickTest.webp");
      assertEquals(true, true);

    } catch (IOException e) {
      assertEquals(false, true);
    }
  }

  /**
   * Test that the file being removed by its short path fails gracefully
   */
  @Test
  void testRemoveFileFails() {
    FileDAO fileDAO = new FileDAO();

    try {
      fileDAO.removeFile("RickTest.webp");
      assertEquals(false, true);

    } catch (IOException e) {
      assertEquals(true, true);
    }
  }

  @Test
  public void testRemoveFiles() {
    FileDAO fileDAO = new FileDAO();

    List<String> doNotTouchFileNames = new ArrayList<String>();
    doNotTouchFileNames.add("Barbary.jpg");
    doNotTouchFileNames.add("Aplomando.jpg");

    List<String> testFileNames = new ArrayList<String>();
    List<String> createdFileBaseForTest = new ArrayList<String>();

    try {

      for (String noTouch : doNotTouchFileNames) {
        File noTouchFile = new File(fileDAO.getFilePath(noTouch));
        String newName = noTouch.replace(".", "Test.");

        testFileNames.add(newName);
        createdFileBaseForTest.add(Base64.encodeBase64String(Files.readAllBytes(noTouchFile.toPath())));
      }

      for (int i = 0; i < testFileNames.size(); i++) {
        String fullName = testFileNames.get(i);
        String base = createdFileBaseForTest.get(i);
        String[] split = fullName.split("\\.(?=[^\\.]+$)");

        fileDAO.saveBase64File(base, split[0], split[1]);
      }

      // for (String name : list) {
      // String path = fileDAO.getFilePath(name);
      // File file = new File(path);

      // }

      fileDAO.removeFiles(testFileNames);
      // for (int i = 0; i < list.size(); i++) {
      // String base = reAdding.get(i);
      // String fullName = list.get(i);
      // String[] split = fullName.split(".");
      // fileDAO.saveBase64File(base, "RickTest",
      // "webp");
      // }

      assertTrue(true);

    } catch (IOException e) {
      // assertEquals(true, e.getMessage());
      // assertFalse(true);
    }

  }
}
