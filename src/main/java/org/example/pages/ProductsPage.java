package org.example.pages;

import com.microsoft.playwright.Page;
import java.util.List;

public class ProductsPage extends BasePage {

    // 选择器
    private final String inventoryContainer = "#inventory_container";
    private final String cartBadge = ".shopping_cart_badge";

    public ProductsPage(Page page) {
        super(page);
    }

    /**
     * 判断是否在产品列表页面（Inventory Container 可见）
     */
// 在 ProductsPage.java 中修改 isOnProductsPage 方法
    public boolean isOnProductsPage() {
        // 使用 data-test 属性定位，确保唯一
        return page.locator("#inventory_container[data-test='inventory-container']").isVisible();
        // 或者直接：page.locator("[data-test='inventory-container']").isVisible();
    }

    /**
     * 获取购物车图标上的物品数量
     */
    public String getCartCount() {
        return page.textContent(cartBadge);
    }

    /**
     * 根据商品名称添加商品到购物车
     * @param productName 商品名称，如 "Sauce Labs Backpack"
     */
    public void addProductToCart(String productName) {
        // 按钮的 data-test 属性命名规则：add-to-cart-{商品名称小写并用-连接}
        String buttonName = "add-to-cart-" + productName.toLowerCase().replace(" ", "-");
        page.click("[data-test='" + buttonName + "']");
        logInfo("添加商品: " + productName + " 到购物车");
    }

    /**
     * 获取所有商品名称列表
     */
    public List<String> getProductNames() {
        return page.locator(".inventory_item_name").allInnerTexts();
    }

    /**
     * 获取页面标题
     */
    public String getTitle() {
        return page.title();
    }
}