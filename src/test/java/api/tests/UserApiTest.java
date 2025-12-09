package api.tests;

import api.requests.UserCreateRequest;
import api.requests.UpdateUserRequest;
import api.responses.UserResponse;
import api.services.UserApi;
import com.google.gson.Gson;
import com.microsoft.playwright.APIResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserApiTest extends BaseApiTest {

    UserApi userApi = new UserApi();
    Gson gson = new Gson();

    @Test
    public void testUserCRUD() {

        // CREATE
        UserCreateRequest createReq = new UserCreateRequest("John", "Doe", 25);
        APIResponse createResp = userApi.createUser(createReq);
        Assert.assertEquals(createResp.status(), 201);

        UserResponse created = gson.fromJson(createResp.text(), UserResponse.class);
        int userId = created.getId();

        // UPDATE
        UpdateUserRequest updateReq = new UpdateUserRequest("Mike", "Smith", 30);
        APIResponse updateResp = userApi.updateUser(userId, updateReq);
        Assert.assertEquals(updateResp.status(), 404);

        // GET
        APIResponse getResp = userApi.getUser(1);
        Assert.assertEquals(getResp.status(), 200);

        UserResponse fetched = gson.fromJson(getResp.text(), UserResponse.class);
        Assert.assertEquals(fetched.getFirstName(), "Emily");

        // DELETE
        APIResponse delResp = userApi.deleteUser(1);
        Assert.assertEquals(delResp.status(), 200);

        System.out.println("CRUD test completed successfully!");
    }
}
