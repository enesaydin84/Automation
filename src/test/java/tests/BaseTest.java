package tests;

import com.microsoft.playwright.*;
import org.testng.ITestResult;
import org.testng.annotations.*;

public class BaseTest {
    protected static Playwright playwright;
    protected static Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeSuite
    public void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false)  // Show browser window during test execution
                .setTimeout(30000)); // Set timeout to 30 seconds
    }

    @AfterSuite
    public void closeBrowser() {
        if (playwright != null) {
            playwright.close();
        }
    }

    @BeforeMethod
    public void createContext() {
        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(1920, 1080));
        page = context.newPage();
        page.setDefaultNavigationTimeout(15000);  // Set navigation timeout to 15 seconds
    }

    @AfterMethod
    public void closeContext(ITestResult result) {
        try {
            Thread.sleep(3000); // Wait for 3 seconds after each test
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (context != null) {
            context.close();
        }
    }
} 