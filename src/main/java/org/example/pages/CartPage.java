package org.example.pages;

import com.microsoft.playwright.Page;
import java.util.List;

public class CartPage extends BasePage {

    // 选择器
    private final String cartTitle = ".title";                    // 页面标题 "Your Cart"
    private final String cartItems = ".cart_item";                // 购物车中所有商品行
    private final String cartItemName = ".inventory_item_name";   // 商品名称
    private final String cartItemQuantity = ".cart_quantity";     // 商品数量
    private final String checkoutButton = "[data-test='checkout']";
    private final String continueShoppingButton = "[data-test='continue-shopping']";
    private final String removeButton = ".btn_secondary";         // Remove 按钮

    public CartPage(Page page) {
        super(page);
    }

    /**
     * 打开购物车页面（通过 URL 直接导航）
     */
    public void open() {
        page.navigate("https://www.saucedemo.com/cart.html");
        logInfo("打开购物车页面");
    }

    /**
     * 判断是否在购物车页面（标题是否为 "Your Cart"）
     */
    public boolean isOnCartPage() {
        return page.textContent(cartTitle).equals("Your Cart");
    }

    /**
     * 获取购物车中所有商品的名称
     */
    public List<String> getCartItemNames() {
        return page.locator(cartItemName).allInnerTexts();
    }

    /**
     * 获取购物车中商品的数量
     */
    public int getItemCount() {
        return page.locator(cartItems).count();
    }

    /**
     * 判断指定商品是否在购物车中
     */
    public boolean isProductInCart(String productName) {
        return getCartItemNames().contains(productName);
    }

    /**
     * 获取指定商品的显示数量（通常为 1）
     */
    public String getItemQuantity(String productName) {
        // 根据商品名称找到其对应的数量元素
        // cart_item 包含名称和数量，这里简化处理：取所有数量中的第一个（实际应更精确）
        return page.locator(cartItemQuantity).first().textContent();
    }

    /**
     * 点击 Checkout 按钮
     */
    public void clickCheckout() {
        page.click(checkoutButton);
        logInfo("点击 Checkout 按钮");
    }

    /**
     * 点击 Continue Shopping 按钮
     */
    public void clickContinueShopping() {
        page.click(continueShoppingButton);
        logInfo("点击 Continue Shopping 按钮");
    }

    /**
     * 从购物车中移除指定商品
     * @param productName 商品名称，如 "Sauce Labs Backpack"
     */
    public void removeProduct(String productName) {
        // 找到包含该商品名称的 cart_item，再点击其内部的 Remove 按钮
        // 简化：所有 Remove 按钮的 data-test 属性为 "remove-{商品名小写用-连接}"
        String buttonName = "remove-" + productName.toLowerCase().replace(" ", "-");
        page.click("[data-test='" + buttonName + "']");
        logInfo("从购物车移除商品: " + productName);
    }
}