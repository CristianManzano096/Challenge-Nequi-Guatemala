package co.com.bancolombia.api.config;

import co.com.bancolombia.model.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, String>> handleCustomException(CustomException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("code", ex.getResponseCode().getCode());
        errorResponse.put("message", ex.getMessage());

        return ResponseEntity
                .status(ex.getResponseCode().getStatusCode())
                .body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        log.error(ex.getMessage());
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("code", "GENERIC_ERROR");
        errorResponse.put("message", "An unexpected error occurred. Please try again later.");

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }
}
