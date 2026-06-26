package org.example.pages;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.microsoft.playwright.Page;
import org.example.utils.ReportManager;

/**
 * 所有页面对象的基类，封装公共操作
 */
public class BasePage {

    protected Page page;

    public BasePage(Page page) {
        this.page = page;
    }

    //  公共方法

    /**
     * 获取当前页面标题
     */
    @SuppressWarnings("unused")
    public String getPageTitle() {
        return page.title();
    }

    /**
     * 获取当前页面的 URL
     */
    @SuppressWarnings("unused")
    public String getPageUrl() {
        return page.url();
    }

    /**
     * 等待页面加载（指定超时时间，毫秒）
     * 等待 DOMContentLoaded 状态（不引入 LoadState 枚举）
     */
    @SuppressWarnings("unused")
    public void waitForPageLoad(int timeoutMs) {
        page.waitForLoadState(
                com.microsoft.playwright.options.LoadState.DOMCONTENTLOADED,
                new Page.WaitForLoadStateOptions().setTimeout(timeoutMs)
        );
    }

    /**
     * 截图保存到指定路径
     */
    @SuppressWarnings("unused")
    public String takeScreenshot(String filePath) {
        page.screenshot(new Page.ScreenshotOptions().setPath(java.nio.file.Paths.get(filePath)));
        return filePath;
    }

    /**
     * 刷新页面
     */
    @SuppressWarnings("unused")
    public void refresh() {
        page.reload();
    }

    // ===== 报告日志辅助 =====

    @SuppressWarnings("unused")
    protected void logInfo(String message) {
        ExtentTest test = ReportManager.getTest();
        if (test != null) {
            test.log(Status.INFO, message);
        }
    }

    @SuppressWarnings("unused")
    protected void logPass(String message) {
        ExtentTest test = ReportManager.getTest();
        if (test != null) {
            test.log(Status.PASS, message);
        }
    }

    @SuppressWarnings("unused")
    protected void logFail(String message) {
        ExtentTest test = ReportManager.getTest();
        if (test != null) {
            test.log(Status.FAIL, message);
        }
    }
}