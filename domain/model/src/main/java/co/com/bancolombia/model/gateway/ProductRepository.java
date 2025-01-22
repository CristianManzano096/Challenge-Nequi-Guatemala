package co.com.bancolombia.model.gateway;

import co.com.bancolombia.model.Branch;
import co.com.bancolombia.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository {
    Mono<Product> createProductWithBranch(Product product);
    Mono<Boolean> deleteProduct(Integer productId);
    Mono<Boolean> setStock(Product product, Integer productId);
    Flux<Product> getAll();
}
