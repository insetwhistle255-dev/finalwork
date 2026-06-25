package org.example.pages;

import com.microsoft.playwright.Page;

public class LoginPage extends BasePage {

    // 选择器
    private final String usernameInput = "[data-test='username']";
    private final String passwordInput = "[data-test='password']";
    private final String loginButton = "[data-test='login-button']";
    private final String errorMessage = "[data-test='error']";

    public LoginPage(Page page) {
        super(page);   // 调用 BasePage 的构造器
    }

    // 打开登录页
    public void open() {
        page.navigate("https://www.saucedemo.com/");
        logInfo("打开 SauceDemo 登录页面");
    }

    // 输入用户名
    public void enterUsername(String username) {
        page.fill(usernameInput, username);
    }

    // 输入密码
    public void enterPassword(String password) {
        page.fill(passwordInput, password);
    }

    // 点击登录按钮
    public void clickLogin() {
        page.click(loginButton);
        logInfo("点击 Login 按钮");
    }

    // 快速登录（一步完成）
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    // 获取错误消息（仅当登录失败时存在）
    public String getErrorMessage() {
        return page.textContent(errorMessage);
    }

    // 判断是否在登录页面（URL 或标题）
    public boolean isOnLoginPage() {
        return page.url().contains("saucedemo.com");
    }
}