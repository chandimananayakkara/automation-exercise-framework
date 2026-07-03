package com.automationexercise.tests;

import com.automationexercise.pages.*;
import com.automationexercise.utils.ExcelReader;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.UUID;

public class SignupTest extends BaseTest {

    private static final String TEST_DATA_PATH = System.getProperty("user.dir")
            + "/src/test/resources/testdata/testdata.xlsx";

    @DataProvider(name = "signupData")
    public Object[][] getSignupData() {
        return ExcelReader.getTestData(TEST_DATA_PATH, "SignupData");
    }

    private String generateUniqueEmail() {
        return "testuser_" + UUID.randomUUID().toString().substring(0, 8) + "@mail.com";
    }

    @Test(
            dataProvider = "signupData",     // ← Excel එකෙන් data!
            priority = 1,
            description = "Register new user with data from Excel"
    )
    public void testRegisterNewUser(Map<String, String> data) {
        String uniqueEmail = generateUniqueEmail();

        logger.info("=== Register New User ===");
        logger.info("📊 Name: " + data.get("name"));
        logger.info("📊 Email: " + uniqueEmail);
        logger.info("📊 Country: " + data.get("country"));

        HomePage homePage = new HomePage();
        Assert.assertTrue(homePage.isHomePageVisible(),
                "Home page should be visible");

        LoginSignupPage loginPage = homePage.clickSignupLogin();

        Assert.assertTrue(loginPage.isNewUserSignupVisible(),
                "'New User Signup!' should be visible");

        SignupDetailsPage signupPage = loginPage.signup(
                data.get("name"),
                uniqueEmail
        );

        Assert.assertTrue(signupPage.isEnterAccountInfoVisible(),
                "'Enter Account Information' should be visible");

        AccountCreatedDeletedPage accountCreatedPage = signupPage.completeRegistration(
                data.get("title"),
                data.get("password"), data.get("day"), data.get("month"), data.get("year"), data.get("firstname"), data.get("lastname"), data.get("company"), data.get("address1"),
                data.get("address2"), data.get("country"), data.get("state"), data.get("city"), data.get("zipcode"), data.get("mobile"));

        Assert.assertTrue(accountCreatedPage.isAccountCreatedVisible(),
                "'ACCOUNT CREATED!' should be visible");

        homePage = accountCreatedPage.clickContinue();

        Assert.assertTrue(homePage.isLoggedIn(),
                "User should be logged in after registration");

        AccountCreatedDeletedPage deletedPage = homePage.clickDeleteAccount();
        Assert.assertTrue(deletedPage.isAccountDeletedVisible(),
                "'ACCOUNT DELETED!' should be visible");

        logger.info("✅ Registration PASSED for: " + data.get("name"));
    }

    @Test(
            priority = 2,
            description = "Register with already registered email"
    )
    public void testRegisterWithExistingEmail() {
        logger.info("=== Register Existing Email ===");

        HomePage homePage = new HomePage();
        LoginSignupPage loginPage = homePage.clickSignupLogin();

        loginPage.signupWithExistingEmail(
                "TestUser",
                "testuser_automation@mail.com"
        );

        Assert.assertTrue(loginPage.isEmailAlreadyExistErrorVisible(),
                "'Email Address already exist!' should be visible");

        logger.info("✅ Existing email error correctly shown");
    }
}