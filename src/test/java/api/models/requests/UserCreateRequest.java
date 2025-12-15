package api.models.requests;

public class UserCreateRequest {
    private final String firstName;
    private final String lastName;

    public UserCreateRequest(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}

