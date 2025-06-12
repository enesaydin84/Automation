package ApiTest;


import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserApi {

    private final APIRequestContext api;
    private final String token;
    private static final Logger logger = LoggerFactory.getLogger(UserApi.class);

    public UserApi(APIRequestContext api) {
        this.api = api;
        this.token = TokenProvider.getToken(api);
    }

    public APIResponse createUser(String firstName, String lastName, int age) {
        String payload = String.format("""
            {
              "firstName": "%s",
              "lastName": "%s",
              "age": %d
            }
        """, firstName, lastName, age);

        logger.info("Creating user: firstName={}, lastName={}, age={}", firstName, lastName, age);
        logger.debug("Payload: {}", payload);

        return api.post("https://dummyjson.com/users/add",
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setHeader("Authorization", "Bearer " + token)
                        .setData(payload));
    }

    public APIResponse getUsers() {
        logger.info("Fetching all users");
        return api.get("https://dummyjson.com/users",
                RequestOptions.create()
                        .setHeader("Authorization", "Bearer " + token));
    }
    public APIResponse updateUser(String userId, String firstName, String lastName, int age) {
        String payload = String.format("""
            {
              "firstName": "%s",
              "lastName": "%s",
              "age": %d
            }
        """, firstName, lastName, age);

        logger.info("Updating user ID {}: firstName={}, lastName={}, age={}", userId, firstName, lastName, age);
        logger.debug("Payload: {}", payload);

        return api.put("https://dummyjson.com/users/" + userId,
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setHeader("Authorization", "Bearer " + token)
                        .setData(payload));
    }

    public APIResponse deleteUser(String userId) {
        logger.info("Deleting user ID {}", userId);
        return api.delete("https://dummyjson.com/users/" + userId,
                RequestOptions.create()
                        .setHeader("Authorization", "Bearer " + token));
    }

}
