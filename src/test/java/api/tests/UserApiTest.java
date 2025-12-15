package api.tests;

import api.models.requests.UserCreateRequest;
import api.services.UsersApi;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserApiTest extends BaseApiTest {
    private UsersApi users;

    @BeforeClass
    public void init() { users = new UsersApi(); }

    @Test
    public void create_user_returns_201_and_body() {
        var req = new UserCreateRequest("John", "Tester");
        Response res = users.createUser(req);

        res.then().statusCode(201);
        assertThat(res.jsonPath().getString("firstName")).isEqualTo("John");
    }
}