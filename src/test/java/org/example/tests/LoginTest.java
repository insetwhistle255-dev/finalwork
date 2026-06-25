package org.example.tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest extends BaseTest {

    @Test
    public void testLoginSuccess() {
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");

        // 验证跳转到产品页面
        assertTrue(productsPage.isOnProductsPage(), "登录成功应显示产品页面");
        logPass("用户 standard_user 成功登录，看到产品页面");
    }

    @Test
    public void testLoginLockedOut() {
        loginPage.open();
        loginPage.login("locked_out_user", "secret_sauce");

        // 预期：始终停留在登录页面，并显示错误消息
        assertTrue(loginPage.isOnLoginPage(), "被锁用户应无法登录");
        String error = loginPage.getErrorMessage();
        assertTrue(error.contains("locked"), "错误消息应包含 'locked'");
        logInfo("用户 locked_out_user 被锁定，登录失败");
    }
}