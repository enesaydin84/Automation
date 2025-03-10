package tests;

import com.microsoft.playwright.*;
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
                .setHeadless(false));
    }

    @AfterSuite
    public void closeBrowser() {
        try {
            Thread.sleep(3000); // 3 second wait before closing
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        playwright.close();
    }

    @BeforeMethod
    public void createContext() {
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterMethod
    public void closeContext() {
        context.close();
    }
} 