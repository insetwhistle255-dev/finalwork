package org.example.pages;

import com.microsoft.playwright.Page;

public class CheckoutPage extends BasePage {

    // ===== 选择器 =====
    // Your Information 页面
    private final String firstNameInput = "[data-test='firstName']";
    private final String lastNameInput  = "[data-test='lastName']";
    private final String postalCodeInput = "[data-test='postalCode']";
    private final String continueButton = "[data-test='continue']";
    private final String cancelButton   = "[data-test='cancel']";

    // Overview 页面
    private final String finishButton   = "[data-test='finish']";
    private final String subtotalLabel  = ".summary_subtotal_label";
    private final String taxLabel       = ".summary_tax_label";
    private final String totalLabel     = ".summary_total_label";

    // Complete 页面
    private final String completeHeader = ".complete-header";
    private final String backHomeButton = "[data-test='back-to-products']";

    public CheckoutPage(Page page) {
        super(page);
    }

    // ===== 页面判断 =====

    /**
     * 是否在信息填写页面（Checkout Step One）
     */
    public boolean isOnInformationPage() {
        return page.url().contains("checkout-step-one");
    }

    /**
     * 是否在订单概览页面（Checkout Step Two）
     */
    public boolean isOnOverviewPage() {
        return page.url().contains("checkout-step-two");
    }

    /**
     * 是否在订单完成页面（Checkout Complete）
     */
    public boolean isOnCompletePage() {
        return page.url().contains("checkout-complete");
    }

    // ===== Your Information 页面操作 =====

    /**
     * 填写个人信息
     */
    public void fillInformation(String firstName, String lastName, String postalCode) {
        page.fill(firstNameInput, firstName);
        page.fill(lastNameInput, lastName);
        page.fill(postalCodeInput, postalCode);
        logInfo("填写个人信息: " + firstName + " " + lastName + " " + postalCode);
    }

    /**
     * 点击 Continue 按钮（进入 Overview）
     */
    public void clickContinue() {
        page.click(continueButton);
        logInfo("Click the Continue button");
    }

    /**
     * 点击 Cancel 按钮（返回购物车）
     */
    public void clickCancel() {
        page.click(cancelButton);
        logInfo("Click the Cancel button");
    }

    // ===== Overview 页面操作 =====

    /**
     * 点击 Finish 按钮（完成订单）
     */
    public void clickFinish() {
        page.click(finishButton);
        logInfo("Click the Finish button");
    }

    /**
     * 获取商品小计金额（如 "Item total: $29.99"）
     */
    public String getSubtotal() {
        return page.textContent(subtotalLabel);
    }

    /**
     * 获取税费金额
     */
    public String getTax() {
        return page.textContent(taxLabel);
    }

    /**
     * 获取总计金额
     */
    public String getTotal() {
        return page.textContent(totalLabel);
    }

    // ===== Complete 页面操作 =====

    /**
     * 获取订单完成后的成功标题（如 "Thank you for your order!"）
     */
    public String getCompleteHeader() {
        return page.textContent(completeHeader);
    }

    /**
     * 点击 Back Home 按钮
     */
    public void clickBackHome() {
        page.click(backHomeButton);
        logInfo("Click the Back Home button");
    }


    /**
     * 快速完成结账：填写信息 -> Continue -> Finish
     * @param firstName 名
     * @param lastName  姓
     * @param postalCode 邮编
     */
    public void completeCheckout(String firstName, String lastName, String postalCode) {
        fillInformation(firstName, lastName, postalCode);
        clickContinue();
        clickFinish();
    }
}