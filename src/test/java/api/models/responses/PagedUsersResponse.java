package api.models.responses;

import java.util.List;

public class PagedUsersResponse {
    private int total;
    private int limit;
    private int skip;
    private List<UserResponse> users;

    public int getTotal() {
        return total;
    }

    public int getLimit() {
        return limit;
    }

    public int getSkip() {
        return skip;
    }

    public List<UserResponse> getUsers() {
        return users;
    }
}

