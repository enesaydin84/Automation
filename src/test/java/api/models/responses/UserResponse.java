package api.models.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponse {
    private int id;
    private String firstName;
    private String lastName;
    private String maidenName;
    private String email;
    private AddressResponse address;
    private List<String> roles;

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getMaidenName() {
        return maidenName;
    }

    public AddressResponse getAddress() {
        return address;
    }

    public List<String> getRoles() {
        return roles;
    }
}

