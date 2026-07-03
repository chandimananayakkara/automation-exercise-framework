package com.automationexercise.pages;

import com.automationexercise.base.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginSignupPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(LoginSignupPage.class);


    @FindBy(xpath = "//h2[contains(text(),'New User Signup!')]")
    private WebElement newUserSignupText;

    @FindBy(css = "input[data-qa='signup-name']")
    private WebElement signupNameInput;

    @FindBy(css = "input[data-qa='signup-email']")
    private WebElement signupEmailInput;

    @FindBy(css = "button[data-qa='signup-button']")
    private WebElement signupButton;

    @FindBy(xpath = "//p[contains(text(),'Email Address already exist!')]")
    private WebElement emailAlreadyExistError;

    @FindBy(xpath = "//h2[contains(text(),'Login to your account')]")
    private WebElement loginToAccountText;

    @FindBy(css = "input[data-qa='login-email']")
    private WebElement loginEmailInput;

    @FindBy(css = "input[data-qa='login-password']")
    private WebElement loginPasswordInput;

    @FindBy(css = "button[data-qa='login-button']")
    private WebElement loginButton;

    @FindBy(xpath = "//p[contains(text(),'Your email or password is incorrect!')]")
    private WebElement invalidLoginError;

    private void removeAds() {
        try {
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                    "var iframes = document.querySelectorAll('iframe');" +
                            "iframes.forEach(function(iframe) { iframe.remove(); });"
            );

            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                    "var ads = document.querySelectorAll(" +
                            "'ins.adsbygoogle, #google_ads_frame1, #google_ads_frame2, " +
                            "#google_ads_frame3, .adsbygoogle, [id^=google_ads], " +
                            "[id^=aswift], [class*=google-ad], #ad_position_box, " +
                            "#dismiss-button, .google-auto-placed');" +
                            "ads.forEach(function(ad) { ad.remove(); });"
            );
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                    "var overlays = document.querySelectorAll(" +
                            "'div[style*=\"position: fixed\"], div[style*=\"position:fixed\"], " +
                            "div[style*=\"z-index: 2147\"], div[style*=\"z-index:2147\"]');" +
                            "overlays.forEach(function(overlay) { " +
                            "  if(!overlay.querySelector('form')) { overlay.remove(); } " +
                            "});"
            );

            logger.info("🛡️ Removed ad overlays from page");
        } catch (Exception e) {
            logger.warn("Ad removal attempted: " + e.getMessage());
        }
    }

    public boolean isNewUserSignupVisible() {
        logger.info("Checking 'New User Signup!' visibility...");
        return isDisplayed(newUserSignupText);
    }

    public SignupDetailsPage signup(String name, String email) {
        logger.info("Signing up with name: " + name + ", email: " + email);

        String originalWindow = driver.getWindowHandle();

        type(signupNameInput, name);
        type(signupEmailInput, email);

        int maxAttempts = 3;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            logger.info("🔄 Signup attempt " + attempt + "/" + maxAttempts);

            removeAds();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }

            scrollToElement(signupButton);

            jsClick(signupButton);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException ignored) {
            }

            try {
                java.util.Set<String> allWindows = driver.getWindowHandles();
                if (allWindows.size() > 1) {
                    for (String window : allWindows) {
                        if (!window.equals(originalWindow)) {
                            driver.switchTo().window(window);
                            driver.close();
                            logger.info("🛡️ Closed ad tab (attempt " + attempt + ")");
                        }
                    }
                    driver.switchTo().window(originalWindow);
                }
            } catch (Exception e) {
                logger.warn("Window handling: " + e.getMessage());
            }

            String currentUrl = driver.getCurrentUrl();
            logger.info("📍 Current URL after attempt " + attempt + ": " + currentUrl);

            if (currentUrl.contains("/signup")) {
                logger.info("✅ Successfully navigated to signup page!");
                break;
            }

            try {
                boolean enterInfoVisible = driver.findElement(
                        By.xpath("//h2[contains(text(),'Enter Account Information')]")
                ).isDisplayed();

                if (enterInfoVisible) {
                    logger.info("✅ 'Enter Account Information' found!");
                    break;
                }
            } catch (Exception e) {
            }

            if (attempt < maxAttempts) {
                logger.info("⚠️ Signup page not reached. Retrying...");

                try {
                    if (isDisplayed(signupEmailInput)) {
                        signupEmailInput.clear();
                        signupEmailInput.sendKeys(email);
                    }
                } catch (Exception e) {
                    driver.get("https://automationexercise.com/login");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ignored) {
                    }
                    removeAds();
                    type(signupNameInput, name);
                    type(signupEmailInput, email);
                }
            }
        }

        return new SignupDetailsPage();
    }

    public void signupWithExistingEmail(String name, String email) {
        logger.info("Attempting signup with existing email: " + email);
        type(signupNameInput, name);
        type(signupEmailInput, email);

        removeAds();
        try {
            Thread.sleep(500);
        } catch (InterruptedException ignored) {
        }
        scrollToElement(signupButton);
        jsClick(signupButton);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {
        }
        handleAdWindows();
    }

    public String getEmailAlreadyExistError() {
        return getText(emailAlreadyExistError);
    }

    public boolean isEmailAlreadyExistErrorVisible() {
        return isDisplayed(emailAlreadyExistError);
    }

    public boolean isLoginToAccountVisible() {
        logger.info("Checking 'Login to your account' visibility...");
        return isDisplayed(loginToAccountText);
    }

    public HomePage login(String email, String password) {
        logger.info("Logging in with email: " + email);

        removeAds();
        type(loginEmailInput, email);
        type(loginPasswordInput, password);

        removeAds();
        scrollToElement(loginButton);
        jsClick(loginButton);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {
        }
        handleAdWindows();

        return new HomePage();
    }

    public void loginWithInvalidCredentials(String email, String password) {
        logger.info("Attempting login with invalid credentials...");

        removeAds();
        type(loginEmailInput, email);
        type(loginPasswordInput, password);

        removeAds();
        scrollToElement(loginButton);
        jsClick(loginButton);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {
        }
        handleAdWindows();
    }

    public String getInvalidLoginError() {
        return getText(invalidLoginError);
    }

    public boolean isInvalidLoginErrorVisible() {
        return isDisplayed(invalidLoginError);
    }
}