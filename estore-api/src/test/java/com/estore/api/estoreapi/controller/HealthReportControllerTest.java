package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.estore.api.estoreapi.model.HealthReport;
import com.estore.api.estoreapi.model.HealthReport.DietStatus;
import com.estore.api.estoreapi.model.HealthReport.FitnessStatus;
import com.estore.api.estoreapi.model.HealthReport.SocialStatus;
import com.estore.api.estoreapi.model.HealthStatus;
import com.estore.api.estoreapi.model.ImageSource;
import com.estore.api.estoreapi.persistence.FileDAO;
import com.estore.api.estoreapi.persistence.HealthReportFileDAO;
import com.estore.api.estoreapi.persistence.IFileDAO;
import com.estore.api.estoreapi.persistence.IHealthReportDAO;

public class HealthReportControllerTest {

  private static final Logger LOG = Logger.getLogger(HealthReportController.class.getName());

  // Test that get healthReport by id succedes with a found healthReport
  //
  //
  @Test
  void testGetHealthReportById() throws Exception {
    IHealthReportDAO healthReportDAO = this.mockHealthReport();
    IFileDAO fileDAO = this.mockFile();
    HealthReportController healthReportController = new HealthReportController(healthReportDAO, fileDAO);
    HealthReport healthReport = this.createHealthReport(4);
    when(healthReportDAO.getHealthReport(4)).thenReturn(healthReport);

    ResponseEntity<HealthReport> response = healthReportController.getHealthReport(4);
    HealthReport body = response.getBody();

    String expected = healthReport.toString();

    assertNotNull(body);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(expected, body.toString());
    verify(healthReportDAO).getHealthReport(4);
  }

  // Test get healthReport id with an id that does not exist returns null
  //
  ///
  @Test
  void testGetHealthReportByIdNotFound() throws Exception {
    IHealthReportDAO healthReportDAO = this.mockHealthReport();
    IFileDAO fileDAO = this.mockFile();
    HealthReportController healthReportController = new HealthReportController(healthReportDAO, fileDAO);
    when(healthReportDAO.getHealthReport(4)).thenReturn(null);

    ResponseEntity<HealthReport> response = healthReportController.getHealthReport(4);
    HealthReport body = response.getBody();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNull(body);
    verify(healthReportDAO).getHealthReport(4);
  }

  // Ensure that the get healthReport by id method fails from an internal
  // server error

  ///
  @Test
  void testGetHealthReportByIdFailed() throws Exception {
    IHealthReportDAO healthReportDAO = this.mockHealthReport();
    IFileDAO fileDAO = this.mockFile();
    HealthReportController healthReportController = new HealthReportController(healthReportDAO, fileDAO);
    when(healthReportDAO.getHealthReport(4)).thenAnswer(answer -> {
      throw new IOException("Test Should Fail");
    });

    ResponseEntity<HealthReport> response = healthReportController.getHealthReport(4);
    HealthReport body = response.getBody();

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertNull(body);
    verify(healthReportDAO).getHealthReport(4);
  }

  // Test getting all available healthReports
  //
  ///
  @Test
  void testGetAllHealthReports() throws Exception {
    IHealthReportDAO healthReportDAO = this.mockHealthReport();
    IFileDAO fileDAO = this.mockFile();
    HealthReportController healthReportController = new HealthReportController(healthReportDAO, fileDAO);
    HealthReport[] healthReports = this.createHealthReports(3);
    when(healthReportDAO.getAllHealthReports()).thenReturn(healthReports);

    ResponseEntity<HealthReport[]> response = healthReportController.getAllHealthReports();
    HealthReport[] body = response.getBody();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(Arrays.toString(healthReports), Arrays.toString(body));
    verify(healthReportDAO).getAllHealthReports();

  }

