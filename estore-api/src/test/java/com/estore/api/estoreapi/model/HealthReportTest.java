package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.ImageSource;
import com.estore.api.estoreapi.model.HealthReport.DietStatus;
import com.estore.api.estoreapi.model.HealthReport.FitnessStatus;
import com.estore.api.estoreapi.model.HealthReport.SocialStatus;
import com.estore.api.estoreapi.model.HealthReport;



class HealthReportTest {

  int reportID = 0;
  int falconID = 2;
  Date reportDate = new Date();
  HealthStatus healthStatus = HealthStatus.Well;
  String healthDescription = "Health Description";
  ArrayList<String> keyNotes = new ArrayList<String>();
  DietStatus diet = DietStatus.Nutritional;
  FitnessStatus fitness = FitnessStatus.Active;
  SocialStatus social = SocialStatus.Lively;
  boolean reproductiveStatus = true;
  ArrayList<String> fileNameList = new ArrayList<String>();
  ArrayList<ImageSource> fileSourceList = new ArrayList<ImageSource>();

   //Test the HealthReport Model Constuctor with no filename or source
   
  @Test
  void constructionNoFileTest() {
    HealthReport healthReport = createHealthReport(falconID);
    String actual = healthReport.toString();

    String expectedHealthReport = String.format(HealthReport.HEALTH_REPORT_STRING, reportID, falconID, healthStatus, healthDescription, diet, fitness, social, reproductiveStatus, HealthReport.fileNameToString(fileNameList), HealthReport.fileSourceToString(fileSourceList));

    assertEquals(expectedHealthReport, actual);
  }

   //Test the HealthReport Model Constuctor with no filename
   
  @Test
  void constructionWithFileSourceTest() {
    ImageSource source = new ImageSource("TestBase64", "TestOriginalName");
    ArrayList<ImageSource> fileSourceList = new ArrayList<ImageSource>();
    fileSourceList.add(source);
    HealthReport healthReport = createHealthReport(falconID);
    healthReport.setFileSource(fileSourceList);    

    String actual = healthReport.toString();

    String expectedHealthReport = String.format(HealthReport.HEALTH_REPORT_STRING, reportID, falconID, healthStatus, healthDescription, diet, fitness, social, reproductiveStatus, HealthReport.fileNameToString(fileNameList), HealthReport.fileSourceToString(fileSourceList));
    assertEquals(expectedHealthReport, actual);
  }

  
   //Test the HealthReport Model Constuctor with no source
   
  @Test
  void constructionWithFileNameTest() {
    HealthReport healthReport = createHealthReport(falconID);
    String actual = healthReport.toString();
    String expectedHealthReport = String.format(HealthReport.HEALTH_REPORT_STRING, reportID, falconID, healthStatus, healthDescription, diet, fitness, social, reproductiveStatus, HealthReport.fileNameToString(fileNameList), HealthReport.fileSourceToString(fileSourceList));
    assertEquals(expectedHealthReport, actual);
  }
  
   //Test if a healthReport has a new image to be proccessed
   
  @Test
  void hasNewImageTest() {
    ArrayList<ImageSource> fileSourceList = new ArrayList<ImageSource>(); 
    ImageSource source = new ImageSource("TestBase64", "TestOriginalName");
    HealthReport healthReport = createHealthReport(falconID);
    fileSourceList.add(source);
    healthReport.setFileSource(fileSourceList);
    Boolean actual = healthReport.hasNewImages();

    Boolean expected = true;

    assertEquals(expected, actual);
  }

  
   //Test if a healthReport has a new image to be proccessed
   
  @Test
  void notHasNewImageTest() {

    HealthReport healthReport = createHealthReport(falconID);
    Boolean actual = healthReport.hasNewImages();

    Boolean expected = false;

    assertEquals(expected, actual);
  }

  
   //Test if a healthReport has a an old image
   
  @Test
  void hasOldImageTest() {
    ArrayList<String> fileNameList = new ArrayList<String>();
    HealthReport healthReport = createHealthReport(falconID);
    String testFileName = "TestFile";
    fileNameList.add(testFileName);
    healthReport.setFileName(fileNameList);

    Boolean actual = healthReport.hasOldImages();

    Boolean expected = true;

    assertEquals(expected, actual);
    healthReport.setFileName(null);
    assertEquals(new ArrayList<String>(), healthReport.getFileName());
  }

  
   //Test if a healthReport has a an old image
   
  @Test
  void notHasOldImageTest() {
    HealthReport healthReport = createHealthReport(falconID);
    Boolean actual = healthReport.hasOldImages();

    Boolean expected = false;

    assertEquals(expected, actual);
  }

  
   //Test the normal function for sending healthReport to the ui
   //test that the filename attribute is formatted to be accessed on the internet
   
