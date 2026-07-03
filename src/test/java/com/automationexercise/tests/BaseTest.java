package com.automationexercise.tests;

import com.automationexercise.base.DriverFactory;
import com.automationexercise.config.ConfigReader;
import com.automationexercise.utils.TestListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

@Listeners(TestListener.class)
public class BaseTest {

    protected WebDriver driver;
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);

    @BeforeMethod
    public void setUp() {
        logger.info("═══════════════════════════════════════");
        logger.info("🚀 Setting up browser...");

       DriverFactory.initDriver();
        driver = DriverFactory.getDriver();

        driver.get(ConfigReader.getBaseUrl());
        logger.info("📍 Navigated to: " + ConfigReader.getBaseUrl());

       try { Thread.sleep(3000); } catch (InterruptedException ignored) {}

         try {
            String originalWindow = driver.getWindowHandle();
            java.util.Set<String> allWindows = driver.getWindowHandles();
            if (allWindows.size() > 1) {
                for (String window : allWindows) {
                    if (!window.equals(originalWindow)) {
                        driver.switchTo().window(window);
                        driver.close();
                        logger.info("🛡️ Closed initial ad tab");
                    }
                }
                driver.switchTo().window(originalWindow);
            }
        } catch (Exception e) {
            logger.warn("Initial ad handling: " + e.getMessage());
        }

        logger.info("═══════════════════════════════════════");
    }

    @AfterMethod
    public void tearDown() {
        logger.info("🧹 Tearing down browser...");
        DriverFactory.quitDriver();
        logger.info("✅ Browser closed successfully");
    }
}