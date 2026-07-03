package com.automationexercise.pages;

import com.automationexercise.base.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(CheckoutPage.class);

    @FindBy(xpath = "//ul[@id='address_delivery']//li[@class='address_firstname address_lastname']")
    private WebElement deliveryName;

    @FindBy(xpath = "//ul[@id='address_invoice']//li[@class='address_firstname address_lastname']")
    private WebElement billingName;

    @FindBy(xpath = "//div[@id='ordertable']//tbody//tr")
    private java.util.List<WebElement> orderItems;

    @FindBy(xpath = "//p[@class='cart_total_price']")
    private WebElement totalAmount;

    @FindBy(name = "message")
    private WebElement commentTextArea;

    @FindBy(xpath = "//a[contains(@href,'payment')]")
    private WebElement placeOrderButton;

   public String getDeliveryName() {
        return getText(deliveryName);
    }
 public String getBillingName() {
        return getText(billingName);
    }

   public boolean isAddressDetailsVisible() {
        return isDisplayed(deliveryName) && isDisplayed(billingName);
    }

     public CheckoutPage enterComment(String comment) {
        logger.info("Entering comment: " + comment);
        type(commentTextArea, comment);
        return this;
    }

   public PaymentPage clickPlaceOrder() {
        logger.info("Clicking Place Order...");
        click(placeOrderButton);
        return new PaymentPage();
    }

   public String getTotalAmount() {
        return getText(totalAmount);
    }
}