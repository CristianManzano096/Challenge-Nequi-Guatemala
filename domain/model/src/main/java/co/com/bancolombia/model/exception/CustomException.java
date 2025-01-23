package co.com.bancolombia.model.exception;

import co.com.bancolombia.model.enums.ResponseCode;
import lombok.Getter;

public class CustomException extends RuntimeException {
    @Getter
    private final ResponseCode responseCode;
    private final String message;

    public CustomException(ResponseCode code) {
        super(code.getMessage());
        this.responseCode = code;
        this.message = code.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
