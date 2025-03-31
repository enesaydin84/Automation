package pages;

import com.microsoft.playwright.Page;

public class ProductsPage extends BasePage {
    // Locators
    private String productsTitle = ".product_label";
    private String addToCartButton = "//button[text()='ADD TO CART']";
    private String shoppingCartBadge = ".shopping_cart_badge";
    private String breakImages= "//img[contains(@src,'Break')]";

    public ProductsPage(Page page) {
        super(page);
    }

    public String getTitle() {
        return page.textContent(productsTitle);
    }

    public void addProductToCart(int index) {
        page.locator(addToCartButton).nth(index).click();
    }

    public String getCartItemCount() {
        return page.textContent(shoppingCartBadge);
    }

    public boolean hasBreakImage(){
        if (page.isVisible(breakImages)){
            return true;
        }
        else
            return false;
    }
}