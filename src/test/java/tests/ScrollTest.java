package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.BasePage;

public class ScrollTest extends BaseTest {
    
    @Test(description = "Verify page scrolling functionality")
    public void validateScrollFunctionality() {
        BasePage basePage = new BasePage(page);

        loginPage.navigateToLoginPage();
        loginPage.login(prop.getProperty("username"),prop.getProperty("password"));
        
        // Get initial position (should be at top)
        Object initialPosition = page.evaluate("window.pageYOffset");
        Assert.assertEquals(initialPosition.toString(), "0", "Page should start at the top");
        
        // Scroll to bottom
        basePage.scrollToBottom();
        
        // Wait briefly for scroll to complete
        page.waitForTimeout(1000);
        
        // Get position after scrolling to bottom
        Object bottomPosition = page.evaluate("window.pageYOffset");
        double bottomOffset = Double.parseDouble(bottomPosition.toString());
        Assert.assertTrue(bottomOffset > 0, "Page should be scrolled down");
        
        // Scroll back to top
        basePage.scrollToTop();
        
        // Wait briefly for scroll to complete
        page.waitForTimeout(1000);
        
        // Verify we're back at the top
        Object finalPosition = page.evaluate("window.pageYOffset");
        Assert.assertEquals(finalPosition.toString(), "0", "Page should be back at the top");
    }
} 