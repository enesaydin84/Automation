package api.tests;

import api.models.requests.UserSearchRequest;
import api.models.responses.PagedUsersResponse;
import api.services.UsersApi;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserSearchApiTest extends BaseApiTest {

    private UsersApi users;

    @BeforeClass
    public void init() {
        users = new UsersApi();
    }

    @Test
    public void search_users_respects_limit_and_pagination() {
        UserSearchRequest search = new UserSearchRequest.Builder()
                .limit(5)
                .skip(0)
                .build();

        Response res = users.searchUsers(search.toQueryMap());
        res.then().statusCode(200);

        PagedUsersResponse body = res.as(PagedUsersResponse.class);
        assertThat(body.getLimit()).isEqualTo(5);
        assertThat(body.getUsers()).hasSizeLessThanOrEqualTo(5);
    }

    @Test
    public void search_users_supports_sort_parameter() {
        UserSearchRequest search = new UserSearchRequest.Builder()
                .limit(3)
                .sort("firstName")
                .build();

        Response res = users.searchUsers(search.toQueryMap());
        res.then().statusCode(200);

        PagedUsersResponse body = res.as(PagedUsersResponse.class);
        assertThat(body.getUsers()).hasSizeLessThanOrEqualTo(3);
    }
}

