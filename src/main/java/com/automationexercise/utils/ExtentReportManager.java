package com.automationexercise.utils;

import com.automationexercise.config.ConfigReader;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager {

    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static ExtentReports initReports() {
        if (extent == null) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            String reportPath = System.getProperty("user.dir") + "/"
                    + ConfigReader.getReportPath()
                    + ConfigReader.getProperty("report.name")
                    + "_" + timestamp + ".html";

            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

            sparkReporter.config().setTheme(Theme.DARK);
            sparkReporter.config().setDocumentTitle("AutomationExercise - Test Report");
            sparkReporter.config().setReportName("E-Commerce Automation Test Results");
            sparkReporter.config().setTimelineEnabled(true);

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);

            extent.setSystemInfo("Application", "AutomationExercise.com");
            extent.setSystemInfo("Browser", ConfigReader.getBrowser());
            extent.setSystemInfo("Environment", ConfigReader.getProperty("environment"));
            extent.setSystemInfo("Tester", ConfigReader.getProperty("tester.name"));
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        }
        return extent;
    }

    public static void createTest(String testName) {
        ExtentTest extentTest = initReports().createTest(testName);
        test.set(extentTest);
    }

    public static ExtentTest getTest() {
        return test.get();
    }

     public static void flushReports() {
        if (extent != null) {
            extent.flush();
        }
    }
}