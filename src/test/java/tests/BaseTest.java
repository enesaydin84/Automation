package tests;

import com.microsoft.playwright.*;
import factory.PlaywrightFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.LoginPage;
import pages.ProductsPage;
import utils.ExtentManager;

import java.io.File;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class BaseTest {
    protected static Properties prop;
    protected LoginPage loginPage;
    protected ProductsPage productsPage;
    private PlaywrightFactory pf;  // ThreadLocal yerine sadece instance değişken

    @BeforeSuite
    public void init() {
        // Properties'i bir kez yükle
        pf = new PlaywrightFactory();
        prop = pf.init_prop();
    }

    @BeforeMethod
    public void setupTest() {
        // Her test için yeni bir PlaywrightFactory oluştur ve browser başlat
        pf = new PlaywrightFactory();
        pf.initBrowser(prop);

        // Page object'leri oluştur (PlaywrightFactory static ThreadLocal'dan alıyor)
        loginPage = new LoginPage(PlaywrightFactory.getPage());
        productsPage = new ProductsPage(PlaywrightFactory.getPage());
    }

    @AfterMethod
    public void tearDownTest(ITestResult result) {
        try {
            // Test başarısız olduğunda screenshot al
            if (result.getStatus() == ITestResult.FAILURE) {
                String screenshotFolder = "test-output/testReport/screenshots/";
                new File(screenshotFolder).mkdirs();

                String screenshotPath = screenshotFolder +
                        new SimpleDateFormat("yyyyMMdd_hh_mm").format(new Date()) + result.getName() + ".png";

                PlaywrightFactory.getPage().screenshot(new Page.ScreenshotOptions()
                        .setPath(Paths.get(screenshotPath))
                        .setFullPage(true));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Trace'i kaydet
            String tracePath = getTraceFilePath(result);
            PlaywrightFactory.getBrowserContext().tracing().stop(new Tracing.StopOptions()
                    .setPath(Paths.get(tracePath)));

            // Kaynakları temizle
            if (PlaywrightFactory.getBrowserContext() != null) {
                PlaywrightFactory.getBrowserContext().close();
            }
            if (PlaywrightFactory.getBrowser() != null) {
                PlaywrightFactory.getBrowser().close();
            }
            if (PlaywrightFactory.getPlaywright() != null) {
                PlaywrightFactory.getPlaywright().close();
            }
        }
    }

    @AfterSuite
    public void tearDown() {
        ExtentManager.getInstance().flush();
    }

    public String getTraceFilePath(ITestResult result) {
        String baseDir = "test-output/traces/";
        String methodName = result.getMethod().getMethodName();
        String date = new SimpleDateFormat("yyyyMMdd_hh_mm").format(new Date());
        return baseDir + date + methodName + "-trace.zip";
    }
}
