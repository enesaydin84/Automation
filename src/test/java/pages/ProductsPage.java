package pages;

import com.microsoft.playwright.Page;

public class ProductsPage extends BasePage {
    // Locators
    private String productsTitle = ".title";
    private String addToCartButton = "//button[text()='ADD TO CART']";
    private String shoppingCartBadge = ".shopping_cart_badge";
    private String breakImages = "//img[contains(@src,'Break')]";

    public ProductsPage(Page page) {

        super(page);
    }

    public String getTitle() {
        waitForElementToBeVisible(productsTitle);
        return page.textContent(productsTitle);
    }

    public void addProductToCart(int index) {
        page.locator(addToCartButton).nth(index).click();
    }

    public String getCartItemCount() {
        waitForElementToBeVisible(shoppingCartBadge);
        return page.textContent(shoppingCartBadge);
    }

    public boolean hasBreakImage() {

        return isVisible(breakImages);
    }
}