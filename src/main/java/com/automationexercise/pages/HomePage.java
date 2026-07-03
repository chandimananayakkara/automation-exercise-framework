package com.automationexercise.pages;

import com.automationexercise.base.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    private static final Logger logger = LogManager.getLogger(HomePage.class);

    @FindBy(css = "a[href='/login']")
    private WebElement signupLoginLink;

    @FindBy(css = "a[href='/products']")
    private WebElement productsLink;

    @FindBy(css = "a[href='/view_cart']")
    private WebElement cartLink;

    @FindBy(css = "a[href='/contact_us']")
    private WebElement contactUsLink;

    @FindBy(css = "a[href='/test_cases']")
    private WebElement testCasesLink;

    @FindBy(css = "a[href='/api_list']")
    private WebElement apiTestingLink;

    @FindBy(xpath = "//a[contains(text(),'Logged in as')]")
    private WebElement loggedInAsText;

    @FindBy(css = "a[href='/delete_account']")
    private WebElement deleteAccountLink;

    @FindBy(css = "a[href='/logout']")
    private WebElement logoutLink;

    @FindBy(id = "susbscribe_email")
    private WebElement subscriptionEmailInput;

    @FindBy(id = "subscribe")
    private WebElement subscribeButton;

    @FindBy(xpath = "//div[@class='item active']//h2")
    private WebElement heroSliderText;

    @FindBy(xpath = "//h2[contains(text(),'Subscription')]")
    private WebElement subscriptionText;

    @FindBy(id = "scrollUp")
    private WebElement scrollUpButton;

    @FindBy(xpath = "//div[@class='features_items']//h2[@class='title text-center']")
    private WebElement featuresItemsTitle;

    @FindBy(xpath = "//div[@class='recommended_items']")
    private WebElement recommendedItems;

    @FindBy(xpath = "//a[contains(@href, 'category_products/1')]")
    private WebElement womenCategoryDressLink;

    @FindBy(xpath = "//div[@class='left-sidebar']//h2")
    private WebElement categoryTitle;

    public boolean isHomePageVisible() {
        logger.info("Verifying home page is visible...");
        try {
            return isDisplayed(heroSliderText) || getCurrentUrl().contains("automationexercise");
        } catch (Exception e) {
            // Fallback: check URL
            return getCurrentUrl().contains("automationexercise");
        }
    }

      public LoginSignupPage clickSignupLogin() {
        logger.info("Clicking Signup/Login link...");

        try {
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                    "var iframes = document.querySelectorAll('iframe');" +
                            "iframes.forEach(function(iframe) { iframe.remove(); });" +
                            "var ads = document.querySelectorAll('ins.adsbygoogle, .adsbygoogle');" +
                            "ads.forEach(function(ad) { ad.remove(); });"
            );
        } catch (Exception e) {
        }

        clickAndHandleAds(signupLoginLink);
        return new LoginSignupPage();
    }

    public ProductsPage clickProducts() {
        logger.info("Clicking Products link...");
        clickAndHandleAds(productsLink);
        return new ProductsPage();
    }

    public CartPage clickCart() {
        logger.info("Clicking Cart link...");
        clickAndHandleAds(cartLink);
        return new CartPage();
    }

    public ContactUsPage clickContactUs() {
        logger.info("Clicking Contact Us link...");
        clickAndHandleAds(contactUsLink);
        return new ContactUsPage();
    }

    public String getLoggedInAsText() {
        logger.info("Getting logged in text...");
        return getText(loggedInAsText);
    }

    public boolean isLoggedIn() {
        try {
            return isDisplayed(loggedInAsText);
        } catch (Exception e) {
            return false;
        }
    }

    public LoginSignupPage clickLogout() {
        logger.info("Clicking Logout...");
        click(logoutLink);
        return new LoginSignupPage();
    }

    public AccountCreatedDeletedPage clickDeleteAccount() {
        logger.info("Clicking Delete Account...");
        click(deleteAccountLink);
        return new AccountCreatedDeletedPage();
    }

    public void subscribeWithEmail(String email) {
        logger.info("Subscribing with email: " + email);
        scrollToElement(subscriptionText);
        type(subscriptionEmailInput, email);
        click(subscribeButton);
    }

    public boolean isSubscriptionVisible() {
        scrollToBottom();
        return isDisplayed(subscriptionText);
    }

    public void clickScrollUp() {
        scrollToBottom();
        click(scrollUpButton);
    }

    public String getHeroSliderText() {
        return getText(heroSliderText);
    }
}