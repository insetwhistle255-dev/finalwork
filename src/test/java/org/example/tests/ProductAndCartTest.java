package org.example.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.example.utils.ReportManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class ProductAndCartTest extends BaseTest {

    @Test
    @DisplayName("Verify adding products to cart")
    public void testAddProductToCart() {
        // 1. Open login page and log in
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");
        logInfo("Logged in");

        // 2. Verify entering the products page
        assertTrue(productsPage.isOnProductsPage(), "Not on products page");

        // 3. Add products and verify cart count
        productsPage.addProductToCart("Sauce Labs Backpack");
        productsPage.addProductToCart("Sauce Labs Bike Light");
        String badgeText = productsPage.getCartCount();
        assertEquals("2", badgeText, "Cart badge count should be 2");
        logPass("Cart displays 2 items");

        // 4. Open cart page via cartPage and verify
        cartPage.open();
        assertTrue(cartPage.isOnCartPage(), "Not on cart page");
        assertTrue(cartPage.isProductInCart("Sauce Labs Backpack"), "Backpack should be in cart");
        assertTrue(cartPage.isProductInCart("Sauce Labs Bike Light"), "Bike light should be in cart");

        // 5. Remove one product and verify count
        cartPage.removeProduct("Sauce Labs Bike Light");
        assertEquals(1, cartPage.getItemCount(), "Cart should have 1 item after removal");
        logPass("Successfully removed product and verified count");
    }

    @Test
    @DisplayName("Verify clearing the cart")
    public void testClearCart() {
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");
        productsPage.addProductToCart("Sauce Labs Backpack");
        productsPage.addProductToCart("Sauce Labs Fleece Jacket");

        // Enter cart
        cartPage.open();
        // Remove items one by one
        cartPage.removeProduct("Sauce Labs Backpack");
        cartPage.removeProduct("Sauce Labs Fleece Jacket");
        assertEquals(0, cartPage.getItemCount(), "Cart should be empty");
        logPass("Cart has been cleared");
    }
    // ===== Report log helper methods (directly usable in test class) =====

    protected void logInfo(String message) {
        ExtentTest test = ReportManager.getTest();
        if (test != null) {
            test.log(Status.INFO, message);
        }
    }

    protected void logPass(String message) {
        ExtentTest test = ReportManager.getTest();
        if (test != null) {
            test.log(Status.PASS, message);
        }
    }

    protected void logFail(String message) {
        ExtentTest test = ReportManager.getTest();
        if (test != null) {
            test.log(Status.FAIL, message);
        }
    }
}