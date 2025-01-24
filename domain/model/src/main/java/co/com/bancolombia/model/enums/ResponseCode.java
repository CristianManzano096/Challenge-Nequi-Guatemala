package co.com.bancolombia.model.enums;

public enum ResponseCode {
    SUCCESS("C00", "Operation successful", 200),
    VALIDATION_ERROR("C01", "Validation error occurred", 400),
    NOT_FOUND("C02", "Resource not found", 404),
    INTERNAL_ERROR("C03", "Internal server error", 500),
    DATABASE_ERROR("C04", "Unexpected database error", 500);

    private final String code;
    private final Integer statusCode;
    private final String message;

    ResponseCode(String code, String message, Integer statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
    public String getCode() {
        return code;
    }
    public Integer getStatusCode(){
        return statusCode;
    }
    public String getMessage() {
        return message;
    }
}