package com.automationexercise.tests;

import com.automationexercise.pages.ContactUsPage;
import com.automationexercise.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ContactUsTest extends BaseTest {

    @Test(priority = 1, description = "Verify Contact Us form submission")
    public void testContactUsForm() {
        logger.info("=== Contact Us Form Test ===");

        HomePage homePage = new HomePage();
        ContactUsPage contactPage = homePage.clickContactUs();

        Assert.assertTrue(contactPage.isGetInTouchVisible(),
                "'GET IN TOUCH' should be visible");

        contactPage.fillContactForm(
                "Test User",
                "testuser@mail.com",
                "Test Subject - Automation",
                "This is an automated test message from Selenium framework."
        );

        String filePath = System.getProperty("user.dir") + "/pom.xml";
        contactPage.uploadFile(filePath);

        contactPage.clickSubmit();

        Assert.assertTrue(contactPage.isSuccessMessageVisible(),
                "Success message should be visible after form submission");

        homePage = contactPage.clickHome();
        Assert.assertTrue(homePage.isHomePageVisible(),
                "Should navigate to home page");

        logger.info("✅ Contact Us form test PASSED");
    }
}