package tests;

import com.microsoft.playwright.*;
import factory.PlaywrightFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.ExtentManager;
import utils.LogUtils;

import java.io.File;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.lang.reflect.Method;

public class BaseTest {
    protected static Properties prop;
    private PlaywrightFactory pf;  // ThreadLocal yerine sadece instance değişken

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @BeforeMethod
    public void setupTest(Method method) {
        logger.info("========== STARTING TEST: {} ==========", method.getName());
        // Her test için yeni bir PlaywrightFactory oluştur ve browser başlat
        pf = new PlaywrightFactory();
        prop = pf.init_prop();
        pf.initBrowser(prop);


    }

    @AfterMethod
    public void tearDownTest(ITestResult result) {

        if (result.getStatus() == ITestResult.SUCCESS) {
            logger.info("✅ TEST PASSED: {}", result.getName());
        } else if (result.getStatus() == ITestResult.FAILURE) {
            logger.error("❌ TEST FAILED: {}", result.getName());
        }
        logger.info("========== END OF TEST ==========\n");

        try {
            // Test başarısız olduğunda screenshot al
            if (result.getStatus() == ITestResult.FAILURE) {
                String screenshotFolder = "test-output/testReport/screenshots/";
                new File(screenshotFolder).mkdirs();

                String screenshotPath = screenshotFolder +
                        new SimpleDateFormat("yyyyMMdd_HH_mm_ss").format(new Date()) + result.getName() + ".png";

                PlaywrightFactory.getPage().screenshot(new Page.ScreenshotOptions()
                        .setPath(Paths.get(screenshotPath))
                        .setFullPage(true));
                logger.info("Screenshot saved to {}", screenshotPath);
            }
        } catch (Exception e) {
            logger.error("Failed to capture screenshot: {}", e.getMessage());
        } finally {

            // Trace'i kaydet
            try {
            String tracePath = getTraceFilePath(result);
            PlaywrightFactory.getBrowserContext().tracing().stop(new Tracing.StopOptions()
                    .setPath(Paths.get(tracePath)));
            logger.info("Trace saved to {}", tracePath);
            } catch (Exception e) {
                LogUtils.logSimpleException(logger, "Failed to stop trace recording", e);
            }
            // Kaynakları temizle
            try {
                if (PlaywrightFactory.getBrowserContext() != null) {
                    PlaywrightFactory.getBrowserContext().close();
                }
                if (PlaywrightFactory.getBrowser() != null) {
                    PlaywrightFactory.getBrowser().close();
                }
                if (PlaywrightFactory.getPlaywright() != null) {
                    PlaywrightFactory.getPlaywright().close();
                }
            } catch (Exception e) {
                LogUtils.logSimpleException(logger, "Error while closing Playwright resources", e);
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
