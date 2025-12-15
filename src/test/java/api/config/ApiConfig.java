package api.config;

import utils.ConfigReader;

public final class ApiConfig {
    public static final String BASE_URL = ConfigReader.get("api_base_url");
    public static final String USERNAME = ConfigReader.get("auth.username");
    public static final String PASSWORD = ConfigReader.get("auth.password");
    private ApiConfig() {}
}