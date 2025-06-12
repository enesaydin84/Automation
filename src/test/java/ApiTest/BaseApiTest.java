package ApiTest;

import com.microsoft.playwright.APIRequestContext;
import factory.PlaywrightFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;

public class BaseApiTest {

    protected APIRequestContext api;
    private static final Logger logger = LoggerFactory.getLogger(BaseApiTest.class);
    @BeforeClass
    public void setupApi() {
        logger.info("Initializing API request context");
        PlaywrightFactory.initApiRequestContext();
        api = PlaywrightFactory.getApiRequestContext();
    }

    @AfterClass
    public void tearDownApi() {
        logger.info("Tearing down API request context");
        if (api != null) {
            api.dispose();
            logger.info("API request context disposed");
        }
        if (PlaywrightFactory.getPlaywright() != null) {
            PlaywrightFactory.getPlaywright().close();
            logger.info("Playwright instance closed");
        }
    }
}