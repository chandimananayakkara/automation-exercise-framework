package com.automationexercise.tests;

import com.automationexercise.pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class ProductTest extends BaseTest {

   @Test(priority = 1, description = "Verify All Products page and product list")
    public void testAllProductsPage() {
        logger.info("=== TC1: All Products Page ===");

        HomePage homePage = new HomePage();
        ProductsPage productsPage = homePage.clickProducts();

        Assert.assertTrue(productsPage.isAllProductsPageVisible(),
                "'ALL PRODUCTS' title should be visible");

        Assert.assertTrue(productsPage.isProductListVisible(),
                "Products list should be visible");
        Assert.assertTrue(productsPage.getProductCount() > 0,
                "Should have at least one product");

        logger.info("Total products found: " + productsPage.getProductCount());
        logger.info("✅ All Products page test PASSED");
    }

    @Test(priority = 2, description = "Verify product detail page information")
    public void testProductDetailPage() {
        logger.info("=== TC2: Product Detail Page ===");

        HomePage homePage = new HomePage();
        ProductsPage productsPage = homePage.clickProducts();

        ProductDetailPage detailPage = productsPage.clickViewFirstProduct();

        Assert.assertTrue(detailPage.isProductDetailVisible(),
                "Product detail information should be visible");

        logger.info("Product Name: " + detailPage.getProductName());
        logger.info("Category: " + detailPage.getProductCategory());
        logger.info("Price: " + detailPage.getProductPrice());
        logger.info("Availability: " + detailPage.getProductAvailability());
        logger.info("Condition: " + detailPage.getProductCondition());
        logger.info("Brand: " + detailPage.getProductBrand());

        Assert.assertFalse(detailPage.getProductName().isEmpty(),
                "Product name should not be empty");
        Assert.assertFalse(detailPage.getProductPrice().isEmpty(),
                "Product price should not be empty");

        logger.info("✅ Product detail page test PASSED");
    }
 @Test(priority = 3, description = "Search for product and verify results")
    public void testSearchProduct() {
        logger.info("=== TC3: Search Product ===");

        String searchTerm = "Top";

        HomePage homePage = new HomePage();
        ProductsPage productsPage = homePage.clickProducts();

        Assert.assertTrue(productsPage.isAllProductsPageVisible(),
                "All Products page should be visible");

        productsPage.searchProduct(searchTerm);

       Assert.assertTrue(productsPage.isSearchedProductsVisible(),
                "'SEARCHED PRODUCTS' should be visible");

        List<String> productNames = productsPage.getAllProductNames();
        Assert.assertTrue(productNames.size() > 0,
                "Search should return at least one result");

        logger.info("Search results for '" + searchTerm + "': " + productNames.size() + " products");
        logger.info("✅ Search product test PASSED");
    }

   @Test(priority = 4, description = "Add products to cart and verify cart")
    public void testAddProductsToCart() {
        logger.info("=== TC4: Add Products to Cart ===");

        HomePage homePage = new HomePage();
        ProductsPage productsPage = homePage.clickProducts();

        productsPage.addProductToCart(0);
        productsPage.clickContinueShopping();

        productsPage.addProductToCart(1);
        CartPage cartPage = productsPage.clickViewCart();

        Assert.assertEquals(cartPage.getCartItemCount(), 2,
                "Cart should have 2 items");

        logger.info("Cart items count: " + cartPage.getCartItemCount());
        logger.info("Cart items: " + cartPage.getCartItemNames());
        logger.info("✅ Add products to cart test PASSED");
    }
}