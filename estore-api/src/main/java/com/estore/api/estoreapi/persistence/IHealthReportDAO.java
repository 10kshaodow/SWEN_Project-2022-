package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.HealthReport;

/**
 * Defines the interface for HealthReport object persistence
 * 
 * @author SWEN 05 Team 4D - Big Development
 * 
 */
public interface IHealthReportDAO {

    // define your functions here

    /**
     * Retrieves all HealthReports
     * 
     * @return An array of HealthReport objects, may be empty
     * 
     */
    HealthReport[] getAllHealthReports() throws IOException;


    /**
     * Retrieves a {@linkplain HealthReport healthReport} with the given id
     * 
     * @param id The id of the {@link HealthReport healthReport} to get
     * 
     * @return a {@link HealthReport healthReport} object with the matching id
     * <br>
     * null if no {@link HealthReport healthReport} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    HealthReport getHealthReport(int id) throws IOException;

    /**
     * Finds all healthReports whose properties contains the given text
     * 
     * @param containsText The text to match against
     * 
     * @return An array of healthReports whose properties contains the given text, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    HealthReport[] findHealthReports(String searchText) throws IOException;

     /**
     * Creates and saves a {@linkplain HealthReport healthReport}
     * 
     * @param healthReport {@linkplain HealthReport healthReport} object to be created and saved
     * <br>
     * The id of the healthReport object is ignored and a new uniqe id is assigned
     *
     * @return new {@link HealthReport healthReport} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     * @throws HealthReportNameTakenException
     * @throws InvalidHealthReportException
     */
    HealthReport createHealthReport(HealthReport healthReport) throws IOException;

    /**
     * Updates and saves a {@linkplain HealthReport healthReport}
     * 
     * @param {@link HealthReport healthReport} object to be updated and saved
     * 
     * @return updated {@link HealthReport healthReport} if successful, null if
     * {@link HealthReport healthReport} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    HealthReport updateHealthReport(HealthReport healthReport) throws IOException;


     /**
     * Deletes a healthReport with the given id
     * 
     * @param id The id of the {@link HealthReport healthReport}
     * 
     * @return true if the {@link HealthReport healthReport} was deleted
     * <br>
     * false if HealthReport with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteHealthReport(int id) throws IOException;



}
