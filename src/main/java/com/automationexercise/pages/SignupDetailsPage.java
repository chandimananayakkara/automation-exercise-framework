package com.automationexercise.pages;

import com.automationexercise.base.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignupDetailsPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(SignupDetailsPage.class);


    @FindBy(xpath = "//h2[contains(text(),'Enter Account Information')]")
    private WebElement enterAccountInfoText;

    @FindBy(id = "id_gender1")
    private WebElement titleMr;

    @FindBy(id = "id_gender2")
    private WebElement titleMrs;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "days")
    private WebElement dayDropdown;

    @FindBy(id = "months")
    private WebElement monthDropdown;

    @FindBy(id = "years")
    private WebElement yearDropdown;

    @FindBy(id = "newsletter")
    private WebElement newsletterCheckbox;

    @FindBy(id = "optin")
    private WebElement specialOffersCheckbox;


    @FindBy(id = "first_name")
    private WebElement firstNameInput;

    @FindBy(id = "last_name")
    private WebElement lastNameInput;

    @FindBy(id = "company")
    private WebElement companyInput;

    @FindBy(id = "address1")
    private WebElement address1Input;

    @FindBy(id = "address2")
    private WebElement address2Input;

    @FindBy(id = "country")
    private WebElement countryDropdown;

    @FindBy(id = "state")
    private WebElement stateInput;

    @FindBy(id = "city")
    private WebElement cityInput;

    @FindBy(id = "zipcode")
    private WebElement zipcodeInput;

    @FindBy(id = "mobile_number")
    private WebElement mobileNumberInput;

    @FindBy(css = "button[data-qa='create-account']")
    private WebElement createAccountButton;

     public boolean isEnterAccountInfoVisible() {
        logger.info("Verifying 'Enter Account Information' text...");

        handleAdWindows();

        try {
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                    "var iframes = document.querySelectorAll('iframe');" +
                            "iframes.forEach(function(iframe) { iframe.remove(); });"
            );
        } catch (Exception e) {
        }

        try {
            org.openqa.selenium.support.ui.WebDriverWait longWait =
                    new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10));

            longWait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf(enterAccountInfoText));
            return true;
        } catch (Exception e) {
            String currentUrl = driver.getCurrentUrl();
            logger.info("📍 Fallback URL check: " + currentUrl);

            if (currentUrl.contains("/signup")) {
                try {
                    org.openqa.selenium.WebElement altText = driver.findElement(
                            org.openqa.selenium.By.xpath(
                                    "//b[contains(text(),'Enter Account Information')] | " +
                                            "//h2[contains(text(),'Enter Account Information')] | " +
                                            "//*[contains(text(),'ENTER ACCOUNT INFORMATION')]"
                            )
                    );
                    return altText.isDisplayed();
                } catch (Exception ex) {
                   try {
                        return driver.findElement(
                                org.openqa.selenium.By.id("password")
                        ).isDisplayed();
                    } catch (Exception finalEx) {
                        return false;
                    }
                }
            }
            return false;
        }
    }
public SignupDetailsPage selectTitle(String title) {
        if (title.equalsIgnoreCase("Mr")) {
            click(titleMr);
        } else {
            click(titleMrs);
        }
        return this;
    }

   public SignupDetailsPage fillAccountInfo(String password, String day,
                                             String month, String year) {
        logger.info("Filling account information...");
        type(passwordInput, password);
        selectByValue(dayDropdown, day);
        selectByVisibleText(monthDropdown, month);
        selectByValue(yearDropdown, year);
        return this;
    }
public SignupDetailsPage selectNewsletterAndOffers() {
        logger.info("Selecting newsletter and special offers...");
        jsClick(newsletterCheckbox);
        jsClick(specialOffersCheckbox);
        return this;
    }

    public SignupDetailsPage fillAddressInfo(String firstName, String lastName,
                                             String company, String address1,
                                             String address2, String country,
                                             String state, String city,
                                             String zipcode, String mobileNumber) {
        logger.info("Filling address information...");
        type(firstNameInput, firstName);
        type(lastNameInput, lastName);
        type(companyInput, company);
        type(address1Input, address1);
        type(address2Input, address2);
        selectByVisibleText(countryDropdown, country);
        type(stateInput, state);
        type(cityInput, city);
        type(zipcodeInput, zipcode);
        type(mobileNumberInput, mobileNumber);
        return this;
    }

    public AccountCreatedDeletedPage clickCreateAccount() {
        logger.info("Clicking Create Account button...");
        click(createAccountButton);
        return new AccountCreatedDeletedPage();
    }

    public AccountCreatedDeletedPage completeRegistration(
            String title, String password, String day, String month, String year,
            String firstName, String lastName, String company,
            String address1, String address2, String country,
            String state, String city, String zipcode, String mobileNumber) {

        selectTitle(title);
        fillAccountInfo(password, day, month, year);
        selectNewsletterAndOffers();
        fillAddressInfo(firstName, lastName, company, address1, address2,
                country, state, city, zipcode, mobileNumber);
        return clickCreateAccount();
    }
}