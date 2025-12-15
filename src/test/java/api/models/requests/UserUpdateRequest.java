package api.models.requests;

import java.util.Optional;

public class UserUpdateRequest {
    private final Optional<String> firstName;
    private final Optional<String> lastName;
    private final Optional<String> email;

    private UserUpdateRequest(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
    }

    public Optional<String> getFirstName() { return firstName; }
    public Optional<String> getLastName() { return lastName; }
    public Optional<String> getEmail() { return email; }

    public static class Builder {
        private Optional<String> firstName = Optional.empty();
        private Optional<String> lastName = Optional.empty();
        private Optional<String> email = Optional.empty();

        public Builder firstName(String value) { this.firstName = Optional.ofNullable(value); return this; }
        public Builder lastName(String value) { this.lastName = Optional.ofNullable(value); return this; }
        public Builder email(String value) { this.email = Optional.ofNullable(value); return this; }
        public UserUpdateRequest build() { return new UserUpdateRequest(this); }
    }
}

