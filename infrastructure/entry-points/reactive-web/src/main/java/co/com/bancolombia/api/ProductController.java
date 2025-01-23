package co.com.bancolombia.api;
import co.com.bancolombia.api.handler.ResponseHandler;
import co.com.bancolombia.model.Product;
import co.com.bancolombia.model.enums.ResponseCode;
import co.com.bancolombia.usecase.ProductUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ProductController {

    private final ProductUseCase productUseCase;

    @DeleteMapping(path = "/product/{productId}")
    public Mono<ResponseEntity<Map<String, Object>>> deleteProduct(@PathVariable("productId") Integer productId) {
        return productUseCase.deleteProduct(productId)
                .flatMap(response -> ResponseHandler.success(response, ResponseCode.SUCCESS));
    }

    @PostMapping(path = "/product")
    public Mono<ResponseEntity<Map<String, Object>>> addProduct(@RequestBody Product product) {
        return productUseCase.createProduct(product)
                .flatMap(productResponse -> ResponseHandler.success(productResponse, ResponseCode.SUCCESS));
    }

    @PatchMapping(path = "/product/{productId}")
    public Mono<ResponseEntity<Map<String, Object>>> updateProduct(@RequestBody Product product, @PathVariable("productId") Integer productId) {
        return productUseCase.updateProduct(product, productId)
                .flatMap(productResponse -> ResponseHandler.success(productResponse, ResponseCode.SUCCESS));
    }

}
