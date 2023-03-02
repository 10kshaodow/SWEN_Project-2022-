package com.estore.api.estoreapi.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.filechooser.FileNameExtensionFilter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Product
 * 
 * @author SWEN 05 Team 4D - Big Development
 * 
 */
public class HealthReport {

    public static final String HEALTH_REPORT_STRING = "\nHealth Report {reportID=%d, falconID=%d, healthstatus=%s, health description=%s, diet=%s, fitness=%s, social=%s, reproducive status = %s, fileName=[%s], fileSource=[%s]}\n";
    @JsonProperty("reportID")
    public int reportID;
    @JsonProperty("falconID")
    public int falconID;
    @JsonProperty("reportDate")
    public Date reportDate;
    @JsonProperty("healthStatus")
    public HealthStatus healthStatus;
    @JsonProperty("healthDescription")
    public String healthDescription;
    @JsonProperty("keyNotes")
    public ArrayList<String> keyNotes = new ArrayList<String>();
    @JsonProperty("diet")
    public DietStatus diet;
    @JsonProperty("fitness")
    public FitnessStatus fitness;
    @JsonProperty("social")
    public SocialStatus social;
    @JsonProperty("reproductiveStatus")
    public boolean reproductiveStatus;
    @JsonProperty("fileName")
    public ArrayList<String> fileName = new ArrayList<String>();
    @JsonProperty("fileSource")
    public ArrayList<ImageSource> fileSource = new ArrayList<ImageSource>();

    
    
    public HealthReport(@JsonProperty("reportID") int reportID, @JsonProperty("falconID") int falconID, @JsonProperty("reportDate") Date reportDate, @JsonProperty("healthStatus") HealthStatus healthStatus, @JsonProperty("healthDescription") String healthDescription, @JsonProperty("keyNotes") ArrayList<String> keyNotes, @JsonProperty("diet") DietStatus diet,
    @JsonProperty("fitness") FitnessStatus fitness, @JsonProperty("social") SocialStatus social, @JsonProperty("reproductiveStatus") boolean reproductiveStatus, @JsonProperty("fileName") ArrayList<String> fileName, @JsonProperty("fileSource") ArrayList<ImageSource> fileSource) {
        this.reportID = reportID;
        this.falconID = falconID;
        this.reportDate = reportDate;
        this.healthStatus = healthStatus;
        this.healthDescription = healthDescription;
        this.keyNotes = keyNotes;
        this.diet = diet;
        this.fitness = fitness;
        this.social = social;
        this.reproductiveStatus = reproductiveStatus;
        this.fileName = fileName;
        this.fileSource = fileSource;
    }



    public int getReportID() {
        return reportID;
    }



    public void setReportID(int reportID) {
        this.reportID = reportID;
    }



    public int getFalconID() {
        return falconID;
    }



