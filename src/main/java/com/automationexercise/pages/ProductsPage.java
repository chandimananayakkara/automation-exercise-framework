package com.automationexercise.pages;

import com.automationexercise.base.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ProductsPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(ProductsPage.class);

    @FindBy(xpath = "//h2[@class='title text-center' and contains(text(),'All Products')]")
    private WebElement allProductsTitle;

    @FindBy(xpath = "//h2[@class='title text-center' and contains(text(),'Searched Products')]")
    private WebElement searchedProductsTitle;

    @FindBy(id = "search_product")
    private WebElement searchInput;

    @FindBy(id = "submit_search")
    private WebElement searchButton;

    @FindBy(xpath = "//div[@class='features_items']//div[@class='col-sm-4']")
    private List<WebElement> productCards;

    @FindBy(xpath = "//div[@class='features_items']//div[@class='productinfo text-center']//p")
    private List<WebElement> productNames;

    @FindBy(xpath = "//a[@class='btn btn-default add-to-cart']")
    private List<WebElement> addToCartButtons;

    @FindBy(xpath = "//div[@class='features_items']//a[contains(@href,'product_details')]")
    private List<WebElement> viewProductLinks;

     @FindBy(xpath = "//button[contains(text(),'Continue Shopping')]")
    private WebElement continueShoppingButton;

    @FindBy(xpath = "//u[contains(text(),'View Cart')]")
    private WebElement viewCartLink;

    @FindBy(xpath = "//div[@class='brands_products']//ul//li//a")
    private List<WebElement> brandLinks;

  public boolean isAllProductsPageVisible() {
        logger.info("Verifying All Products page...");
        return isDisplayed(allProductsTitle);
    }
 public boolean isProductListVisible() {
        return productCards.size() > 0;
    }
public int getProductCount() {
        return productCards.size();
    }

    public ProductsPage searchProduct(String productName) {
        logger.info("Searching for product: " + productName);
        type(searchInput, productName);
        click(searchButton);
        return this;
    }
public boolean isSearchedProductsVisible() {
        return isDisplayed(searchedProductsTitle);
    }
public List<String> getAllProductNames() {
        return productNames.stream()
                .map(WebElement::getText)
                .toList();
    }

      public ProductDetailPage clickViewFirstProduct() {
        logger.info("Clicking View Product for first product...");

        String originalWindow = driver.getWindowHandle();

        if (!viewProductLinks.isEmpty()) {
            scrollToElement(viewProductLinks.get(0));

            try {
                click(viewProductLinks.get(0));
            } catch (Exception e) {
                jsClick(viewProductLinks.get(0));
            }

            try { Thread.sleep(3000); } catch (InterruptedException ignored) {}

            try {
                java.util.Set<String> allWindows = driver.getWindowHandles();
                if (allWindows.size() > 1) {
                    for (String window : allWindows) {
                        if (!window.equals(originalWindow)) {
                            driver.switchTo().window(window);
                            driver.close();
                            logger.info("🛡️ Closed ad tab");
                        }
                    }
                    driver.switchTo().window(originalWindow);
                }
            } catch (Exception e) {
                logger.warn("Ad handling: " + e.getMessage());
            }
        }
        return new ProductDetailPage();
    }

    public ProductDetailPage clickViewProduct(int index) {
        logger.info("Clicking View Product at index: " + index);
        scrollToElement(viewProductLinks.get(index));
        click(viewProductLinks.get(index));
        return new ProductDetailPage();
    }

    public ProductsPage addProductToCart(int index) {
        logger.info("Adding product at index " + index + " to cart...");
        WebElement product = productCards.get(index);
        scrollToElement(product);
        hoverOver(product);

        List<WebElement> addButtons = product.findElements(
                By.xpath(".//a[contains(@class,'add-to-cart')]"));
        if (!addButtons.isEmpty()) {
            jsClick(addButtons.get(0));
        }
        return this;
    }

   public ProductsPage clickContinueShopping() {
        logger.info("Clicking Continue Shopping...");
        waitForVisibility(continueShoppingButton);
        click(continueShoppingButton);
        return this;
    }

   public CartPage clickViewCart() {
        logger.info("Clicking View Cart...");
        click(viewCartLink);
        return new CartPage();
    }

   public ProductsPage addMultipleProductsToCart(int... indices) {
        for (int index : indices) {
            addProductToCart(index);
            if (index < indices[indices.length - 1]) {
                clickContinueShopping();
            }
        }
        return this;
    }

    public boolean doSearchResultsContain(String searchText) {
        List<String> names = getAllProductNames();
        return names.stream()
                .anyMatch(name -> name.toLowerCase().contains(searchText.toLowerCase()));
    }
}