package com.automationexercise.pages;

import com.automationexercise.base.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PaymentPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(PaymentPage.class);

    @FindBy(name = "name_on_card")
    private WebElement nameOnCardInput;

    @FindBy(name = "card_number")
    private WebElement cardNumberInput;

    @FindBy(name = "cvc")
    private WebElement cvcInput;

    @FindBy(name = "expiry_month")
    private WebElement expiryMonthInput;

    @FindBy(name = "expiry_year")
    private WebElement expiryYearInput;

    @FindBy(id = "submit")
    private WebElement payAndConfirmButton;

    @FindBy(xpath = "//p[contains(text(),'Congratulations! Your order has been confirmed!')]")
    private WebElement orderConfirmationMessage;

    @FindBy(css = "a[data-qa='continue-button']")
    private WebElement continueButton;

    @FindBy(xpath = "//a[contains(text(),'Download Invoice')]")
    private WebElement downloadInvoiceButton;

    public PaymentPage fillPaymentDetails(String nameOnCard, String cardNumber,
                                          String cvc, String expiryMonth,
                                          String expiryYear) {
        logger.info("Filling payment details...");
        type(nameOnCardInput, nameOnCard);
        type(cardNumberInput, cardNumber);
        type(cvcInput, cvc);
        type(expiryMonthInput, expiryMonth);
        type(expiryYearInput, expiryYear);
        return this;
    }

    public PaymentPage clickPayAndConfirm() {
        logger.info("Clicking Pay and Confirm...");
        click(payAndConfirmButton);
        return this;
    }

    public boolean isOrderPlacedSuccessfully() {
        logger.info("Verifying order confirmation...");
        return isDisplayed(orderConfirmationMessage);
    }

    public String getOrderConfirmationMessage() {
        return getText(orderConfirmationMessage);
    }

    public PaymentPage clickDownloadInvoice() {
        logger.info("Clicking Download Invoice...");
        click(downloadInvoiceButton);
        return this;
    }

    public HomePage clickContinue() {
        logger.info("Clicking Continue...");
        click(continueButton);
        return new HomePage();
    }
}