    public void setFalconID(int falconID) {
        this.falconID = falconID;
    }


    
    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }



    
    public HealthStatus getHealthStatus() {
        return healthStatus;
    }



    public void setHealthStatus(HealthStatus healthStatus) {
        this.healthStatus = healthStatus;
    }



    public String getHealthDescription() {
        return healthDescription;
    }



    public void setHealthDescription(String healthDescription) {
        this.healthDescription = healthDescription;
    }

    



    public ArrayList<String> getKeyNotes() {
        return keyNotes;
    }


    public void setKeyNotes(ArrayList<String> keyNotes) {
        this.keyNotes = keyNotes;
    }



    public DietStatus getDiet() {
        return diet;
    }



    public void setDiet(DietStatus diet) {
        this.diet = diet;
    }



    public FitnessStatus getFitness() {
        return fitness;
    }



    public void setFitness(FitnessStatus fitness) {
        this.fitness = fitness;
    }



    public SocialStatus getSocial() {
        return social;
    }


    public void setSocial(SocialStatus social) {
        this.social = social;
    }

    public boolean getReproductiveStatus(){
        return reproductiveStatus;
    }

    public void setReproductiveStatus(boolean reproductiveStatus){
        this.reproductiveStatus = reproductiveStatus;
    }

    /**
     * Sets the fileName of the product - necessary for JSON object to Java object
     * deserialization
     * 
     * @param fileName The fileName of the product
     */
    public void setFileName(ArrayList<String> fileName) {
        if(fileName != null)
            this.fileName = fileName;
        else
            this.fileName = new ArrayList<String>();
    }

    /**
     * Sets the fileSource of the product - necessary for JSON object to Java object
     * deserialization
     * 
     * @param fileSource The fileSource of the product
     */
    public void setFileSource(ArrayList<ImageSource> fileSource) {
        if(fileSource != null)
            this.fileSource = fileSource;
        else
            this.fileSource = new ArrayList<ImageSource>();
    }

    /**
     * Retrieves the filename of the product
     * 
     * @return The filename of the product
     */
    public ArrayList<String> getFileName() {
        return this.fileName;
    }

    /**
     * Retrieves the file source object of the product
     * 
     * @return The file source object of the product
     */
    public ArrayList<ImageSource> getFileSource() {
        return this.fileSource;
    }

    /**
     * See if the product has a new fileSource
     * 
     * @return True if it does false if not
     */
    public Boolean hasNewImages() {
        if (this.fileSource.size() > 0)
            return true;
        return false;
    }

    /**
     * See if the product has a old fileName saved
     * 
     * @return True if it does false if not
     */
    public Boolean hasOldImages() {
        if (this.fileName.size() == 0)
            return false;
        return true;
    }

    /**
     * Turn the file name into a url for the front end
     */
    public void normalize() {

        for(int i = 0; i<this.fileName.size();i++){
            String fileName = this.fileName.get(i);
            if (this.fileName == null)
                continue;
            String[] split = fileName.split("/");

            if (split.length > 1)
                continue;

            String pureFileName = fileName;
            String fullPath = "http://localhost:8080/static/" + pureFileName;
            this.fileName.set(i, fullPath);
        }

    }


    /**
     * Turn the filename from a url into the actual files name
     */
    public void unNormalize() {

        for(int i = 0; i<this.fileName.size();i++){
            String fileName = this.fileName.get(i);
            if (fileName == null)
                continue;

            String[] split = fileName.split("/");

            if (split.length <= 1)
                continue;

            String pureFileName = fileName.replace("http://localhost:8080/static/", "");

            this.fileName.set(i, pureFileName);
        }

    }

    public static String fileNameToString(ArrayList<String> fileNameList){
        return String.join(",", fileNameList);
    }
    
    public static String fileSourceToString(ArrayList<ImageSource> fileSourceList){
        ArrayList<String> fileSourceToStringList = new ArrayList<String>();
        for(int i = 0; i<fileSourceList.size();i++){
            fileSourceToStringList.add(fileSourceList.get(i).toString());
        }
        
        return String.join(",", fileSourceToStringList);
    }

    @Override
    public String toString() {
        return String.format(HEALTH_REPORT_STRING, this.reportID, this.falconID, this.healthStatus.toString(), this.healthDescription, this.diet, this.fitness, this.social, this.reproductiveStatus, HealthReport.fileNameToString(this.fileName), HealthReport.fileSourceToString(this.fileSource));
    }

    public enum DietStatus{
        @JsonProperty("Nutritional")
        Nutritional("Nutritional"),
        @JsonProperty("Balanced")
        Balanced("Balanced"),
        @JsonProperty("Junk")
        Junk("Junk");

    
        private String status;
    
        private DietStatus(String status) {
            this.status = status;
        }
    
        @Override
        public String toString() {
            return this.status;
        }
    }

    public enum FitnessStatus{
        @JsonProperty("Active")
        Active("Active"),
        @JsonProperty("Average")
        Average("Average"),
        @JsonProperty("Dormant")
        Dormant("Dormant");
    
        private String status;
    
        private FitnessStatus(String status) {
            this.status = status;
        }
    
        @Override
        public String toString() {
            return this.status;
        }
    }

    public enum SocialStatus{
        @JsonProperty("Lively")
        Lively("Lively"),
        @JsonProperty("Rowdy")
        Rowdy("Rowdy"),
        @JsonProperty("Reserved")
        Reserved("Reserved");
    
        private String status;
    
        private SocialStatus(String status) {
            this.status = status;
        }
    
        @Override
        public String toString() {
            return this.status;
        }
    }
}
