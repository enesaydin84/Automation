package tests;

import com.microsoft.playwright.*;
import factory.PlaywrightFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.DBUtil;
import utils.ExtentManager;
import utils.LogUtils;

import java.io.File;
import java.nio.file.Paths;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.lang.reflect.Method;

public class BaseTest {

    private PlaywrightFactory pf;
    protected Connection connection;
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @BeforeMethod
    public void setupTest(Method method) {
        logger.info("========== STARTING TEST: {} ==========", method.getName());
        pf = new PlaywrightFactory();
        pf.initBrowser();
        connection = DBUtil.getConnection();
    }

    @AfterMethod
    public void tearDownTest(ITestResult result) {
        try {
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

            String tracePath = getTraceFilePath(result);
            PlaywrightFactory.getBrowserContext().tracing().stop(new Tracing.StopOptions()
                    .setPath(Paths.get(tracePath)));
            logger.info("Trace saved to {}", tracePath);

        } catch (Exception e) {
            LogUtils.logSimpleException(logger, "Error during tearDown", e);
        } finally {
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
                LogUtils.logSimpleException(logger, "Error closing Playwright resources", e);
            }


        }
        DBUtil.closeConnection();
        logger.info("========== END OF TEST ==========\n");
    }

    @AfterSuite
    public void tearDownSuite() {
        ExtentManager.getInstance().flush();
    }

    private String getTraceFilePath(ITestResult result) {
        String baseDir = "test-output/traces/";
        String methodName = result.getMethod().getMethodName();
        String date = new SimpleDateFormat("yyyyMMdd_HH_mm").format(new Date());
        return baseDir + date + methodName + "-trace.zip";
    }
}
