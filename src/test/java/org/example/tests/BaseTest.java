package org.example.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.microsoft.playwright.*;
import org.example.pages.CartPage;     // 需要导入
import org.example.pages.LoginPage;
import org.example.pages.ProductsPage;
import org.example.utils.ReportManager;
import org.junit.jupiter.api.*;

class BaseTest {

    protected static Playwright playwright;
    protected static Browser browser;
    protected BrowserContext context;
    protected Page page;

    protected LoginPage loginPage;
    protected ProductsPage productsPage;
    protected CartPage cartPage;   // ← 添加这一行

    @BeforeAll
    public static void setupBrowserAndReport() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        ReportManager.init("target/extent-report/report.html");
    }

    @BeforeEach
    public void createContextAndPage(TestInfo testInfo) {
        context = browser.newContext();
        page = context.newPage();
        loginPage = new LoginPage(page);
        productsPage = new ProductsPage(page);
        cartPage   = new CartPage(page);   // ← 初始化 cartPage

        String testName = testInfo.getDisplayName();
        ReportManager.createTest(testName);
    }

    @AfterEach
    public void closeContext() {
        if (page != null) page.close();
        if (context != null) context.close();
    }

    @AfterAll
    public static void tearDown() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
        ReportManager.flush();
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