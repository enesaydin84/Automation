package ApiTest;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.microsoft.playwright.APIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserApiTest extends BaseApiTest {

    private static final Logger logger = LoggerFactory.getLogger(UserApiTest.class);

    @Test(priority = 1)
    public void testCreateUserWithToken() {
        logger.info("Running test: testCreateUserWithToken");
        UserApi userApi = new UserApi(api);
        APIResponse response = userApi.createUser("Neo", "Anderson", 35);

        Assert.assertEquals(response.status(), 201);
        JsonObject json = JsonParser.parseString(response.text()).getAsJsonObject();

        Assert.assertEquals(json.get("firstName").getAsString(), "Neo");
        Assert.assertEquals(json.get("lastName").getAsString(), "Anderson");
        Assert.assertEquals(json.get("age").getAsInt(), 35);
        logger.info("User creation test passed.");
    }

    @Test(priority = 2)
    public void testUpdateUserWithToken() {
        logger.info("Running test: testUpdateUserWithToken");
        UserApi userApi = new UserApi(api);
        // 1 numaralı kullanıcı güncelleniyor
        APIResponse response = userApi.updateUser("1", "Neo", "Matrix", 36);

        Assert.assertEquals(response.status(), 200);
        JsonObject json = JsonParser.parseString(response.text()).getAsJsonObject();

        Assert.assertEquals(json.get("firstName").getAsString(), "Neo");
        Assert.assertEquals(json.get("lastName").getAsString(), "Matrix");
        Assert.assertEquals(json.get("age").getAsInt(), 36);
        logger.info("User update test passed.");
    }

    @Test(priority = 3)
    public void testDeleteUserWithToken() {
        logger.info("Running test: testDeleteUserWithToken");
        UserApi userApi = new UserApi(api);
        APIResponse response = userApi.deleteUser("1");

        Assert.assertEquals(response.status(), 200); // dummyjson silme yanıtı da 200
        logger.info("User delete test passed.");
    }
}
