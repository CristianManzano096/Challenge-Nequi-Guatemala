package co.com.bancolombia.api;
import co.com.bancolombia.model.Branch;
import co.com.bancolombia.model.Product;
import co.com.bancolombia.usecase.franchise.ProductUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ProductController {

    private final ProductUseCase productUseCase;

    @DeleteMapping(path = "/product/{productId}")
    public Mono<Boolean> deleteProduct(@PathVariable("productId") Integer productId) {
        return productUseCase.deleteProduct(productId);
    }

    @PostMapping(path = "/product")
    public Mono<Product> addProduct(@RequestBody Product product) {
        return productUseCase.createProduct(product);
    }

    @PatchMapping(path = "/product/{productId}")
    public Mono<Product> updateProduct(@RequestBody Product product, @PathVariable("productId") Integer productId) {
        return productUseCase.updateProduct(product, productId);
    }

}
