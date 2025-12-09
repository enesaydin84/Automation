package api.client;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;

import java.util.Map;

public class ApiClient {

    private static ThreadLocal<APIRequestContext> requestContext = new ThreadLocal<>();

    public static void init(String baseUrl, String token) {
        Playwright playwright = Playwright.create();

        APIRequest request = playwright.request();

        requestContext.set(
                request.newContext(new APIRequest.NewContextOptions()
                        .setBaseURL(baseUrl)
                        .setExtraHTTPHeaders(token != null ?
                                Map.of("Authorization", "Bearer " + token) : Map.of())
                )
        );

    }

    public static APIRequestContext get() {
        return requestContext.get();
    }
}
