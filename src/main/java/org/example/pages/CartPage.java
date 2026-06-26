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

    public void open() {
        page.navigate("https://www.saucedemo.com/cart.html");
        logInfo("Open the shopping cart page");
    }

    public boolean isOnCartPage() {
        return page.textContent(cartTitle).equals("Your Cart");
    }


    public List<String> getCartItemNames() {
        return page.locator(cartItemName).allInnerTexts();
    }


    public int getItemCount() {
        return page.locator(cartItems).count();
    }


    public boolean isProductInCart(String productName) {
        return getCartItemNames().contains(productName);
    }


    public String getItemQuantity(String productName) {

        return page.locator(cartItemQuantity).first().textContent();
    }

    public void clickCheckout() {
        page.click(checkoutButton);
        logInfo("Click the Checkout button");
    }


    public void clickContinueShopping() {
        page.click(continueShoppingButton);
        logInfo("Click the Continue Shopping button");
    }


    public void removeProduct(String productName) {
        // 找到包含该商品名称的 cart_item，再点击其内部的 Remove 按钮
        // 简化：所有 Remove 按钮的 data-test 属性为 "remove-{商品名小写用-连接}"
        String buttonName = "remove-" + productName.toLowerCase().replace(" ", "-");
        page.click("[data-test='" + buttonName + "']");
        logInfo("Remove item from shopping cart:" + productName);
    }
}