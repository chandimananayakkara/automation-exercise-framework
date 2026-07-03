package com.automationexercise.base;

import com.automationexercise.config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initDriver() {
        String browser = ConfigReader.getBrowser().toLowerCase();

        switch (browser) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();

                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--disable-popup-blocking");
                chromeOptions.addArguments("--disable-infobars");
                chromeOptions.addArguments("--disable-extensions");

                chromeOptions.addArguments("--host-resolver-rules=MAP googleads.g.doubleclick.net 127.0.0.1, MAP pagead2.googlesyndication.com 127.0.0.1, MAP tpc.googlesyndication.com 127.0.0.1, MAP googleadservices.com 127.0.0.1");

                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");

                chromeOptions.addArguments("--disable-features=PreloadMediaEngagementData,MediaEngagementBypassAutoplayPolicies");
                chromeOptions.setExperimentalOption("excludeSwitches",
                        java.util.Arrays.asList("enable-automation"));

                java.util.Map<String, Object> prefs = new java.util.HashMap<>();
                prefs.put("profile.default_content_setting_values.popups", 2);
                prefs.put("profile.default_content_setting_values.notifications", 2);
                prefs.put("profile.default_content_setting_values.ads", 2);
                chromeOptions.setExperimentalOption("prefs", prefs);

                if (ConfigReader.isHeadless()) {
                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--window-size=1920,1080");
                }
                if (ConfigReader.isIncognito()) {
                    chromeOptions.addArguments("--incognito");
                }

                driver.set(new ChromeDriver(chromeOptions));
                break;

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (ConfigReader.isHeadless()) {
                    firefoxOptions.addArguments("--headless");
                }
                firefoxOptions.addPreference("dom.webnotifications.enabled", false);
                driver.set(new FirefoxDriver(firefoxOptions));
                break;

            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                if (ConfigReader.isHeadless()) {
                    edgeOptions.addArguments("--headless=new");
                }
                edgeOptions.addArguments("--disable-notifications");
                driver.set(new EdgeDriver(edgeOptions));
                break;

            default:
                throw new RuntimeException(
                        "❌ Invalid browser: '" + browser + "' in config.properties. "
                                + "Supported browsers: chrome, firefox, edge"
                );
        }

        WebDriver webDriver = getDriver();

        webDriver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(ConfigReader.getImplicitWait())
        );

        webDriver.manage().timeouts().pageLoadTimeout(
                Duration.ofSeconds(ConfigReader.getPageLoadTimeout())
        );

        if (ConfigReader.isMaximize()) {
            webDriver.manage().window().maximize();
        }
    }

    public static WebDriver getDriver() {
        WebDriver webDriver = driver.get();
        if (webDriver == null) {
            throw new RuntimeException(
                    "❌ WebDriver is null! Call DriverFactory.initDriver() first."
            );
        }
        return webDriver;
    }

    public static void quitDriver() {
        WebDriver webDriver = driver.get();
        if (webDriver != null) {
            webDriver.quit();
            driver.remove(); // Prevent memory leak!
        }
    }
}