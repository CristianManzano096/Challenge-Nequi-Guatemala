package co.com.bancolombia.api.handler;

import co.com.bancolombia.model.enums.ResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static <T> Mono<ResponseEntity<Map<String, Object>>> success(T data, ResponseCode responseCode) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", responseCode.getCode());
        response.put("message", responseCode.getMessage());
        response.put("data", data);

        return Mono.just(ResponseEntity.status(responseCode.getStatusCode()).body(response));
    }

    public static Mono<ResponseEntity<Map<String, Object>>> error(String code, String message, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", code);
        response.put("message", message);

        return Mono.just(ResponseEntity.status(status).body(response));
    }
}
