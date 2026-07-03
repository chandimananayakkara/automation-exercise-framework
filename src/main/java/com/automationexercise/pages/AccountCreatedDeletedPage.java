package com.automationexercise.pages;

import com.automationexercise.base.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountCreatedDeletedPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(AccountCreatedDeletedPage.class);

    @FindBy(css = "h2[data-qa='account-created']")
    private WebElement accountCreatedText;

    @FindBy(css = "h2[data-qa='account-deleted']")
    private WebElement accountDeletedText;

    @FindBy(css = "a[data-qa='continue-button']")
    private WebElement continueButton;

    public boolean isAccountCreatedVisible() {
        logger.info("Verifying 'ACCOUNT CREATED!' text...");
        return isDisplayed(accountCreatedText);
    }

     public String getAccountCreatedText() {
        return getText(accountCreatedText);
    }

    public boolean isAccountDeletedVisible() {
        logger.info("Verifying 'ACCOUNT DELETED!' text...");
        return isDisplayed(accountDeletedText);
    }
public HomePage clickContinue() {
        logger.info("Clicking Continue button...");
        click(continueButton);
        return new HomePage();
    }
}