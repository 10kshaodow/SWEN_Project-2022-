package com.estore.api.estoreapi.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estore.api.estoreapi.model.HealthReport;
import com.estore.api.estoreapi.model.ImageSource;
import com.estore.api.estoreapi.persistence.IFileDAO;
import com.estore.api.estoreapi.persistence.IHealthReportDAO;

/**
 * Handles the REST API requests for the HealthReport resource
 * 
 * @author SWEN 05 Team 4D - Big Development
 */
@RestController
@RequestMapping("health-reports")
public class HealthReportController {
    private IHealthReportDAO healthReportDao;
    private IFileDAO imageFileDao;
    private static final Logger LOG = Logger.getLogger(HealthReportController.class.getName());

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param healthReportDao The {@link healthReportDAO healthReport Data Access
     *                        Object} to
     *                        perform CRUD operations
     */
    public HealthReportController(IHealthReportDAO healthReportDao, IFileDAO imageFileDao) {
        this.healthReportDao = healthReportDao;
        this.imageFileDao = imageFileDao;
    }

    /**
     * simple error handler for requests that may throw an error
     * logs to console and returns a response of specified type
     * 
     * @param <T> takes in a generalized type
     * @param e   takes in the error message
     * @return a response entity of the specified typ
     */
    private <T> ResponseEntity<T> errorHandler(Exception e) {
        LOG.severe(e.getLocalizedMessage());
        return new ResponseEntity<T>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // insert your methods here

    /**
     * Gets the entire healthReports array from the DAO
     * 
     * @return ResponseEntity with a okay status and the healthReports array or
     *         a ResponseEntity with a Internal Server Error if something went wrong
     *         loading the data
     */
    @GetMapping("")
    public ResponseEntity<HealthReport[]> getAllHealthReports() {
        LOG.info("GET /healthReports");

        try {
            HealthReport[] healthReports = healthReportDao.getAllHealthReports();
            healthReports = Arrays.stream(healthReports)
                    .map((_healthReport) -> {
                        _healthReport.normalize();
                        return _healthReport;
                    }).toArray(HealthReport[]::new);
            return new ResponseEntity<HealthReport[]>(healthReports, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return this.errorHandler(e);
        }
    }

    /**
     * Retrieves a {@linkplain HealthReport healthReport} with the given id
     * 
     * @param id The id of the {@link HealthReport healthReport} to get
     * 
     * @return a {@link HealthReport healthReport} object with the matching id
     *         <br>
     *         null if no {@link HealthReport healthReport} with a matching id is
     *         found
     * 
     * @throws IOException if an issue with underlying storage
     */
    @GetMapping("/{id}")
    public ResponseEntity<HealthReport> getHealthReport(@PathVariable int id) {
        LOG.info("GET /health-reports/" + id);

        try {
            HealthReport healthReport = healthReportDao.getHealthReport(id);
            if (healthReport != null) {
                healthReport.normalize();
                return new ResponseEntity<HealthReport>(healthReport, HttpStatus.OK);
            } else
                return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all healthReports whose name contains
     * the text in name
     * 
     * @param id The id of the {@link HealthReport healthReport} to search for
     * 
     * @return ResponseEntity with array of healthReport objects (may be empty) and
     *         HTTP status of OK<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     *         Example: Find all healthReport that contain the text "fa"
     *         GET http://localhost:8080/health-reports/?searchTerm=fa
     */
    @GetMapping("/")
    public ResponseEntity<HealthReport[]> searchHealthReports(@RequestParam String searchTerm) {
        LOG.info("GET /health-reports/?searchTerm=" + searchTerm);
        try {
            HealthReport[] healthReports = Arrays.stream(healthReportDao.findHealthReports(searchTerm))
                    .map((_healthReport) -> {
                        _healthReport.normalize();
                        return _healthReport;
                    }).toArray(HealthReport[]::new);

            return new ResponseEntity<HealthReport[]>(healthReports, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain HealthReport healthReport} with a given id
     * 
     * @param healthReport - The {@link HealthReport healthReport} to create
     * 
     * @return a {@link HealthReport healthReport} object with the matching id
     *         <br>
     *         will be removed from the healthReport list
     * 
     * @throws IOException if an issue with underlying storage, such as an invalid
     *                     id.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<HealthReport> deleteHealthReport(@PathVariable int id) {
        LOG.info("DELETE /health-reports/" + id);
        try {
            HealthReport healthReport = healthReportDao.getHealthReport(id);

            if (healthReport != null && healthReport.hasOldImages()) {
                healthReport.unNormalize();
                this.imageFileDao.removeFiles(healthReport.getFileName());
            }

            Boolean healthReport_void = healthReportDao.deleteHealthReport(id);
            if (healthReport_void != false) {

                return new ResponseEntity<HealthReport>(healthReport, HttpStatus.OK);
            } else
                return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain HealthReport healthReport} with the provided
     * healthReport object
     * 
     * @param healthReport - The {@link HealthReport healthReport} to create
     * 
     * @return ResponseEntity with created {@link HealthReport healthReport} object
     *         and HTTP
     *         status of CREATED<br>
     *         ResponseEntity with HTTP status of CONFLICT if {@link HealthReport
     *         healthReport} object already exists<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * @throws IOException
     */
    @PostMapping("")
    public ResponseEntity<HealthReport> createHealthReport(@RequestBody HealthReport healthReport) throws IOException {
        LOG.info("POST /healthReports " + healthReport);
        ArrayList<String> newFileNameList = new ArrayList<String>();
        ArrayList<ImageSource> newFileSourceList = new ArrayList<ImageSource>();
        ArrayList<String> backupFileNameList = new ArrayList<String>();

        try {

            // if(healthReport != null){
            if (healthReport.hasNewImages()) {

                for (int i = 0; i < healthReport.fileSource.size(); i++) {
                    ImageSource fileSource = healthReport.fileSource.get(i);
                    String fileName = this.imageFileDao.saveBase64File(fileSource.base64,
                            fileSource.getImageName(), fileSource.getImageType());
                    newFileNameList.add(fileName);
                    backupFileNameList.add(fileName);
                }

                healthReport.setFileName(newFileNameList);
                healthReport.setFileSource(newFileSourceList);

            }
            // }
            HealthReport newHealthReport = healthReportDao.createHealthReport(healthReport);

            LOG.info("Created HealthReport: " + newHealthReport);

            if (newHealthReport != null) {
                newHealthReport.normalize();
                return new ResponseEntity<HealthReport>(newHealthReport, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }

        } catch (IOException e) {
            if (backupFileNameList.size() > 0) {
                this.imageFileDao.removeFiles(backupFileNameList);
            }
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Updates the {@linkplain HealthReport healthReport} with the provided
     * {@linkplain HealthReport healthReport} object, if it exists
     * 
     * @param healthReport The {@link HealthReport healthReport} to update
     * 
     * @return ResponseEntity with updated {@link HealthReport healthReport} object
     *         and HTTP
     *         status of OK if updated<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * @throws IOException
     */
    @PutMapping("")
    public ResponseEntity<HealthReport> updateHealthReport(@RequestBody HealthReport healthReport) throws IOException {
        LOG.info("PUT /healthReports " + healthReport);

        ArrayList<String> newFileNameList = new ArrayList<String>();
        ArrayList<String> backupFileNameList = new ArrayList<String>();
        ArrayList<ImageSource> newFileSourceList = new ArrayList<ImageSource>();

        try {

            LOG.info("Calling DAO");
            HealthReport oldHealthReport = healthReportDao.getHealthReport(healthReport.reportID);
            // if there is an image allready on the server delete it
            LOG.info("Checking images on DAO");
            if (oldHealthReport.hasOldImages()) {
                oldHealthReport.unNormalize();
                for (int i = 0; i < oldHealthReport.fileName.size(); i++) {
                    String fileName = oldHealthReport.fileName.get(i);
                    boolean found = false;
                    for (int j = 0; j < healthReport.fileName.size(); j++) {
                        LOG.info(fileName + " == " + getFileName(healthReport.fileName.get(j)));
                        if (fileName.equals(getFileName(healthReport.fileName.get(j)))) {
                            found = true;
                        }
                    }

                    if (!found) {
                        LOG.info("Could not find " + fileName);
                        this.imageFileDao.removeFile(fileName);
                    }
                }
            }

            newFileNameList = healthReport.fileName;

            for (int i = 0; i < healthReport.fileSource.size(); i++) {
                ImageSource fileSource = healthReport.fileSource.get(i);
                LOG.info("64: " + fileSource.base64);
                String newFileName = this.imageFileDao.saveBase64File(fileSource.base64,
                        fileSource.getImageName(), fileSource.getImageType());
                // String photo_id = String.valueOf(healthReport.getFalconID()) + "-" +
                // String.valueOf(healthReport.getReportID()) + "-" + newFileName;
                newFileNameList.add(newFileName);
                backupFileNameList.add(newFileName);
            }

            healthReport.setFileName(newFileNameList);
            healthReport.setFileSource(newFileSourceList);

            HealthReport newHealthReport = healthReportDao.updateHealthReport(healthReport);

            if (newHealthReport != null) {
                newHealthReport.normalize();
                return new ResponseEntity<HealthReport>(newHealthReport, HttpStatus.OK);
            } else {
                if (backupFileNameList.size() > 0) {
                    this.imageFileDao.removeFiles(backupFileNameList);
                }
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } catch (IOException e) {
            if (backupFileNameList.size() > 0) {
                this.imageFileDao.removeFiles(backupFileNameList);
            }
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String getFileName(String fileName) {
        String url = "http://localhost:8080/static/";
        if (fileName != null) {
            if (fileName.indexOf(url) > -1) {
                return fileName.substring(url.length());
            } else {
                return fileName;
            }
        }

        return "file.jpg";
    }

    /*
     * private void addPhotoID(HealthReport healthReport, int id){
     * String photo_id = String.valueOf(healthReport.getFalconID()) + "-" +
     * String.valueOf(id) + "-";
     * for (int i = 0; i < healthReport.fileName.size(); i++) {
     * 
     * String oldImageName = healthReport.fileName.get(i);
     * if(oldImageName.indexOf(photo_id) > 0){
     * continue;
     * }
     * else{
     * String newImageName = photo_id + oldImageName;
     * healthReport.fileName.set(i, newImageName);
     * }
     * }
     * }
     */

}