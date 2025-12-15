package api.models.requests;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserSearchRequest {
    private final Optional<Integer> limit;
    private final Optional<Integer> skip;
    private final Optional<String> sort;

    private UserSearchRequest(Builder builder) {
        this.limit = builder.limit;
        this.skip = builder.skip;
        this.sort = builder.sort;
    }

    public Map<String, Object> toQueryMap() {
        Map<String, Object> params = new HashMap<>();
        limit.ifPresent(v -> params.put("limit", v));
        skip.ifPresent(v -> params.put("skip", v));
        sort.ifPresent(v -> params.put("sort", v));
        return params;
    }

    public static class Builder {
        private Optional<Integer> limit = Optional.empty();
        private Optional<Integer> skip = Optional.empty();
        private Optional<String> sort = Optional.empty();

        public Builder limit(Integer value) { this.limit = Optional.ofNullable(value); return this; }
        public Builder skip(Integer value) { this.skip = Optional.ofNullable(value); return this; }
        public Builder sort(String value) { this.sort = Optional.ofNullable(value); return this; }
        public UserSearchRequest build() { return new UserSearchRequest(this); }
    }
}

