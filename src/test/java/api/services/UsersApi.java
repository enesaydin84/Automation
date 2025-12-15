package api.services;

import api.client.ApiClient;
import io.restassured.response.Response;

public class UsersApi extends ApiClient {
    public Response createUser(Object body) {
        return request().body(body).post("/users/add");
    }

    public Response getUser(int id) {
        return request().get("/users/" + id);
    }

    public Response updateUser(int id, Object body) {
        return request().body(body).put("/users/" + id);
    }

    public Response searchUsers(java.util.Map<String, ?> queryParams) {
        return request().queryParams(queryParams).get("/users");
    }
}

