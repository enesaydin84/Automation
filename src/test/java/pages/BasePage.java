package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import locators.LocatorReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.LogUtils;


public class BasePage {
    protected Page page;
    protected Logger logger = LoggerFactory.getLogger(getClass());

    public BasePage(Page page) {

        this.page = page;
    }
    protected String getLocator(String elementName) {
        String pageName = this.getClass().getSimpleName();
        return LocatorReader.getLocator(pageName, elementName);
    }


    public void fill(String locator, String value) {
        try {
            page.waitForSelector(locator).fill(value);
            logger.info("Filled element '{}' with value '{}'", locator, value);
        } catch (Exception e) {
            LogUtils.logSimpleException(logger, "Failed to fill element: " + locator, e);
            throw e;
        }
    }


    public void click(String locator) {
        try {
            page.waitForSelector(locator).click();
            logger.info("Clicked on element: {}", locator);
        } catch (Exception e) {
            LogUtils.logSimpleException(logger, "Failed to click element: " + locator, e);
            throw e;
        }
    }

    public void waitForElement(String locator) {

        try {
            page.waitForSelector(locator);
            logger.debug("Waited for element: {}", locator);
        } catch (Exception e) {
            LogUtils.logSimpleException(logger, "Failed to wait for element: " + locator, e);
            throw e;
        }
    }

    public void waitForElementToBeVisible(String locator) {
        try {
            page.waitForSelector(
                    locator,
                    new Page.WaitForSelectorOptions().setState(WaitForSelectorState.ATTACHED)
            );
            logger.info("Element '{}' is visible on the page.", locator);
        } catch (Exception e) {
            LogUtils.logSimpleException(logger, "Failed waiting for visibility: " + locator, e);
            throw e;
        }
    }


    public boolean isVisible(String locator) {
        try {
            return page.waitForSelector(locator,
                    new Page.WaitForSelectorOptions()
                            .setState(WaitForSelectorState.ATTACHED)
                            .setTimeout(5000)
            ) != null;
        } catch (Exception e) {
            LogUtils.logSimpleException(logger, "Visibility check failed for: " + locator, e);
            return false;
        }
    }

    /**
     * Scrolls to a specific element on the page
     * @param locator The element locator to scroll to
     */
    public void scrollToElement(String locator) {
        page.waitForSelector(locator).scrollIntoViewIfNeeded();
    }

    /**
     * Scrolls the page by a specific number of pixels
     * @param x Horizontal scroll amount in pixels
     * @param y Vertical scroll amount in pixels
     */
    public void scrollByPixels(int x, int y) {
        page.evaluate("window.scrollBy(" + x + ", " + y + ")");
    }

    /**
     * Scrolls to the top of the page
     */
    public void scrollToTop() {
        page.evaluate("window.scrollTo(0, 0)");
    }

    /**
     * Scrolls to the bottom of the page
     */
    public void scrollToBottom() {
        page.evaluate("window.scrollTo(0, document.body.scrollHeight)");
    }

    /**
     * Smooth scrolls to a specific element
     * @param locator The element locator to scroll to
     */
    public void smoothScrollToElement(String locator) {
        page.evaluate("document.querySelector('" + locator + "').scrollIntoView({ behavior: 'smooth' })");
    }

    /**
     * Scrolls until an element is visible
     * @param locator The element locator to scroll to
     * @param maxScrollAttempts Maximum number of scroll attempts
     * @return true if element becomes visible, false otherwise
     */
    public boolean scrollUntilElementVisible(String locator, int maxScrollAttempts) {
        int attempts = 0;
        while (!isVisible(locator) && attempts < maxScrollAttempts) {
            scrollByPixels(0, 300);
            attempts++;
            try {
                Thread.sleep(500); // Small delay to allow content to load
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }
        return isVisible(locator);
    }
} 