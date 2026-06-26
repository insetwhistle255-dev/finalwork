package org.example.tests;

import org.example.pages.CartPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CartTest extends BaseTest {

    private static final String PRODUCT_NAME = "Sauce Labs Backpack";

    @BeforeEach
    public void loginAndAddProduct() {
        // 1. 登录
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");
        assertTrue(productsPage.isOnProductsPage(), "Login successful");

        // 2. 添加商品到购物车
        productsPage.addProductToCart(PRODUCT_NAME);
        logInfo("Added " + PRODUCT_NAME + " to cart");

        // 3. 进入购物车页面
        // 点击购物车图标（位于页面右上角）
        page.click(".shopping_cart_link");
        assertTrue(new CartPage(page).isOnCartPage(), "should navigate to the cart page");
    }

    @Test
    public void testCartDisplaysAddedProduct() {
        CartPage cartPage = new CartPage(page);

        // 验证购物车中显示添加的商品
        assertTrue(cartPage.isProductInCart(PRODUCT_NAME),
                "The cart should contain " + PRODUCT_NAME);
        assertEquals(1, cartPage.getItemCount(),
                "There should be 1 item in the cart");
        logPass("The cart correctly displays the added product: " + PRODUCT_NAME);
    }

    @Test
    public void testRemoveProductFromCart() {
        CartPage cartPage = new CartPage(page);

        // 移除商品
        cartPage.removeProduct(PRODUCT_NAME);
        logInfo("Removed " + PRODUCT_NAME);

// Verify cart is empty
        assertFalse(cartPage.isProductInCart(PRODUCT_NAME),
                "Cart should not contain " + PRODUCT_NAME);
        assertEquals(0, cartPage.getItemCount(),
                "Cart should have no items");
        logPass("Product successfully removed from cart");
    }

    @Test
    public void testNavigateToCheckout() {
        CartPage cartPage = new CartPage(page);

        // 点击 Checkout
        cartPage.clickCheckout();

        // Verify navigation to checkout information page (URL contains checkout-step-one)
        assertTrue(page.url().contains("checkout-step-one"),
                "Should navigate to checkout information page");
        logPass("Successfully navigated from shopping cart to checkout page");
    }

    @Test
    public void testNavigateBackToProducts() {
        CartPage cartPage = new CartPage(page);

        // 点击 Continue Shopping
        cartPage.clickContinueShopping();

        // 验证回到商品列表页
        assertTrue(productsPage.isOnProductsPage(),
                "Should return to the product list page");
        logPass("Successfully returned to the product list from the shopping cart");
    }
}