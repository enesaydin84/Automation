package api.client;

import api.config.ApiConfig;
import api.models.requests.UserLoginRequest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

import static io.restassured.RestAssured.given;

/**
 * Basit token alma ve önbellekleme katmanı.
 * Thread-safe olması için ConcurrentHashMap + entry bazlı TTL kullanır.
 */
public class TokenProvider {

    private static final ConcurrentHashMap<String, CachedToken> cache = new ConcurrentHashMap<>();
    // Örnek TTL: 50 dakika
    private static final Duration TTL = Duration.ofMinutes(50);

    public static String getToken() {
        String key = ApiConfig.USERNAME;
        CachedToken existing = cache.get(key);
        if (existing != null && !existing.isExpired()) {
            return existing.token();
        }

        String fresh = fetchToken(ApiConfig.USERNAME, ApiConfig.PASSWORD);
        cache.put(key, new CachedToken(fresh, Instant.now()));
        return fresh;
    }

    private static String fetchToken(String username, String password) {
        UserLoginRequest req = new UserLoginRequest(username, password);
        Response res = given()
                .baseUri(ApiConfig.BASE_URL)
                .contentType(ContentType.JSON)
                .body(req)
                .post("/auth/login");

        res.then().statusCode(200);
        String token = res.jsonPath().getString("token");
        if (token == null || token.isBlank()) {
            token = res.jsonPath().getString("accessToken");
        }
        if (token == null || token.isBlank()) {
            throw new IllegalStateException("Auth response did not contain token/accessToken. Body: " + res.getBody().asString());
        }
        return token;
    }

    private record CachedToken(String token, Instant createdAt) {
        boolean isExpired() {
            return Instant.now().isAfter(createdAt.plus(TTL));
        }
    }
}

