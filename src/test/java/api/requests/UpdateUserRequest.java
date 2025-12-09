package api.requests;

public class UpdateUserRequest {
    private String firstName;
    private String lastName;
    private int age;

    public UpdateUserRequest(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
}
