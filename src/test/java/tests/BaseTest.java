package tests;

import com.microsoft.playwright.*;
import factory.PlaywrightFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.LoginPage;
import pages.ProductsPage;
import utils.ExtentManager;

import java.nio.file.Paths;
import java.util.Properties;

public class BaseTest {
    PlaywrightFactory pf;
    Page page;
    protected Properties prop;

    protected ProductsPage productsPage;
    protected LoginPage loginPage;

    @BeforeSuite
    public void launchBrowser() {
        pf=new PlaywrightFactory();
        prop=pf.init_prop();
        page=pf.initBrowser(prop);
        loginPage = new LoginPage(PlaywrightFactory.getPage());
        productsPage=new ProductsPage(PlaywrightFactory.getPage());
    }

    /*
     @AfterSuite
    public void closeBrowser() {
        if (playwright != null) {
            playwright.close();
        }
        ExtentManager.getInstance().flush();
    }


     */
     /*
    @BeforeMethod


    @AfterMethod
    public void closeContext(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshotPath = "test-output/screenshots/" + result.getName() + ".png";
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)));
        }
        
        try {
            Thread.sleep(3000); // Wait for 3 seconds after each test
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (context != null) {
            context.close();
        }
    }

     */
} 