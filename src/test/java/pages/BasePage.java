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
        page.waitForSelector(locator, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
    }

    public boolean isVisible(String locator) {
        try {
            return page.waitForSelector(locator, 
                new Page.WaitForSelectorOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(5000)
            ) != null;
        } catch (Exception e) {
            return false;
        }
    }
} 