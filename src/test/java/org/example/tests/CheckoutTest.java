package org.example.tests;

import org.example.pages.CartPage;
import org.example.pages.CheckoutPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CheckoutTest extends BaseTest {

    private static final String PRODUCT_NAME = "Sauce Labs Backpack";

    @BeforeEach
    public void precondition() {
        // 1. Login
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");
        assertTrue(productsPage.isOnProductsPage(), "Login should be successful");

        // 2. Add product
        productsPage.addProductToCart(PRODUCT_NAME);

        // 3. Go to cart and click Checkout
        page.click(".shopping_cart_link");
        CartPage cartPage = new CartPage(page);
        assertTrue(cartPage.isOnCartPage(), "Should be on cart page");
        cartPage.clickCheckout();
    }

    @Test
    public void testCheckoutInformation() {
        // Verify entering the information page
        CheckoutPage checkoutPage = new CheckoutPage(page);
        assertTrue(checkoutPage.isOnInformationPage(), "Should enter the information page");
        logPass("Successfully navigated to the information page");
    }

    @Test
    public void testCompleteCheckout() {
        CheckoutPage checkoutPage = new CheckoutPage(page);

        // Fill in information
        checkoutPage.fillInformation("John", "Doe", "12345");
        checkoutPage.clickContinue();

        // Verify entering the Overview page
        assertTrue(checkoutPage.isOnOverviewPage(), "Should enter the order overview page");
        logInfo("Entered the order overview page");

        // Click Finish
        checkoutPage.clickFinish();

        // Verify the completion page
        assertTrue(checkoutPage.isOnCompletePage(), "Should enter the completion page");
        String header = checkoutPage.getCompleteHeader();
        assertTrue(header.contains("Thank you"), "Completion header should contain 'Thank you'");
        logPass("Checkout successful! Completion header: " + header);

        // Click Back Home to return to product list
        checkoutPage.clickBackHome();
        assertTrue(productsPage.isOnProductsPage(), "Should return to product list");
        logPass("Successfully returned to product list");
    }

    @Test
    public void testCancelCheckout() {
        CheckoutPage checkoutPage = new CheckoutPage(page);

        // Click Cancel on the information page
        checkoutPage.clickCancel();

        // Verify returning to the cart page
        assertTrue(page.url().contains("cart.html"), "Should return to cart page");
        logPass("Checkout cancelled successfully, returned to cart");
    }
}