  // Test that getting all healthReports fails gracefully
  //
  ///
  @Test
  void testGetAllHealthReportsFails() throws Exception {
    IHealthReportDAO healthReportDAO = this.mockHealthReport();
    IFileDAO fileDAO = this.mockFile();
    HealthReportController healthReportController = new HealthReportController(healthReportDAO, fileDAO);
    // HealthReport[] healthReports = this.createHealthReports(3);
    when(healthReportDAO.getAllHealthReports()).thenAnswer(answer -> {
      throw new IOException("Test Should Fail");
    });

    ResponseEntity<HealthReport[]> response = healthReportController.getAllHealthReports();
    HealthReport[] body = response.getBody();

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertNull(body);
    verify(healthReportDAO).getAllHealthReports();

  }

  // Test Deleting a healthReport succedes
  //
  ///
  @Test
  void testDeleteHealthReport() throws Exception {
    IHealthReportDAO healthReportDAO = this.mockHealthReport();
    IFileDAO fileDAO = this.mockFile();
    HealthReportController healthReportController = new HealthReportController(healthReportDAO, fileDAO);
    HealthReport healthReports = this.createHealthReport(0);
    when(healthReportDAO.getHealthReport(0)).thenReturn(healthReports);
    when(healthReportDAO.deleteHealthReport(0)).thenReturn(true);

    ResponseEntity<HealthReport> response = healthReportController.deleteHealthReport(0);
    HealthReport body = response.getBody();

    assertNotNull(body);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(healthReports.toString(), body.toString());
    verify(healthReportDAO).getHealthReport(0);
    verify(healthReportDAO).deleteHealthReport(0);

    ArrayList<String> fileNameList = new ArrayList<String>();
    String testFileName = "testFileDelete";
    fileNameList.add(testFileName);

    healthReports.setFileName(fileNameList);
    when(healthReportDAO.getHealthReport(0)).thenReturn(healthReports);
    when(healthReportDAO.deleteHealthReport(0)).thenReturn(true);

    response = healthReportController.deleteHealthReport(0);
    body = response.getBody();

  }

  // Test deleting a healthReport that is not found
  ///
  @Test
  void testDeleteHealthReportNotFound() throws Exception {
    IHealthReportDAO healthReportDAO = this.mockHealthReport();
    IFileDAO fileDAO = this.mockFile();
    HealthReportController healthReportController = new HealthReportController(healthReportDAO, fileDAO);

    when(healthReportDAO.getHealthReport(0)).thenReturn(null);
    when(healthReportDAO.deleteHealthReport(0)).thenReturn(false);

    ResponseEntity<HealthReport> response = healthReportController.deleteHealthReport(0);
    HealthReport body = response.getBody();

    assertNull(body);
    assertEquals(HttpStatus.OK, response.getStatusCode());

    verify(healthReportDAO).getHealthReport(0);
    verify(healthReportDAO).deleteHealthReport(0);

  }

  // Test that deleting a healthReport fails gracefully
  //
  ///
  @Test
  void testDeleteHealthReportFails() throws Exception {
    IHealthReportDAO healthReportDAO = this.mockHealthReport();
    IFileDAO fileDAO = this.mockFile();
    HealthReportController healthReportController = new HealthReportController(healthReportDAO, fileDAO);

    when(healthReportDAO.getHealthReport(0)).thenAnswer(answer -> {
      throw new IOException("Test is supposed to fail");
    });
    when(healthReportDAO.deleteHealthReport(0)).thenReturn(false);

    ResponseEntity<HealthReport> response = healthReportController.deleteHealthReport(0);
    HealthReport body = response.getBody();

    assertNull(body);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    verify(healthReportDAO).getHealthReport(0);
    verify(healthReportDAO, times(0)).deleteHealthReport(0);

  }

  // Test creating a new healthReport with no new file
  ///
  @Test
  void testCreatingHealthReportNoNewFile() throws Exception {
    IHealthReportDAO healthReportDAO = this.mockHealthReport();
    IFileDAO fileDAO = this.mockFile();
    HealthReportController healthReportController = new HealthReportController(healthReportDAO, fileDAO);

    HealthReport healthReport = this.createHealthReport(0);

    when(fileDAO.saveBase64File(null, null, null)).thenReturn(null);
    when(healthReportDAO.createHealthReport(healthReport)).thenReturn(healthReport);

    ResponseEntity<HealthReport> response = healthReportController.createHealthReport(healthReport);
    HealthReport body = response.getBody();

    assertNotNull(body);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(healthReport.toString(), body.toString());
    verify(healthReportDAO).createHealthReport(healthReport);
    verify(fileDAO, times(0)).saveBase64File(null, null, null);

  }

