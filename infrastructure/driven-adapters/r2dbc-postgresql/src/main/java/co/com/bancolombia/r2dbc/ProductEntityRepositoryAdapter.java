package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.Branch;
import co.com.bancolombia.model.Franchise;
import co.com.bancolombia.model.Product;
import co.com.bancolombia.model.gateway.BranchRepository;
import co.com.bancolombia.model.gateway.ProductRepository;
import co.com.bancolombia.r2dbc.domain.BranchEntity;
import co.com.bancolombia.r2dbc.domain.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class ProductEntityRepositoryAdapter implements ProductRepository {

    private final ProductEntityRepository productEntityRepository;
    @Override
    public Mono<Product> createProductWithBranch(Product product) {
        return productEntityRepository.save(ProductEntity.builder()
                .branchId(product.getBranchId())
                .name(product.getName())
                .stock(product.getStock())
                .build()).map(productEntity ->
                Product.builder()
                        .id(productEntity.getId())
                        .name(productEntity.getName())
                        .stock(productEntity.getStock())
                        .branchId(productEntity.getBranchId())
                        .build());
    }

    @Override
    public Mono<Boolean> deleteProduct(Integer productId) {
        return productEntityRepository.deleteById(productId)
                .then(Mono.just(true))
                .onErrorResume(e -> Mono.just(false));
    }

    @Override
    public Mono<Boolean> setStock(Product product, Integer id) {
        return productEntityRepository.updateStock(product.getStock(), id)
                .then(Mono.just(true))
                .onErrorResume(e -> Mono.just(false));
    }

    @Override
    public Flux<Product> getAll() {
        return productEntityRepository.findAll().map(productEntity ->
                Product.builder()
                        .id(productEntity.getId())
                        .stock(productEntity.getStock())
                        .name(productEntity.getName())
                        .branchId(productEntity.getBranchId())
                        .build());
    }
}