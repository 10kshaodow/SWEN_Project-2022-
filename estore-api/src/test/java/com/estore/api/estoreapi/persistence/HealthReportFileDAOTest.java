package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.HealthReport;
import com.estore.api.estoreapi.model.HealthReport.DietStatus;
import com.estore.api.estoreapi.model.HealthReport.FitnessStatus;
import com.estore.api.estoreapi.model.HealthReport.SocialStatus;
import com.estore.api.estoreapi.model.HealthStatus;
import com.estore.api.estoreapi.model.ImageSource;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HealthReportFileDAOTest {
    HealthReportFileDAO healthReportFileDAO;
    HealthReportFileDAO mockHealthReportFileDao;
    HealthReport existingHealthReport;
    HealthReport testHealthReport;
    int falconID = 2;

    @BeforeEach
    void setup() throws IOException {
        this.healthReportFileDAO = new HealthReportFileDAO("data/testHealthReports.json", new ObjectMapper());
        HealthReport[] healthReports = healthReportFileDAO.getAllHealthReports();
        for (int i = 0; i < healthReports.length; i++) {
            healthReportFileDAO.deleteHealthReport(healthReports[i].getReportID());
        }
        int reportID = 0;
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

        this.existingHealthReport = new HealthReport(reportID, falconID, reportDate, healthStatus, healthDescription,
                keyNotes, diet, fitness, social, reproductiveStatus, fileNameList, fileSourceList);
        this.existingHealthReport = healthReportFileDAO.createHealthReport(this.existingHealthReport);
        this.testHealthReport = new HealthReport(reportID, falconID, reportDate, healthStatus, "New Test Health Report",
                keyNotes, diet, fitness, social, reproductiveStatus, fileNameList, fileSourceList);
    }

    @Test
    public void testGetHealthReportByFalconId() {
        try {
            HealthReport[] reports = healthReportFileDAO.findHealthReports("1");

            for (HealthReport report : reports) {
                assertEquals(1, report.getFalconID());
            }

        } catch (Exception e) {
            assert (false);
        }
    }

    @Test
    void testCreateDeleteHealthReport() {
        // creating a new product
        try {
            HealthReport healthReport = healthReportFileDAO.createHealthReport(testHealthReport);
            assert (true);
        } catch (IOException e) {
            assert (false);
        }

        // deleting a product that doesn't exist
        try {
            assertFalse(healthReportFileDAO.deleteHealthReport(99));
            assertTrue(true);
        } catch (IOException e) {
            assert (false);
        }

        // deleting testHealthReport, created above
        try {
            assertNotNull(healthReportFileDAO.deleteHealthReport(testHealthReport.getReportID()));
        } catch (IOException e) {
            assert (false);
        }
    }

    @Test
    void testGetAllHealthReports() {
        // getting all products
        try {
            assert (healthReportFileDAO.getAllHealthReports() != null);
        } catch (IOException e) {
            assert (false);
        }

        // searching for products
        assertNotNull(healthReportFileDAO.getHealthReportsArray(falconID));
        assertNotNull(healthReportFileDAO.getHealthReportsArray());
    }

    @Test
    void testGetHealthReport() {
        assertEquals(this.existingHealthReport.toString(),
                healthReportFileDAO.getHealthReport(this.existingHealthReport.getReportID()).toString());
        assert (healthReportFileDAO.getHealthReport(99) == null);
    }

    @Test
    void testUpdateHealthReport() {
        try {
            assertEquals(healthReportFileDAO.updateHealthReport(existingHealthReport), existingHealthReport);
        } catch (IOException e) {
            assert (false);
        }

        try {
            assert (healthReportFileDAO.updateHealthReport(testHealthReport) == null);
        } catch (IOException e) {
            assert (false);
        }
    }

    @AfterAll
    public static void resetData() {
        try {
            FileInputStream in = new FileInputStream(new File("data/testHealthReportsCopy.json"));
            FileOutputStream out = new FileOutputStream(new File("data/testHealthReports.json"));
            try {
                int n;
                try {
                    while ((n = in.read()) != -1) {
                        out.write(n);
                    }
                } catch (IOException e) {
                    // do nothing
                }
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    // do nothing
                }
            }
        } catch (FileNotFoundException e) {
            // do nothing data needs to be reset somehow tho.
        }
    }
}
