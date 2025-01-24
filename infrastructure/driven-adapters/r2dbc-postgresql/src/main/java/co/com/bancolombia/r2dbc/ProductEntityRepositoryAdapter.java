package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.Branch;
import co.com.bancolombia.model.Franchise;
import co.com.bancolombia.model.Product;
import co.com.bancolombia.model.enums.ResponseCode;
import co.com.bancolombia.model.exception.CustomException;
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
                .map(mapper::productEntityToProduct)
                .onErrorMap(e -> new CustomException(ResponseCode.DATABASE_ERROR, "Error creating product: " + e.getMessage()));
    }

    @Override
    public Mono<Boolean> deleteProduct(Integer productId) {
        return productEntityRepository.deleteById(productId)
                .then(Mono.just(true))
                .onErrorMap(e -> new CustomException(ResponseCode.DATABASE_ERROR, "Error deleting product: " + e.getMessage()));
    }

    @Override
    public Mono<Product> updateProduct(Product product, Integer id) {
        return Mono.just(id)
                .flatMap(productEntityRepository::findById)
                .switchIfEmpty(Mono.error(new CustomException(ResponseCode.NOT_FOUND,"No product found for id: "+id)))
                .flatMap(productEntity -> {
                    Optional.ofNullable(product.getStock()).ifPresent(productEntity::setStock);
                    Optional.ofNullable(product.getName()).ifPresent(productEntity::setName);
                    return productEntityRepository.save(productEntity);
                }).map(mapper::productEntityToProduct)
                .onErrorMap(e -> new CustomException(ResponseCode.DATABASE_ERROR, "Error updating branch: " + e.getMessage()));
    }

    @Override
    public Flux<Product> getAll() {
        return productEntityRepository.findAll()
                .map(mapper::productEntityToProduct)
                .onErrorMap(e -> new CustomException(ResponseCode.DATABASE_ERROR, "Error get All products: " + e.getMessage()));
    }
}