  // Test creating a new healthReport with a new filesource
  ///
  @Test
  void testCreatingHealthReportNewFile() throws Exception {
    IHealthReportDAO healthReportDAO = this.mockHealthReport();
    IFileDAO fileDAO = this.mockFile();
    HealthReportController healthReportController = new HealthReportController(healthReportDAO,
        fileDAO);

    ImageSource source = new ImageSource("Test64", "TestName.jpg");
    ArrayList<ImageSource> fileSourceList = new ArrayList<ImageSource>();
    fileSourceList.add(source);

    HealthReport healthReport = this.createHealthReport(0);
    healthReport.setFileSource(fileSourceList);

    when(fileDAO.saveBase64File(source.base64, "John-1665453670602",
        healthReport.fileSource.get(0).getImageType())).thenReturn("TestFile");
    when(healthReportDAO.createHealthReport(healthReport)).thenAnswer(answer -> {
      ArrayList<String> fileNameList = new ArrayList<String>();
      String testFileName = "TestServerFile";
      fileNameList.add(testFileName);
      healthReport.setFileName(fileNameList);
      ArrayList<ImageSource> newFileSourceList = new ArrayList<ImageSource>();
      healthReport.setFileSource(newFileSourceList);
      return healthReport;
    });

    ResponseEntity<HealthReport> response = healthReportController.createHealthReport(healthReport);
    HealthReport body = response.getBody();

    assertNotNull(body);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(healthReport.toString(), body.toString());
    assertEquals("http://localhost:8080/static/TestServerFile",
        body.getFileName().get(0));
    verify(healthReportDAO).createHealthReport(healthReport);

  }

  @Test
  void testCreatingHealthReportNewFileFails() throws Exception {
    IHealthReportDAO healthReportDAO = this.mockHealthReport();
    IFileDAO fileDAO = this.mockFile();
    HealthReportController healthReportController = new HealthReportController(healthReportDAO,
        fileDAO);

    ImageSource source = new ImageSource("Test64", "TestName.jpg");
    ArrayList<ImageSource> fileSourceList = new ArrayList<ImageSource>();
    fileSourceList.add(source);

    HealthReport healthReport = this.createHealthReport(0);
    healthReport.setFileSource(fileSourceList);

    when(fileDAO.saveBase64File(source.base64, healthReport.fileSource.get(0).getImageName(),
        healthReport.fileSource.get(0).getImageType())).thenReturn("TestFile");
    when(healthReportDAO.createHealthReport(healthReport)).thenAnswer(answer -> {
      // ArrayList<String> fileNameList = new ArrayList<String>();
      // String testFileName = "TestServerFile";
      // fileNameList.add(testFileName);
      // healthReport.setFileName(fileNameList);
      // ArrayList<ImageSource> newFileSourceList = new ArrayList<ImageSource>();
      // healthReport.setFileSource(newFileSourceList);
      // return healthReport;
      throw new IOException("Test Should Fail");
    });

    ResponseEntity<HealthReport> response = healthReportController.createHealthReport(healthReport);
    HealthReport body = response.getBody();

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    verify(healthReportDAO).createHealthReport(healthReport);

  }

  // Test that creating a healthReport with a invalid file does not upload the
  // file

  ///
  @Test
  void testCreateHealthReportFileSourceValidationFails() throws Exception {
    IHealthReportDAO healthReportDAO = this.mockHealthReport();
    IFileDAO fileDAO = this.mockFile();
    HealthReportController healthReportController = new HealthReportController(healthReportDAO,
        fileDAO);

    ArrayList<ImageSource> fileSourceList = new ArrayList<ImageSource>();
    ImageSource source = new ImageSource("Test64", "");
    fileSourceList.add(source);

    HealthReport healthReport = this.createHealthReport(0);
    healthReport.setFileSource(fileSourceList);

    assertEquals(true, healthReport.hasNewImages());
    assertEquals(false, source.isValid());
    assertEquals(false, healthReport.fileSource.get(0).isValid());

    when(healthReportDAO.createHealthReport(healthReport)).thenReturn(null);

    ResponseEntity<HealthReport> response = healthReportController.createHealthReport(healthReport);
    HealthReport body = response.getBody();

    verify(healthReportDAO, times(1)).createHealthReport(healthReport);
    verify(fileDAO, times(0)).saveBase64File(null, null, null);

    assertNull(body);
    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
  }

