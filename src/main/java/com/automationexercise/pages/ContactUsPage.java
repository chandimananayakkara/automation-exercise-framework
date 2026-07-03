package com.automationexercise.pages;

import com.automationexercise.base.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ContactUsPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(ContactUsPage.class);

    @FindBy(xpath = "//h2[contains(text(),'Get In Touch')]")
    private WebElement getInTouchTitle;

    @FindBy(css = "input[data-qa='name']")
    private WebElement nameInput;

    @FindBy(css = "input[data-qa='email']")
    private WebElement emailInput;

    @FindBy(css = "input[data-qa='subject']")
    private WebElement subjectInput;

    @FindBy(id = "message")
    private WebElement messageInput;

    @FindBy(name = "upload_file")
    private WebElement uploadFileInput;

    @FindBy(css = "input[data-qa='submit-button']")
    private WebElement submitButton;

    @FindBy(xpath = "//div[contains(@class,'alert-success')]")
    private WebElement successMessage;

    @FindBy(xpath = "//a[contains(text(),'Home')]")
    private WebElement homeButton;

    public boolean isGetInTouchVisible() {
        return isDisplayed(getInTouchTitle);
    }

    public ContactUsPage fillContactForm(String name, String email,
                                         String subject, String message) {
        logger.info("Filling contact form...");
        type(nameInput, name);
        type(emailInput, email);
        type(subjectInput, subject);
        type(messageInput, message);
        return this;
    }

    public ContactUsPage uploadFile(String filePath) {
        logger.info("Uploading file: " + filePath);
        uploadFileInput.sendKeys(filePath);
        return this;
    }

    public ContactUsPage clickSubmit() {
        logger.info("Clicking Submit...");
        click(submitButton);
        try {
            acceptAlert();
        } catch (Exception e) {
            logger.warn("No alert present");
        }
        return this;
    }

    public boolean isSuccessMessageVisible() {
        return isDisplayed(successMessage);
    }

    public HomePage clickHome() {
        click(homeButton);
        return new HomePage();
    }
}