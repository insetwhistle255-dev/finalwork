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
        assertTrue(productsPage.isOnProductsPage(), "登录应成功");

        // 2. 添加商品到购物车
        productsPage.addProductToCart(PRODUCT_NAME);
        logInfo("已添加 " + PRODUCT_NAME + " 到购物车");

        // 3. 进入购物车页面
        // 点击购物车图标（位于页面右上角）
        page.click(".shopping_cart_link");
        assertTrue(new CartPage(page).isOnCartPage(), "应跳转到购物车页面");
    }

    @Test
    public void testCartDisplaysAddedProduct() {
        CartPage cartPage = new CartPage(page);

        // 验证购物车中显示添加的商品
        assertTrue(cartPage.isProductInCart(PRODUCT_NAME),
                "购物车应包含 " + PRODUCT_NAME);
        assertEquals(1, cartPage.getItemCount(),
                "购物车中应有 1 件商品");
        logPass("购物车正确显示已添加的商品: " + PRODUCT_NAME);
    }

    @Test
    public void testRemoveProductFromCart() {
        CartPage cartPage = new CartPage(page);

        // 移除商品
        cartPage.removeProduct(PRODUCT_NAME);
        logInfo("已移除 " + PRODUCT_NAME);

        // 验证购物车为空
        assertFalse(cartPage.isProductInCart(PRODUCT_NAME),
                "购物车不应包含 " + PRODUCT_NAME);
        assertEquals(0, cartPage.getItemCount(),
                "购物车中应无商品");
        logPass("商品已成功从购物车移除");
    }

    @Test
    public void testNavigateToCheckout() {
        CartPage cartPage = new CartPage(page);

        // 点击 Checkout
        cartPage.clickCheckout();

        // 验证跳转到结账信息页面（URL 包含 checkout-step-one）
        assertTrue(page.url().contains("checkout-step-one"),
                "应跳转到结账信息页面");
        logPass("成功从购物车导航到结账页面");
    }

    @Test
    public void testNavigateBackToProducts() {
        CartPage cartPage = new CartPage(page);

        // 点击 Continue Shopping
        cartPage.clickContinueShopping();

        // 验证回到商品列表页
        assertTrue(productsPage.isOnProductsPage(),
                "应返回商品列表页面");
        logPass("成功从购物车返回商品列表");
    }
}