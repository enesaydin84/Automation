package ApiTest;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.LogUtils;

import java.util.HashMap;
import java.util.Map;

public class TokenProvider {

    private static String token;
    private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    public static String getToken(APIRequestContext api) {
        if (token == null) {
            logger.info("Requesting new token...");
            Map<String, String> payloadMap = new HashMap<>();
            payloadMap.put("username", "emilys");
            payloadMap.put("password", "emilyspass");

            try {
                APIResponse response = api.post("https://dummyjson.com/auth/login",
                        RequestOptions.create()
                                .setHeader("Content-Type", "application/json")
                                .setData(payloadMap));
                String responseBody = response.text();
                logger.info("Token API response: {}", responseBody);

                if (response.ok()) {
                    JsonObject json = JsonParser.parseString(response.text()).getAsJsonObject();
                    token = json.get("accessToken").getAsString();
                    logger.info("Token successfully retrieved: {}", token);
                } else {
                    logger.error("Failed to retrieve token. Status code: {}", response.status());
                    throw new RuntimeException("Token alınamadı! Status code: " + response.status());
                }
            } catch (Exception e) {
                LogUtils.logSimpleException(logger, "Token alma sırasında hata oluştu", e);
                throw new RuntimeException("Token alma işlemi başarısız", e);
            }
        } else {
            logger.debug("Using cached token: {}", token);
        }
        return token;
    }
}
