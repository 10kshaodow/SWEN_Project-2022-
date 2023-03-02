package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.HealthReport;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Implements the functionality for JSON file-based peristance for HealthReports
 * 
 * @author SWEN Faculty
 */
@Component
public class HealthReportFileDAO implements IHealthReportDAO {

    // local cache of healthReports
    Map<Integer, HealthReport> healthReports;

    // provides conversion between HealthReport object and their JSON representation
    private ObjectMapper objectMapper;

    // next ReportID to assign
    private static int nextReportID;

    // Filename to read from and write to
    private String filename;

    /**
     * Creates a HealthReport File Data Access Object
     * 
     * @param filename     Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization
     *                     and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public HealthReportFileDAO(@Value("${healthReports.file}") String filename, ObjectMapper objectMapper)
            throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        // load healthReports from the file
        load();
    }

    /**
     * Generates the next id for a new {@linkplain HealthReport healthReport}
     * 
     * @return The next id
     */
    private synchronized static int nextReportID() {
        int id = nextReportID;
        ++nextReportID;
        return id;
    }

    /**
     * Saves the healthReports from the map into the file as an array of JSON
     * objects
     * 
     * @return true if the healthReports were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        // TODO: when getHealthReportsArray is implemented this needs to work
        HealthReport[] healthReportArray = this.getHealthReportsArray();

        for (int i = 0; i < healthReportArray.length; i++) {
            HealthReport healthReport = healthReportArray[i];
            healthReport.unNormalize();
            healthReportArray[i] = healthReport;
        }

        // Serializes the healthReports to JSON format and write to a file
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.writeValue(new File(filename), healthReportArray);
        return true;
    }

    /**
     * Loads healthReports from the JSON file into the map and sets the next id to
     * the largest found id
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        healthReports = new TreeMap<>();
        nextReportID = 0;

        // Deserializes the JSON objects from the file into an array of healthReports
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        HealthReport[] healthReportArray = objectMapper.readValue(new File(filename), HealthReport[].class);

        // Add each healthReport to the tree map and keep track of the greatest id
        for (HealthReport healthReport : healthReportArray) {
            healthReport.unNormalize();
            healthReports.put(healthReport.getReportID(), healthReport);
            if (healthReport.getReportID() > nextReportID)
                nextReportID = healthReport.getReportID();
        }
        // Make the next id one greater than the maximum from the file
        ++nextReportID;
        return true;
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public HealthReport getHealthReport(int id) {
        synchronized (healthReports) {
            if (healthReports.containsKey(id))
                return healthReports.get(id);
            else
                return null;
        }
    }

    /**
     * Generates an array of prodcuts from the tree map for any
     * healthReports that contains the text specified by containsText
     * 
     * If containsText is null, the array contains all of the healthReports
     * in the tree map
     * 
     * @return The array of oroducts, may be empty
     */
    public HealthReport[] getHealthReportsArray() { // if containsText == null, no filter
        ArrayList<HealthReport> heroArrayList = new ArrayList<>();

        for (HealthReport healthReport : healthReports.values()) {
            heroArrayList.add(healthReport);
        }

        HealthReport[] heroArray = new HealthReport[heroArrayList.size()];
        heroArrayList.toArray(heroArray);
        return heroArray;
    }

    /**
     * Retrieves all HealthReports
     * 
     * @return An array of HealthReport objects, may be empty
     * 
     */
    @Override
    public HealthReport[] getAllHealthReports() throws IOException {
        synchronized (healthReports) {
            return this.getHealthReportsArray();
        }
    };

    /**
    ** 
     */
    @Override
    public HealthReport[] findHealthReports(String id) {
        synchronized (healthReports) {
            int falconID = Integer.parseInt(id);
            return getHealthReportsArray(falconID);
        }
    }

    public HealthReport[] getHealthReportsArray(int falconID) {
        ArrayList<HealthReport> heroArrayList = new ArrayList<>();

        for (HealthReport healthReport : healthReports.values()) {
            if (healthReport.getFalconID() == falconID) {
                heroArrayList.add(healthReport);
            }
        }

        HealthReport[] heroArray = new HealthReport[heroArrayList.size()];
        heroArrayList.toArray(heroArray);
        return heroArray;
    }

    /**
     ** {@inheritDoc}
     * 
     * @throws InvalidHealthReportException
     */
    @Override
    public HealthReport createHealthReport(HealthReport healthReport)
            throws IOException {
        synchronized (healthReports) {
            int id = nextReportID();
            // this.addPhotoID(healthReport, id);

            HealthReport newHealthReport = new HealthReport(id, healthReport.getFalconID(),
                    healthReport.getReportDate(), healthReport.getHealthStatus(),
                    healthReport.getHealthDescription(), healthReport.getKeyNotes(), healthReport.getDiet(),
                    healthReport.getFitness(), healthReport.getSocial(), healthReport.getReproductiveStatus(),
                    healthReport.getFileName(), healthReport.getFileSource());
            healthReports.put(newHealthReport.getReportID(), newHealthReport);
            save(); // may throw an IOException
            return newHealthReport;
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public HealthReport updateHealthReport(HealthReport healthReport) throws IOException {
        synchronized (healthReports) {
            if (healthReports.containsKey(healthReport.getReportID()) == false)
                return null; // healthReport does not exist

            healthReports.put(healthReport.getReportID(), healthReport);
            save(); // may throw an IOException
            return healthReport;
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public boolean deleteHealthReport(int id) throws IOException {
        synchronized (healthReports) {
            if (healthReports.containsKey(id)) {
                healthReports.remove(id);
                return save();
            } else
                return false;
        }
    }

    /**
     * This is never called so it is not needed ?
     */
    // public boolean healthReportNameTaken(String healthReportName) {
    // return (findHealthReports(healthReportName).length > 0);
    // }

    /**
     * This is also never called so it is not needed ?
     */
    // public void addPhotoID(HealthReport healthReport, int id) {
    // String photo_id = String.valueOf(healthReport.getFalconID()) + "-" +
    // String.valueOf(id) + "-";
    // for (int i = 0; i < healthReport.fileName.size(); i++) {
    // String oldImageName = healthReport.fileName.get(i);
    // String newImageName = photo_id + oldImageName;
    // healthReport.fileName.set(i, newImageName);
    // }
    // }

}
