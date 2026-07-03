package com.automationexercise.pages;

import com.automationexercise.base.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductDetailPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(ProductDetailPage.class);


    @FindBy(xpath = "//div[@class='product-information']//h2")
    private WebElement productName;

    @FindBy(xpath = "//div[@class='product-information']/p[contains(.,'Category')]")
    private WebElement productCategory;

    @FindBy(xpath = "//div[@class='product-information']/span/span")
    private WebElement productPrice;

    @FindBy(xpath = "//div[@class='product-information']/p[contains(.,'Availability')]")
    private WebElement productAvailability;

    @FindBy(xpath = "//div[@class='product-information']/p[contains(.,'Condition')]")
    private WebElement productCondition;

    @FindBy(xpath = "//div[@class='product-information']/p[contains(.,'Brand')]")
    private WebElement productBrand;

    @FindBy(id = "quantity")
    private WebElement quantityInput;

    @FindBy(xpath = "//button[contains(@class,'cart')]")
    private WebElement addToCartButton;

    @FindBy(id = "name")
    private WebElement reviewNameInput;

    @FindBy(id = "email")
    private WebElement reviewEmailInput;

    @FindBy(id = "review")
    private WebElement reviewTextArea;

    @FindBy(id = "button-review")
    private WebElement submitReviewButton;

    @FindBy(xpath = "//span[contains(text(),'Thank you for your review')]")
    private WebElement reviewSuccessMessage;


      public boolean isProductDetailVisible() {
        logger.info("Verifying product detail page...");

        handleAdWindows();

        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

        try {
            boolean nameVisible = isDisplayed(productName);
            boolean priceVisible = isDisplayed(productPrice);

            logger.info("Product name visible: " + nameVisible);
            logger.info("Product price visible: " + priceVisible);

            return nameVisible && priceVisible;
        } catch (Exception e) {
            logger.error("Product detail check failed: " + e.getMessage());
            return false;
        }
    }

    public String getProductName() {
        return getText(productName);
    }

    public String getProductCategory() {
        return getText(productCategory);
    }

    public String getProductPrice() {
        return getText(productPrice);
    }

    public String getProductAvailability() {
        return getText(productAvailability);
    }

    public String getProductCondition() {
        return getText(productCondition);
    }

    public String getProductBrand() {
        return getText(productBrand);
    }

    public ProductDetailPage setQuantity(String quantity) {
        logger.info("Setting quantity to: " + quantity);
        quantityInput.clear();
        type(quantityInput, quantity);
        return this;
    }

    public ProductDetailPage clickAddToCart() {
        logger.info("Clicking Add to Cart...");
        click(addToCartButton);
        return this;
    }

    public ProductDetailPage writeReview(String name, String email, String review) {
        logger.info("Writing product review...");
        scrollToElement(reviewNameInput);
        type(reviewNameInput, name);
        type(reviewEmailInput, email);
        type(reviewTextArea, review);
        click(submitReviewButton);
        return this;
    }

    public boolean isReviewSuccessVisible() {
        return isDisplayed(reviewSuccessMessage);
    }
}