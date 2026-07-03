package com.automationexercise.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;
    private static final String CONFIG_PATH = "src/main/resources/config.properties";

   private ConfigReader() {
    }

    static {
        try {
            FileInputStream fis = new FileInputStream(CONFIG_PATH);
            properties = new Properties();
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            throw new RuntimeException(
                    "❌ ERROR: Could not load config.properties from path: " + CONFIG_PATH
                            + "\n Please verify the file exists and path is correct.", e
            );
        }
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException(
                    "❌ Property '" + key + "' not found in config.properties"
            );
        }
        return value.trim();
    }

    public static String getBaseUrl() {
        return getProperty("base.url");
    }

    public static String getBrowser() {
        return getProperty("browser");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless"));
    }

    public static boolean isIncognito() {
        return Boolean.parseBoolean(getProperty("incognito"));
    }

    public static boolean isMaximize() {
        return Boolean.parseBoolean(getProperty("maximize"));
    }

    public static int getImplicitWait() {
        return Integer.parseInt(getProperty("implicit.wait"));
    }

    public static int getExplicitWait() {
        return Integer.parseInt(getProperty("explicit.wait"));
    }

    public static int getPageLoadTimeout() {
        return Integer.parseInt(getProperty("page.load.timeout"));
    }

    public static String getValidUserName() {
        return getProperty("valid.user.name");
    }

    public static String getValidUserEmail() {
        return getProperty("valid.user.email");
    }

    public static String getValidUserPassword() {
        return getProperty("valid.user.password");
    }

    public static String getReportPath() {
        return getProperty("report.path");
    }

    public static String getScreenshotPath() {
        return getProperty("screenshot.path");
    }
}