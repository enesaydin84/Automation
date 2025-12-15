package api.models.responses;

public class ErrorResponse {
    private String error;
    private String message;
    private int statusCode;

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }
}

