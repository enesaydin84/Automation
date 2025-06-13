package pages;

import com.microsoft.playwright.Page;
import locators.LocatorReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.LogUtils;

public class ProductsPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(ProductsPage.class);
    // Locators


    public ProductsPage(Page page) {

        super(page);
    }

    public String getTitle() {
        logger.info("Getting page title from products page.");
        try {
            waitForElementToBeVisible(getLocator("productsTitle"));
            String title = page.textContent(getLocator("productsTitle"));
            logger.info("Products page title: {}", title);
            return title;
        } catch (Exception e) {
            LogUtils.logSimpleException(logger, "Failed to get products page title", e);
            throw new RuntimeException("Failed to retrieve products page title", e);
        }
    }


    public void addProductToCart(int index) {
        logger.info("Adding product at index {} to cart.", index);
        try {
            page.locator(getLocator("addToCartButton")).nth(index).click();
            logger.info("Product at index {} added to cart.", index);
        } catch (Exception e) {
            LogUtils.logSimpleException(logger, "Failed to add product at index " + index + " to cart", e);
            throw new RuntimeException("Failed to add product to cart", e);
        }
    }


    public String getCartItemCount() {
        logger.info("Getting shopping cart item count.");
        try {
            waitForElementToBeVisible(getLocator("shoppingCartBadge"));
            String count = page.textContent(getLocator("shoppingCartBadge"));
            logger.info("Shopping cart has {} items.", count);
            return count;
        } catch (Exception e) {
            LogUtils.logSimpleException(logger, "Failed to retrieve cart item count", e);
            return "0";
        }
    }


    public String getProductsName(int index) {
        logger.info("Getting product name at index {}.", index);
        try {
            waitForElementToBeVisible(getLocator("productsTitle"));
            String name = page.locator(getLocator("inventoryItemName")).nth(index).innerText();
            logger.info("Product name at index {}: {}", index, name);
            return name;
        } catch (Exception e) {
            LogUtils.logSimpleException(logger, "Failed to get product name at index " + index, e);
            return "";
        }
    }

    public boolean hasBreakImage() {
        logger.info("Checking for broken image elements on the products page.");
        try {
            boolean visible = isVisible(getLocator("breakImages"));
            if (visible) {
                logger.warn("Broken image(s) found on the products page.");
            } else {
                logger.info("No broken images found.");
            }
            return visible;
        } catch (Exception e) {
            LogUtils.logSimpleException(logger, "Error occurred while checking for broken images", e);
            return false;
        }
    }

}