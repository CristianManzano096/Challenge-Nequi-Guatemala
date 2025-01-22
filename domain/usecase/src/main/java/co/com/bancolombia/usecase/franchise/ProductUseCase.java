package co.com.bancolombia.usecase.franchise;

import co.com.bancolombia.model.Branch;
import co.com.bancolombia.model.Product;
import co.com.bancolombia.model.gateway.BranchRepository;
import co.com.bancolombia.model.gateway.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ProductUseCase {

    private final ProductRepository productRepository;

    public Mono<Product> createProduct(Product product){
        return productRepository.createProductWithBranch(product);
    }
    public Mono<Boolean> deleteProduct(Integer productId){
        return productRepository.deleteProduct(productId);
    }
    public Mono<Product> updateProduct(Product product, Integer productId){
        return productRepository.updateProduct(product, productId);
    }
}
