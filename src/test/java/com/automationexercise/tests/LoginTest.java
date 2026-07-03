package com.automationexercise.tests;

import com.automationexercise.pages.HomePage;
import com.automationexercise.pages.LoginSignupPage;
import com.automationexercise.utils.ExcelReader;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class LoginTest extends BaseTest {

   private static final String TEST_DATA_PATH = System.getProperty("user.dir")
            + "/src/test/resources/testdata/testdata.xlsx";

       @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return ExcelReader.getTestData(TEST_DATA_PATH, "LoginData");
    }
 @DataProvider(name = "validLoginData")
    public Object[][] getValidLoginData() {
        List<Map<String, String>> allData = ExcelReader.readExcelData(
                TEST_DATA_PATH, "LoginData");

       List<Map<String, String>> validData = allData.stream()
                .filter(row -> "success".equals(row.get("expectedResult")))
                .toList();

        Object[][] testData = new Object[validData.size()][1];
        for (int i = 0; i < validData.size(); i++) {
            testData[i][0] = validData.get(i);
        }
        return testData;
    }

    @DataProvider(name = "invalidLoginData")
    public Object[][] getInvalidLoginData() {
        List<Map<String, String>> allData = ExcelReader.readExcelData(
                TEST_DATA_PATH, "LoginData");

        List<Map<String, String>> invalidData = allData.stream()
                .filter(row -> "error".equals(row.get("expectedResult")))
                .toList();

        Object[][] testData = new Object[invalidData.size()][1];
        for (int i = 0; i < invalidData.size(); i++) {
            testData[i][0] = invalidData.get(i);
        }
        return testData;
    }

   @Test(
            dataProvider = "validLoginData",
            priority = 1,
            description = "Verify login with valid credentials from Excel"
    )
    public void testValidLogin(Map<String, String> data) {

        logger.info("=== Valid Login Test ===");
        logger.info("📊 Test Data: " + data.get("description"));
        logger.info("📊 Email: " + data.get("email"));

        HomePage homePage = new HomePage();
        Assert.assertTrue(homePage.isHomePageVisible(),
                "Home page should be visible");

        LoginSignupPage loginPage = homePage.clickSignupLogin();

        Assert.assertTrue(loginPage.isLoginToAccountVisible(),
                "'Login to your account' should be visible");

        homePage = loginPage.login(
                data.get("email"),
                data.get("password")
        );

        Assert.assertTrue(homePage.isLoggedIn(),
                "User should be logged in. Data: " + data.get("description"));

        String loggedInText = homePage.getLoggedInAsText();
        Assert.assertTrue(loggedInText.contains("Logged in as"),
                "Should display 'Logged in as' text");

        logger.info("✅ Valid login PASSED for: " + data.get("email"));
    }

    @Test(
            dataProvider = "invalidLoginData",
            priority = 2,
            description = "Verify login fails with invalid credentials from Excel"
    )
    public void testInvalidLogin(Map<String, String> data) {
        logger.info("=== Invalid Login Test ===");
        logger.info("📊 Test Data: " + data.get("description"));
        logger.info("📊 Email: " + data.get("email"));
        logger.info("📊 Password: " + data.get("password"));

       HomePage homePage = new HomePage();
        Assert.assertTrue(homePage.isHomePageVisible(),
                "Home page should be visible");

        LoginSignupPage loginPage = homePage.clickSignupLogin();

        loginPage.loginWithInvalidCredentials(
                data.get("email"),       // ← Excel invalid email
                data.get("password")     // ← Excel invalid password
        );

       Assert.assertTrue(loginPage.isInvalidLoginErrorVisible(),
                "Error message should appear for: " + data.get("description"));

        logger.info("✅ Invalid login correctly rejected: " + data.get("description"));
    }

    @Test(
            dataProvider = "validLoginData",
            priority = 3,
            description = "Verify logout after login using Excel data"
    )
    public void testLogout(Map<String, String> data) {
        logger.info("=== Logout Test ===");

        HomePage homePage = new HomePage();
        LoginSignupPage loginPage = homePage.clickSignupLogin();
        homePage = loginPage.login(
                data.get("email"),
                data.get("password")
        );

        Assert.assertTrue(homePage.isLoggedIn(),
                "User should be logged in");

        loginPage = homePage.clickLogout();

        Assert.assertTrue(loginPage.isLoginToAccountVisible(),
                "Should see login page after logout");

        logger.info("✅ Logout test PASSED");
    }
}