  // Test creating a healthReport that has a duplicate id
  // should return null
  ///
  @Test
  void testCreatingHealthReportWithConflictingId() throws Exception {
    IHealthReportDAO healthReportDAO = this.mockHealthReport();
    IFileDAO fileDAO = this.mockFile();
    HealthReportController healthReportController = new HealthReportController(healthReportDAO, fileDAO);

    HealthReport healthReport = this.createHealthReport(0);

    when(fileDAO.saveBase64File(null, null, null)).thenReturn(null);
    when(healthReportDAO.createHealthReport(healthReport)).thenReturn(null);

    ResponseEntity<HealthReport> response = healthReportController.createHealthReport(healthReport);
    HealthReport body = response.getBody();

    assertNull(body);
    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());

    verify(healthReportDAO).createHealthReport(healthReport);
    verify(fileDAO, times(0)).saveBase64File(null, null, null);

    // testing the source gets deleted if added when controller fails

    ImageSource source = new ImageSource("Test64", "TestName.jpg");
    ArrayList<ImageSource> fileSourceList = new ArrayList<ImageSource>();
    fileSourceList.add(source);

    healthReport = this.createHealthReport(0);
    healthReport.setFileSource(fileSourceList);

    assertEquals(true, healthReport.fileSource.get(0).isValid());

    when(fileDAO.saveBase64File(source.base64,
        String.valueOf(healthReport.reportID),
        healthReport.fileSource.get(0).getImageType())).thenReturn("TestFile.jpg");
    when(healthReportDAO.createHealthReport(healthReport)).thenReturn(null);

