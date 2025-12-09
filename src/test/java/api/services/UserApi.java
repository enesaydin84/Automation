package api.services;

import api.client.ApiClient;
import api.requests.UserCreateRequest;
import api.requests.UpdateUserRequest;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;

public class UserApi {

    public APIResponse createUser(UserCreateRequest req) {
        return ApiClient.get().post("/users/add",
                RequestOptions.create().setData(req));
    }


    public APIResponse getUser(int id) {
        return ApiClient.get().get("/users/" + id);
    }

    public APIResponse updateUser(int id, UpdateUserRequest req) {
        return ApiClient.get().put("/users/" + id,
                RequestOptions.create().setData(req));
    }

    public APIResponse deleteUser(int id) {
        return ApiClient.get().delete("/users/" + id);
    }
}
