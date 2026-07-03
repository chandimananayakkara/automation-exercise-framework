package com.automationexercise.tests;

import com.automationexercise.pages.*;
import com.automationexercise.utils.ExcelReader;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.UUID;

public class E2ECheckoutTest extends BaseTest {

    private static final String TEST_DATA_PATH = System.getProperty("user.dir")
            + "/src/test/resources/testdata/testdata.xlsx";

    @DataProvider(name = "checkoutData")
    public Object[][] getCheckoutData() {
        return ExcelReader.getTestData(TEST_DATA_PATH, "CheckoutData");
    }

    @Test(
            dataProvider = "checkoutData",
            priority = 1,
            description = "E2E: Complete purchase with Excel payment data"
    )
    public void testCompletePurchaseFlow(Map<String, String> data) {
        String uniqueEmail = "e2e_" + UUID.randomUUID().toString().substring(0, 8) + "@mail.com";

        logger.info("=== E2E Complete Purchase ===");
        logger.info("📊 Card Name: " + data.get("nameOnCard"));
        logger.info("📊 Comment: " + data.get("comment"));

        HomePage homePage = new HomePage();
        ProductsPage productsPage = homePage.clickProducts();
        productsPage.addProductToCart(0);
        productsPage.clickContinueShopping();
        productsPage.addProductToCart(1);
        CartPage cartPage = productsPage.clickViewCart();

        Assert.assertEquals(cartPage.getCartItemCount(), 2, "Cart should have 2 items");

        LoginSignupPage loginPage = cartPage.clickCheckoutThenRegisterLogin();

        SignupDetailsPage signupPage = loginPage.signup("E2EUser", uniqueEmail);

        AccountCreatedDeletedPage accountPage = signupPage.completeRegistration(
                "Mr", "Test@12345", "10", "May", "1990",
                "E2E", "User", "E2ECo",
                "789 Test Blvd", "Unit 10", "India",
                "TestState", "TestCity", "30300", "0771112233"
        );

        Assert.assertTrue(accountPage.isAccountCreatedVisible(),
                "Account should be created");
        homePage = accountPage.clickContinue();

        Assert.assertTrue(homePage.isLoggedIn(),
                "User should be logged in");

        cartPage = homePage.clickCart();
        CheckoutPage checkoutPage = cartPage.clickProceedToCheckout();

        checkoutPage.enterComment(data.get("comment"));

        PaymentPage paymentPage = checkoutPage.clickPlaceOrder();
        paymentPage.fillPaymentDetails(
                data.get("nameOnCard"),
                data.get("cardNumber"),
                data.get("cvc"),
                data.get("expiryMonth"),
                data.get("expiryYear")
        );
        paymentPage.clickPayAndConfirm();

        Assert.assertTrue(paymentPage.isOrderPlacedSuccessfully(),
                "Order should be confirmed");

        homePage = paymentPage.clickContinue();
        AccountCreatedDeletedPage deletedPage = homePage.clickDeleteAccount();
        Assert.assertTrue(deletedPage.isAccountDeletedVisible(), "Account deleted");

        logger.info("✅ E2E Purchase PASSED with card: " + data.get("nameOnCard"));
    }
}