package com.automationexercise.utils;

import com.automationexercise.base.DriverFactory;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

     public static String captureScreenshot(String testName) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String fileName = testName + "_" + timestamp + ".png";
        String filePath = System.getProperty("user.dir") + "/screenshots/" + fileName;

        try {
            WebDriver driver = DriverFactory.getDriver();
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            File destination = new File(filePath);
            FileUtils.copyFile(source, destination);

            System.out.println("📸 Screenshot saved: " + filePath);

        } catch (IOException e) {
            System.out.println("❌ Failed to capture screenshot: " + e.getMessage());
        }

        return filePath;
    }

      public static String getScreenshotAsBase64() {
        WebDriver driver = DriverFactory.getDriver();
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
    }
}