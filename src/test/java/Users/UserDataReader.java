package Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.Map;

public class UserDataReader {

    private static Map<String, User> users;

    static {
        try {
            ObjectMapper mapper = new ObjectMapper();
            users = mapper.readValue(
                    new File("src/test/java/Users/user.json"),
                    mapper.getTypeFactory().constructMapType(Map.class, String.class, User.class)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static User getUser(String key) {

        return users.get(key);
    }
}