    response = healthReportController.createHealthReport(healthReport);
    body = response.getBody();

    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());

    // assertEquals(true, healthReport.fileSource);

    verify(fileDAO, times(1)).saveBase64File(source.base64,
        source.getImageName(),
        source.getImageType());

    ArrayList<String> fileNameList = new ArrayList<String>();
    String testFileName = "TestFile.jpg";
    fileNameList.add(testFileName);
    // verify(fileDAO, times(1)).removeFiles(fileNameList);
  }

  // Test that creating a new healthReport fails gracefully
  //
  ///
  @Test
  void testCreatingHealthReportThrowsException() throws Exception {
    IHealthReportDAO healthReportDAO = this.mockHealthReport();
    IFileDAO fileDAO = this.mockFile();
    HealthReportController healthReportController = new HealthReportController(healthReportDAO, fileDAO);

    HealthReport healthReport = this.createHealthReport(0);

    when(fileDAO.saveBase64File(null, null, null)).thenReturn(null);
    when(healthReportDAO.createHealthReport(healthReport)).thenAnswer(answer -> {
      throw new IOException("Test Should Fail");
    });

    ResponseEntity<HealthReport> response = healthReportController.createHealthReport(healthReport);
    HealthReport body = response.getBody();

    assertNull(body);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    verify(healthReportDAO).createHealthReport(healthReport);
    verify(fileDAO, times(0)).saveBase64File(null, null, null);

    // healthReport.setFileName("Hello");

    // when(fileDAO.saveBase64File(null, null, null)).thenReturn(null);
    // when(healthReportDAO.createHealthReport(healthReport)).thenReturn(null);

    // response = healthReportController.createHealthReport(healthReport);
    // body = response.getBody();

  }

  // Test that updating an existing healthReport succeds
  ///
  @Test
  void testUpdatingHealthReportSuccedes() throws Exception {
    IHealthReportDAO healthReportDAO = this.mockHealthReport();
    IFileDAO fileDAO = this.mockFile();
    HealthReportController healthReportController = new HealthReportController(healthReportDAO, fileDAO);

    HealthReport healthReport = this.createHealthReport(0);

    when(healthReportDAO.getHealthReport(0)).thenReturn(healthReport);
    when(fileDAO.saveBase64File(null, null, null)).thenReturn(null);
    when(healthReportDAO.updateHealthReport(healthReport)).thenReturn(healthReport);

    ResponseEntity<HealthReport> response = healthReportController.updateHealthReport(healthReport);
    HealthReport body = response.getBody();

    verify(healthReportDAO).getHealthReport(0);
    verify(healthReportDAO).updateHealthReport(healthReport);
    verify(fileDAO, times(0)).saveBase64File(null, null, null);

    healthReport.normalize();

    assertNotNull(body);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(healthReport.toString(), body.toString());

  }

  // Test that a prudct updates successfully with a new file and no old file
  ///
  @Test
  void testUpdatingHealthReportSuccedesWithNewFile() throws Exception {
    IHealthReportDAO healthReportDAO = this.mockHealthReport();
    IFileDAO fileDAO = this.mockFile();
    HealthReportController healthReportController = new HealthReportController(healthReportDAO, fileDAO);

    ImageSource source = new ImageSource("Test64", "Test.jpg");
    HealthReport healthReport = this.createHealthReport(0);

    ArrayList<ImageSource> fileSourceList = new ArrayList<ImageSource>();
    fileSourceList.add(source);
    healthReport.setFileSource(fileSourceList);

    when(healthReportDAO.getHealthReport(0)).thenReturn(healthReport);
    when(fileDAO.saveBase64File(source.base64, "JohnName",
        ".jpg")).thenReturn("TestFile.jpg");
    when(healthReportDAO.updateHealthReport(any(HealthReport.class))).thenAnswer(answer -> {
      ArrayList<String> fileNameList = new ArrayList<String>();
      String testFileName = "TestFile.jpg";
      fileNameList.add(testFileName);
      fileSourceList.add(source);
      healthReport.setFileName(fileNameList);
      ArrayList<ImageSource> newFileSourceList = new ArrayList<ImageSource>();
      healthReport.setFileSource(newFileSourceList);
      return healthReport;
    });

    LOG.info("Health Report: 469" + healthReport);
    ResponseEntity<HealthReport> response = healthReportController.updateHealthReport(healthReport);
    HealthReport body = response.getBody();

    verify(healthReportDAO).getHealthReport(0);
    verify(healthReportDAO).updateHealthReport(healthReport);
    // verify(fileDAO, times(1)).saveBase64File(source.base64, anyString(),
    // "jpg");

    healthReport.normalize();

    assertNotNull(body);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(healthReport.toString(), body.toString());

  }

  // Test that a healthReport successfully updates with a new and old file
  ///
  @Test
  void testUpdatingHealthReportSuccedesWithNewFileAndOld() throws Exception {
    IHealthReportDAO healthReportDAO = this.mockHealthReport();
    IFileDAO fileDAO = this.mockFile();
    HealthReportController healthReportController = new HealthReportController(healthReportDAO, fileDAO);

    ImageSource source = new ImageSource("Test64", "Test.jpg");
    ArrayList<ImageSource> fileSourceList = new ArrayList<ImageSource>();
    fileSourceList.add(source);
    HealthReport healthReport = this.createHealthReport(0);

    healthReport.setFileSource(fileSourceList);

    ArrayList<String> fileNameList = new ArrayList<String>();
    String testFileName = "TestDelete";
    fileNameList.add(testFileName);
    healthReport.setFileName(fileNameList);

    when(healthReportDAO.getHealthReport(0)).thenReturn(healthReport);
    when(fileDAO.saveBase64File(source.base64, "JohnName",
        ".jpg")).thenReturn("TestFile.jpg");
    when(healthReportDAO.updateHealthReport(any(HealthReport.class))).thenAnswer(answer -> {
      ArrayList<String> newFileNameList = new ArrayList<String>();
      String testNewFileName = "TestFile.jpg";
      fileNameList.add(testNewFileName);
      healthReport.setFileName(newFileNameList);

      ArrayList<ImageSource> newFileSourceList = new ArrayList<ImageSource>();
      healthReport.setFileSource(newFileSourceList);
      return healthReport;
    });

    ResponseEntity<HealthReport> response = healthReportController.updateHealthReport(healthReport);
    HealthReport body = response.getBody();

    verify(healthReportDAO).getHealthReport(0);
    verify(healthReportDAO).updateHealthReport(healthReport);

    healthReport.normalize();

    assertNotNull(body);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(healthReport.toString(), body.toString());

  }

  @Test
  void testUpdatingHealthReportNullWithNewFileAndOld() throws Exception {
    IHealthReportDAO healthReportDAO = this.mockHealthReport();
    IFileDAO fileDAO = this.mockFile();
    HealthReportController healthReportController = new HealthReportController(healthReportDAO, fileDAO);

    ImageSource source = new ImageSource("Test64", "Test.jpg");
    ArrayList<ImageSource> fileSourceList = new ArrayList<ImageSource>();
    fileSourceList.add(source);
    HealthReport healthReport = this.createHealthReport(0);

    healthReport.setFileSource(fileSourceList);

    ArrayList<String> fileNameList = new ArrayList<String>();
    String testFileName = "TestDelete";
    fileNameList.add(testFileName);
    healthReport.setFileName(fileNameList);

    when(healthReportDAO.getHealthReport(0)).thenReturn(healthReport);
    when(fileDAO.saveBase64File(source.base64, "JohnName",
        ".jpg")).thenReturn("TestFile.jpg");
    when(healthReportDAO.updateHealthReport(any(HealthReport.class))).thenReturn(null);

    ResponseEntity<HealthReport> response = healthReportController.updateHealthReport(healthReport);
    HealthReport body = response.getBody();

    verify(healthReportDAO).getHealthReport(0);
    verify(healthReportDAO).updateHealthReport(healthReport);

    assertEquals(HttpStatus.OK, response.getStatusCode());

  }

  @Test
  void testUpdatingHealthReportErrorWithNewFileAndOld() throws Exception {
    IHealthReportDAO healthReportDAO = this.mockHealthReport();
    IFileDAO fileDAO = this.mockFile();
    HealthReportController healthReportController = new HealthReportController(healthReportDAO, fileDAO);

    ImageSource source = new ImageSource("Test64", "Test.jpg");
    ArrayList<ImageSource> fileSourceList = new ArrayList<ImageSource>();
    fileSourceList.add(source);
    HealthReport healthReport = this.createHealthReport(0);

    healthReport.setFileSource(fileSourceList);

    ArrayList<String> fileNameList = new ArrayList<String>();
    String testFileName = "TestDelete";
    fileNameList.add(testFileName);
    healthReport.setFileName(fileNameList);

    when(healthReportDAO.getHealthReport(0)).thenReturn(healthReport);
    when(fileDAO.saveBase64File(source.base64, "JohnName",
        ".jpg")).thenReturn("TestFile.jpg");
    when(healthReportDAO.updateHealthReport(any(HealthReport.class))).thenAnswer(answer -> {
      throw new IOException("Test should Fail");
    });

    ResponseEntity<HealthReport> response = healthReportController.updateHealthReport(healthReport);
    HealthReport body = response.getBody();

    verify(healthReportDAO).getHealthReport(0);
    verify(healthReportDAO).updateHealthReport(healthReport);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

  }

  @Test
  void testUpdatingHealthReportReturnsNull() throws Exception {
    IHealthReportDAO healthReportDAO = this.mockHealthReport();
    IFileDAO fileDAO = this.mockFile();
    HealthReportController healthReportController = new HealthReportController(healthReportDAO, fileDAO);

    HealthReport healthReport = this.createHealthReport(0);

    when(healthReportDAO.getHealthReport(0)).thenReturn(healthReport);
    when(fileDAO.saveBase64File(null, null, null)).thenReturn(null);
    when(healthReportDAO.updateHealthReport(healthReport)).thenReturn(null);

    ResponseEntity<HealthReport> response = healthReportController.updateHealthReport(healthReport);
    HealthReport body = response.getBody();

    verify(healthReportDAO).getHealthReport(0);
    verify(healthReportDAO).updateHealthReport(healthReport);
    verify(fileDAO, times(0)).saveBase64File(null, null, null);

    // healthReport.normalize();

    assertNull(body);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    // assertEquals(healthReport.toString(), body.toString());

  }

  // Test that updating a healthReport fails gracefully
  ///
  @Test
  void testUpdatingHealthReportFails() throws Exception {
    IHealthReportDAO healthReportDAO = this.mockHealthReport();
    IFileDAO fileDAO = this.mockFile();
    HealthReportController healthReportController = new HealthReportController(healthReportDAO, fileDAO);

    HealthReport healthReport = this.createHealthReport(0);

    when(healthReportDAO.getHealthReport(0)).thenAnswer(answer -> {
      throw new IOException("Test Should Fail");
    });
    when(fileDAO.saveBase64File(null, null, null)).thenReturn(null);
    when(healthReportDAO.updateHealthReport(healthReport)).thenReturn(null);

    ResponseEntity<HealthReport> response = healthReportController.updateHealthReport(healthReport);
    HealthReport body = response.getBody();

    verify(healthReportDAO).getHealthReport(0);
    verify(healthReportDAO, times(0)).updateHealthReport(healthReport);
    verify(fileDAO, times(0)).saveBase64File(null, null, null);

    assertNull(body);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    healthReport.setFileName(null);

  }

  @Test
  public void testSearchHealthReports() throws IOException {
    IHealthReportDAO healthReportDAO = this.mockHealthReport();
    IFileDAO fileDAO = this.mockFile();
    HealthReportController healthReportController = new HealthReportController(healthReportDAO, fileDAO);

    HealthReport[] expected = createHealthReports(1);

    when(healthReportDAO.findHealthReports("1")).thenReturn(expected);

    ResponseEntity<HealthReport[]> response = healthReportController.searchHealthReports("1");

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(1, response.getBody().length);

    when(healthReportDAO.findHealthReports("1")).thenAnswer(answer -> {
      throw new IOException("Test should fail");
    });

    response = healthReportController.searchHealthReports("1");

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

  }

  @Test
  public void testGetFileName() {
    IHealthReportDAO healthReportDAO = this.mockHealthReport();
    IFileDAO fileDAO = this.mockFile();
    HealthReportController healthReportController = new HealthReportController(healthReportDAO, fileDAO);

    assertEquals("On the gange", healthReportController.getFileName("On the gange"));
    assertEquals("file.jpg", healthReportController.getFileName(null));
    assertEquals("On the gange", healthReportController.getFileName("http://localhost:8080/static/On the gange"));

  }

  private IHealthReportDAO mockHealthReport() {
    return Mockito.mock(HealthReportFileDAO.class);
  }

  private IFileDAO mockFile() {
    return Mockito.mock(FileDAO.class);
  }

  private HealthReport[] createHealthReports(int amount) {
    HealthReport[] array = new HealthReport[amount];

    for (int i = 0; i < amount; i++) {
      HealthReport healthReport = this.createHealthReport(i);
      array[i] = healthReport;
    }

    return array;

  }

  private ImageSource createSource() {
    return new ImageSource("Test", "Test.jpg");
  }

  private HealthReport createHealthReport(int id) {

    int reportID = 0;
    int falconID = 2;
    Date reportDate = new Date();
    HealthStatus healthStatus = HealthStatus.Well;
    String healthDescription = "Health Description";
    ArrayList<String> keyNotes = new ArrayList<String>();
    DietStatus diet = DietStatus.Nutritional;
    FitnessStatus fitness = FitnessStatus.Active;
    SocialStatus social = SocialStatus.Rowdy;
    boolean reproductiveStatus = true;
    ArrayList<String> fileNameList = new ArrayList<String>();
    ArrayList<ImageSource> fileSourceList = new ArrayList<ImageSource>();

    HealthReport healthReport = new HealthReport(reportID, falconID, reportDate,
        healthStatus, healthDescription, keyNotes, diet, fitness, social,
        reproductiveStatus, fileNameList, fileSourceList);

    return healthReport;
  }
}
