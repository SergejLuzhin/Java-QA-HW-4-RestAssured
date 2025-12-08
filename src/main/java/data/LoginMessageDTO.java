package data;

public class LoginMessageDTO {
    private String token;
    private String error;
    private String message;
    private String requestId;

    public LoginMessageDTO(String requestId, String message, String error, String token) {
        this.requestId = requestId;
        this.message = message;
        this.error = error;
        this.token = token;
    }

    public LoginMessageDTO(String token) {
        this.token = token;
    }

    public LoginMessageDTO(String error, String message, String requestId) {
        this.error = error;
        this.message = message;
        this.requestId = requestId;
    }

    public LoginMessageDTO() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
