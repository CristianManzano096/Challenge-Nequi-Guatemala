package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.Branch;
import co.com.bancolombia.model.Franchise;
import co.com.bancolombia.model.Product;
import co.com.bancolombia.model.gateway.BranchRepository;
import co.com.bancolombia.model.gateway.ProductRepository;
import co.com.bancolombia.r2dbc.domain.BranchEntity;
import co.com.bancolombia.r2dbc.domain.ProductEntity;
import co.com.bancolombia.r2dbc.mapper.FranchiseMapper;
import co.com.bancolombia.r2dbc.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductEntityRepositoryAdapter implements ProductRepository {

    private final ProductEntityRepository productEntityRepository;
    private final ProductMapper mapper;
    @Override
    public Mono<Product> createProductWithBranch(Product product) {
        return Mono.just(product)
                .map(mapper::productToProductEntity)
                .flatMap(productEntityRepository::save)
                .map(mapper::productEntityToProduct);
    }

    @Override
    public Mono<Boolean> deleteProduct(Integer productId) {
        return productEntityRepository.deleteById(productId)
                .then(Mono.just(true))
                .onErrorResume(e -> Mono.just(false));
    }

    @Override
    public Mono<Product> updateProduct(Product product, Integer id) {
        return Mono.just(id)
                .flatMap(productEntityRepository::findById)
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found for id: " + id)))
                .flatMap(productEntity -> {
                    Optional.ofNullable(product.getStock()).ifPresent(productEntity::setStock);
                    Optional.ofNullable(product.getName()).ifPresent(productEntity::setName);
                    return productEntityRepository.save(productEntity);
                }).map(mapper::productEntityToProduct);
    }

    @Override
    public Flux<Product> getAll() {
        return productEntityRepository.findAll()
                .map(mapper::productEntityToProduct);
    }
}