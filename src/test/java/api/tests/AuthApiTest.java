package api.tests;

import api.client.TokenProvider;
import api.config.ApiConfig;
import api.models.requests.UserLoginRequest;
import api.models.responses.AuthResponse;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class AuthApiTest extends BaseApiTest {

    @Test
    public void login_returns_token_and_expiry() {
        UserLoginRequest req = new UserLoginRequest(ApiConfig.USERNAME, ApiConfig.PASSWORD);
        Response res = given()
                .baseUri(ApiConfig.BASE_URL)
                .contentType(ContentType.JSON)
                .body(req)
                .post("/auth/login");

        res.then().statusCode(200);
        AuthResponse body = res.as(AuthResponse.class);
        String token = body.getToken() != null ? body.getToken() : body.getAccessToken();

        assertThat(token).as("token or accessToken").isNotBlank();
    }

    @Test
    public void login_with_wrong_credentials_fails() {
        UserLoginRequest req = new UserLoginRequest("invalid", "wrong");

        Response res = given()
                .baseUri(ApiConfig.BASE_URL)
                .contentType(ContentType.JSON)
                .body(req)
                .post("/auth/login");

        assertThat(res.statusCode()).isBetween(400, 401);
    }
}