  @Test
  void normalizeServerPhotoTest() {
    ArrayList<String> fileNameList = new ArrayList<String>();
    HealthReport healthReport = createHealthReport(falconID);
    String testFileName = "TestFile";
    fileNameList.add(testFileName);
    healthReport.setFileName(fileNameList);

    healthReport.normalize();
    String actual = healthReport.getFileName().get(0);

    String expected = "http://localhost:8080/static/TestFile";

    assertEquals(expected, actual);

    healthReport.setFileName(null);
    healthReport.normalize();

  }

  
   //Test the normal function when the file name is allready formated for the ui
   
  @Test
  void normalizeUIPhotoTest() {
    ArrayList<String> fileNameList = new ArrayList<String>();
    HealthReport healthReport = createHealthReport(falconID);
    String testFileName = "TestFile";
    fileNameList.add(testFileName);
    healthReport.setFileName(fileNameList);

    healthReport.normalize();
    String actual = healthReport.getFileName().get(0);

    String expected = "http://localhost:8080/static/TestFile";
    assertEquals(expected, actual);
  }

  
   //Test the unnormal function for recieving a healthReport from the ui
   //test that the filename attribute is formatted to be NOT accessed on the
   //internet
   
  @Test
  void unNormalizeUIFileTest() {
    ArrayList<String> fileNameList = new ArrayList<String>();
    HealthReport healthReport = createHealthReport(falconID);
    String testFileName = "TestFile";
    fileNameList.add(testFileName);
    healthReport.setFileName(fileNameList);

    healthReport.unNormalize();
    String actual = healthReport.getFileName().get(0);

    String expected = "TestFile";

    assertEquals(expected, actual);
  }

  
   //Test the unormal function when the file is allready in the format of the
   //server
   
  @Test
  void unNormalizeServerFileTest() {
    ArrayList<String> fileNameList = new ArrayList<String>();
    HealthReport healthReport = createHealthReport(falconID);
    String testFileName = "Test File";
    fileNameList.add(testFileName);
    healthReport.setFileName(fileNameList);

    healthReport.unNormalize();
    String actual = healthReport.getFileName().get(0);

    String expected = "Test File";

    assertEquals(expected, actual);
  }

  
   //Test the unnormalized function when there is no value for a filename
   
  @Test
  void unNormalizeMissingFileTest() {
    HealthReport healthReport = createHealthReport(falconID);
    healthReport.unNormalize();
    boolean expected = healthReport.hasOldImages();

    assertFalse(expected);
  }


  @Test 
  void gettersSetters() {
    HealthReport healthReport = createHealthReport(falconID); 
    healthReport.setReportID(reportID);
    assertEquals(healthReport.getReportID(), reportID);
    healthReport.setFalconID(falconID);
    assertEquals(healthReport.getFalconID(), falconID);
    healthReport.setReportDate(reportDate);
    assertEquals(healthReport.getReportDate(), reportDate);
    healthReport.setHealthStatus(healthStatus);
    assertEquals(healthReport.getHealthStatus(), healthStatus);
    healthReport.setHealthDescription(healthDescription);
    assertEquals(healthReport.getHealthDescription(), healthDescription);
    healthReport.setKeyNotes(keyNotes);
    assertEquals(healthReport.getKeyNotes(), keyNotes);
    healthReport.setDiet(diet);
    assertEquals(healthReport.getDiet(), diet);
    healthReport.setFitness(fitness);
    assertEquals(healthReport.getFitness(), fitness);
    healthReport.setSocial(social);
    assertEquals(healthReport.getSocial(), social);
    healthReport.setReproductiveStatus(reproductiveStatus);
    assertEquals(healthReport.getReproductiveStatus(), reproductiveStatus);
    healthReport.setFileSource(fileSourceList);
    assertEquals(healthReport.getFileSource(), fileSourceList);
    healthReport.setFileSource(null);
    assertEquals(healthReport.getFileSource(), new ArrayList<ImageSource>());
  }

  
   //Test Setting a healthReports file name
   
  @Test
  void testSettingFileName() {
    ArrayList<String> fileNameList = new ArrayList<String>();
    HealthReport healthReport = createHealthReport(falconID);
    String testFileName = "TestFile";
    fileNameList.add(testFileName);
    healthReport.setFileName(fileNameList);

    String expected = "TestFile";

    assertEquals(expected, healthReport.getFileName().get(0));
  }

  
   //Test Setting a healthReports file source
   
  @Test
  void testSettingFileSource() {
    ArrayList<ImageSource> fileSourceList = new ArrayList<ImageSource>(); 
    ImageSource source = new ImageSource("Test64", "65");
    fileSourceList.add(source);
    HealthReport healthReport = createHealthReport(falconID);
    healthReport.setFileSource(fileSourceList);

    String expected = source.toString();
    String actual = healthReport.getFileSource().get(0).toString();

    assertEquals(expected, actual);
  }


private HealthReport createHealthReport(int id) {
  HealthReport healthReport = new HealthReport(reportID, falconID, reportDate, healthStatus, healthDescription, keyNotes, diet, fitness, social, reproductiveStatus, fileNameList, fileSourceList);

   return healthReport;
 }
 
}