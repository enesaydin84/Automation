package api.client;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.LogUtils;
import utils.ConfigReader;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
    private static final Map<String, String> tokenCache = new ConcurrentHashMap<>();

    public static String getToken(APIRequestContext api) {
        String username = ConfigReader.get("auth.username");
        String password = ConfigReader.get("auth.password");
        return getToken(api, username, password);
    }

    public static String getToken(APIRequestContext api, String username, String password) {
        if (tokenCache.containsKey(username)) {
            logger.debug("Using cached token for user: {}", username);
            return tokenCache.get(username);
        }

        logger.info("Requesting new token for user: {}", username);
        Map<String, String> payloadMap = new HashMap<>();
        payloadMap.put("username", username);
        payloadMap.put("password", password);

        try {
            APIResponse response = api.post("/auth/login",
                    RequestOptions.create()
                            .setHeader("Content-Type", "application/json")
                            .setData(payloadMap));
            String responseBody = response.text();
            logger.info("Token API response: {}", responseBody);

            if (response.ok()) {
                JsonObject json = JsonParser.parseString(response.text()).getAsJsonObject();
                String token = json.has("token") ? json.get("token").getAsString() : json.get("accessToken").getAsString();
                tokenCache.put(username, token);
                logger.info("Token successfully retrieved and cached for {}.", username);
                return token;
            } else {
                logger.error("Failed to retrieve token. Status code: {}", response.status());
                throw new RuntimeException("Token alınamadı! Status code: " + response.status());
            }
        } catch (Exception e) {
            LogUtils.logSimpleException(logger, "Token alma sırasında hata oluştu", e);
            throw new RuntimeException("Token alma işlemi başarısız", e);
        }
    }

    public static void clearCache() {
        tokenCache.clear();
    }
}
