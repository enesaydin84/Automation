package api.requests;

public class UserCreateRequest {
    private String firstName;
    private String lastName;
    private int age;

    public UserCreateRequest(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
}
