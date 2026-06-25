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
        // 1. 登录
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");
        assertTrue(productsPage.isOnProductsPage(), "登录应成功");

        // 2. 添加商品
        productsPage.addProductToCart(PRODUCT_NAME);

        // 3. 进入购物车并点击 Checkout
        page.click(".shopping_cart_link");
        CartPage cartPage = new CartPage(page);
        assertTrue(cartPage.isOnCartPage(), "应在购物车页面");
        cartPage.clickCheckout();
    }

    @Test
    public void testCheckoutInformation() {
        // 验证进入信息填写页面
        CheckoutPage checkoutPage = new CheckoutPage(page);
        assertTrue(checkoutPage.isOnInformationPage(), "应进入信息填写页面");
        logPass("成功跳转到信息填写页面");
    }

    @Test
    public void testCompleteCheckout() {
        CheckoutPage checkoutPage = new CheckoutPage(page);

        // 填写信息
        checkoutPage.fillInformation("John", "Doe", "12345");
        checkoutPage.clickContinue();

        // 验证进入 Overview 页面
        assertTrue(checkoutPage.isOnOverviewPage(), "应进入订单概览页面");
        logInfo("进入订单概览页面");

        // 点击 Finish
        checkoutPage.clickFinish();

        // 验证完成页面
        assertTrue(checkoutPage.isOnCompletePage(), "应进入完成页面");
        String header = checkoutPage.getCompleteHeader();
        assertTrue(header.contains("Thank you"), "完成标题应包含 'Thank you'");
        logPass("结账成功！完成标题: " + header);

        // 点击 Back Home 回到商品列表
        checkoutPage.clickBackHome();
        assertTrue(productsPage.isOnProductsPage(), "应返回商品列表");
        logPass("成功返回商品列表");
    }

    @Test
    public void testCancelCheckout() {
        CheckoutPage checkoutPage = new CheckoutPage(page);

        // 在信息页面点击 Cancel
        checkoutPage.clickCancel();

        // 验证回到购物车页面
        assertTrue(page.url().contains("cart.html"), "应返回购物车页面");
        logPass("取消结账成功，返回购物车");
    }
}