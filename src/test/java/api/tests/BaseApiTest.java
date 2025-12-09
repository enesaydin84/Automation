package api.tests;

import api.client.ApiClient;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ConfigReader;

public class BaseApiTest {

        @BeforeMethod
        public void setup() {
            String baseUrl = ConfigReader.get("api_base_url");

            ApiClient.init(baseUrl, null);
        }

        @AfterMethod
        public void tearDown() {
            if (ApiClient.get() != null) {
                ApiClient.get().dispose();
            }
        }
    }
