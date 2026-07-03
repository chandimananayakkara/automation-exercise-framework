package com.automationexercise.tests;

import com.automationexercise.pages.CartPage;
import com.automationexercise.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SubscriptionTest extends BaseTest {

    @Test(priority = 1, description = "Verify subscription on home page")
    public void testSubscriptionHomePage() {
        logger.info("=== Subscription Home Page Test ===");

        HomePage homePage = new HomePage();

        Assert.assertTrue(homePage.isSubscriptionVisible(),
                "'SUBSCRIPTION' should be visible");

         homePage.subscribeWithEmail("testauto@mail.com");

        logger.info("✅ Subscription home page test PASSED");
    }

    @Test(priority = 2, description = "Verify scroll up and scroll down")
    public void testScrollUpDown() {
        logger.info("=== Scroll Up/Down Test ===");

        HomePage homePage = new HomePage();

        Assert.assertTrue(homePage.isSubscriptionVisible(),
                "'SUBSCRIPTION' should be visible at bottom");

        homePage.clickScrollUp();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        String heroText = homePage.getHeroSliderText();
        Assert.assertNotNull(heroText, "Hero slider text should be visible after scrolling up");

        logger.info("✅ Scroll Up/Down test PASSED");
    }
}