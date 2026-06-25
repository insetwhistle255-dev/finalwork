package org.example.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.example.utils.ReportManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class ProductAndCartTest extends BaseTest {

    @Test
    @DisplayName("验证添加商品到购物车")
    public void testAddProductToCart() {
        // 1. 打开登录页并登录
        loginPage.open();               // 改为 open()
        loginPage.login("standard_user", "secret_sauce");
        logInfo("已登录");

        // 2. 验证进入商品页面
        assertTrue(productsPage.isOnProductsPage(), "未进入商品页面");

        // 3. 添加商品并验证购物车数量
        productsPage.addProductToCart("Sauce Labs Backpack");
        productsPage.addProductToCart("Sauce Labs Bike Light");
        String badgeText = productsPage.getCartCount();  // 返回字符串
        assertEquals("2", badgeText, "购物车徽标数量应为 2");
        logPass("购物车显示 2 个商品");

        // 4. 通过 cartPage 打开购物车页并验证
        cartPage.open();                // 直接通过 CartPage 导航
        assertTrue(cartPage.isOnCartPage(), "未进入购物车页面");
        assertTrue(cartPage.isProductInCart("Sauce Labs Backpack"), "背包应存在于购物车");
        assertTrue(cartPage.isProductInCart("Sauce Labs Bike Light"), "车灯应存在于购物车");

        // 5. 移除一个商品，验证数量
        cartPage.removeProduct("Sauce Labs Bike Light");
        // 返回商品页面查看购物车徽标（需要回到商品页面）
        // 注意：如果移除后直接在购物车页，徽标可能未更新；可以刷新或返回商品页面
        // 这里简单验证购物车页中商品数量
        assertEquals(1, cartPage.getItemCount(), "移除后购物车应有 1 件商品");
        logPass("成功移除商品并验证数量");
    }

    @Test
    @DisplayName("验证清空购物车")
    public void testClearCart() {
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");
        productsPage.addProductToCart("Sauce Labs Backpack");
        productsPage.addProductToCart("Sauce Labs Fleece Jacket");

        // 进入购物车
        cartPage.open();
        // 逐件移除
        cartPage.removeProduct("Sauce Labs Backpack");
        cartPage.removeProduct("Sauce Labs Fleece Jacket");
        assertEquals(0, cartPage.getItemCount(), "购物车应已清空");
        logPass("购物车已清空");
    }
    // ===== 报告日志快捷方法（测试类中可直接使用） =====

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