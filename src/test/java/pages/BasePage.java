package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class BasePage {
    public static Page page;

    public BasePage(Page page) {

        BasePage.page = page;
    }

    public void fill(String locator, String value) {

        page.waitForSelector(locator).fill(value);
    }

    public void click(String locator) {

        page.waitForSelector(locator).click();
    }

    public void waitForElement(String locator) {

        page.waitForSelector(locator);
    }

    public void waitForElementToBeVisible(String locator) {
        page.waitForSelector(locator, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.ATTACHED));
    }

    public boolean isVisible(String locator) {
        try {
            return page.waitForSelector(locator,
                    new Page.WaitForSelectorOptions()
                            .setState(WaitForSelectorState.ATTACHED)
                            .setTimeout(5000)
            ) != null;
        } catch (Exception e) {
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