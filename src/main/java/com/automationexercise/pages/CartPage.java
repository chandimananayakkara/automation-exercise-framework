package com.automationexercise.pages;

import com.automationexercise.base.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CartPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(CartPage.class);

    @FindBy(id = "cart_info_table")
    private WebElement cartTable;

    @FindBy(xpath = "//tbody/tr")
    private List<WebElement> cartItems;

    @FindBy(xpath = "//td[@class='cart_price']//p")
    private List<WebElement> itemPrices;

    @FindBy(xpath = "//td[@class='cart_quantity']//button")
    private List<WebElement> itemQuantities;

    @FindBy(xpath = "//td[@class='cart_total']//p")
    private List<WebElement> itemTotals;

    @FindBy(xpath = "//td[@class='cart_description']//h4//a")
    private List<WebElement> itemNames;

    @FindBy(xpath = "//a[contains(@class,'check_out')]")
    private WebElement proceedToCheckoutButton;

    @FindBy(xpath = "//u[contains(text(),'Register / Login')]")
    private WebElement registerLoginLink;

    @FindBy(xpath = "//td[@class='cart_delete']//a")
    private List<WebElement> deleteButtons;

    @FindBy(id = "empty_cart")
    private WebElement emptyCartMessage;

    public int getCartItemCount() {
        return cartItems.size();
    }

    public List<String> getCartItemNames() {
        return itemNames.stream()
                .map(WebElement::getText)
                .toList();
    }

    public String getItemPrice(int index) {
        return getText(itemPrices.get(index));
    }

    public String getItemQuantity(int index) {
        return getText(itemQuantities.get(index));
    }

    public String getItemTotal(int index) {
        return getText(itemTotals.get(index));
    }

    public boolean isProductInCart(String productName) {
        return getCartItemNames().stream()
                .anyMatch(name -> name.contains(productName));
    }

    public CartPage removeItem(int index) {
        logger.info("Removing cart item at index: " + index);
        click(deleteButtons.get(index));
        return this;
    }

    public CheckoutPage clickProceedToCheckout() {
        logger.info("Clicking Proceed To Checkout...");
        scrollToElement(proceedToCheckoutButton);
        click(proceedToCheckoutButton);
        return new CheckoutPage();
    }

    public LoginSignupPage clickCheckoutThenRegisterLogin() {
        logger.info("Clicking Proceed To Checkout (not logged in)...");
        scrollToElement(proceedToCheckoutButton);
        click(proceedToCheckoutButton);

        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

        logger.info("Clicking Register/Login link in modal...");
        try {
            click(registerLoginLink);
        } catch (Exception e) {
            jsClick(registerLoginLink);
        }
        return new LoginSignupPage();
    }

    public boolean isCartEmpty() {
        try {
            return cartItems.isEmpty() || isDisplayed(emptyCartMessage);
        } catch (Exception e) {
            return true;
        }
    }

    public boolean isCartPageDisplayed() {
        return isDisplayed(cartTable);
    }

    public LoginSignupPage clickRegisterLogin() {
        return clickCheckoutThenRegisterLogin();
    }
}