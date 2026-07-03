package com.automationexercise.base;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;

    public BasePage() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    protected WebElement waitForVisibility(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected WebElement waitForClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForPresence(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected void click(WebElement element) {
        waitForClickable(element).click();
    }

    protected void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    protected void type(WebElement element, String text) {
        waitForVisibility(element);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(WebElement element) {
        return waitForVisibility(element).getText().trim();
    }

    protected String getAttribute(WebElement element, String attribute) {
        return waitForVisibility(element).getAttribute(attribute);
    }

    protected void selectByVisibleText(WebElement element, String text) {
        Select select = new Select(waitForVisibility(element));
        select.selectByVisibleText(text);
    }

    protected void selectByValue(WebElement element, String value) {
        Select select = new Select(waitForVisibility(element));
        select.selectByValue(value);
    }

    protected void selectByIndex(WebElement element, int index) {
        Select select = new Select(waitForVisibility(element));
        select.selectByIndex(index);
    }

    protected void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});",
                element
        );
    }

    protected void scrollToBottom() {
        ((JavascriptExecutor) driver).executeScript(
                "window.scrollTo(0, document.body.scrollHeight);"
        );
    }

    protected void scrollToTop() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
    }

    protected void hoverOver(WebElement element) {
        actions.moveToElement(waitForVisibility(element)).perform();
    }

    protected void doubleClick(WebElement element) {
        actions.doubleClick(waitForVisibility(element)).perform();
    }

    protected void rightClick(WebElement element) {
        actions.contextClick(waitForVisibility(element)).perform();
    }

    protected void dragAndDrop(WebElement source, WebElement target) {
        actions.dragAndDrop(source, target).perform();
    }

    protected void acceptAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    protected void dismissAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().dismiss();
    }

    protected String getAlertText() {
        wait.until(ExpectedConditions.alertIsPresent());
        return driver.switchTo().alert().getText();
    }

    protected boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    protected String getPageTitle() {
        return driver.getTitle();
    }

    protected void navigateTo(String url) {
        driver.get(url);
    }

    protected void switchToFrame(WebElement frame) {
        driver.switchTo().frame(frame);
    }

    protected void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    protected void uploadFile(WebElement element, String filePath) {
        element.sendKeys(filePath);
    }


    protected void handleAdWindows() {
        try {
            String originalWindow = driver.getWindowHandle();
            java.util.Set<String> allWindows = driver.getWindowHandles();

            if (allWindows.size() > 1) {
                for (String window : allWindows) {
                    if (!window.equals(originalWindow)) {
                        driver.switchTo().window(window);
                        driver.close();
                    }
                }
                driver.switchTo().window(originalWindow);
            }
        } catch (Exception e) {
        }
    }

    protected void clickAndHandleAds(WebElement element) {
        String originalWindow = driver.getWindowHandle();

        try {
            waitForClickable(element).click();
        } catch (Exception e) {
            jsClick(element);
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {
        }

        try {
            java.util.Set<String> allWindows = driver.getWindowHandles();
            if (allWindows.size() > 1) {
                for (String window : allWindows) {
                    if (!window.equals(originalWindow)) {
                        driver.switchTo().window(window);
                        driver.close();
                    }
                }
                driver.switchTo().window(originalWindow);
            }
        } catch (Exception e) {
        }
    }
}