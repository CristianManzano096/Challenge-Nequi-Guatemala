package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.Product;
import co.com.bancolombia.model.enums.ResponseCode;
import co.com.bancolombia.model.exception.CustomException;
import co.com.bancolombia.r2dbc.domain.ProductEntity;
import co.com.bancolombia.r2dbc.mapper.BranchMapper;
import co.com.bancolombia.r2dbc.mapper.ProductMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class ProductEntityRepositoryAdapterTest {

    private ProductEntityRepositoryAdapter adapter;

    @Mock
    private ProductEntityRepository productEntityRepository;

    @Mock
    private ProductMapper productMapper;

    private Product product;
    private ProductEntity productEntity;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        adapter = new ProductEntityRepositoryAdapter(productEntityRepository, productMapper);
        product = Product.builder()
                .id(1)
                .name("Test Product")
                .stock(100)
                .build();

        productEntity = ProductEntity.builder()
                .id(1)
                .name("Test Product")
                .stock(100)
                .build();
    }

    @Test
    void testCreateProductWithBranch() {
        Mockito.when(productMapper.productToProductEntity(Mockito.any(Product.class))).thenReturn(productEntity);
        Mockito.when(productEntityRepository.save(Mockito.any(ProductEntity.class))).thenReturn(Mono.just(productEntity));
        Mockito.when(productMapper.productEntityToProduct(Mockito.any(ProductEntity.class))).thenReturn(product);

        StepVerifier.create(adapter.createProductWithBranch(product))
                .expectNext(product)
                .verifyComplete();

        Mockito.verify(productMapper).productToProductEntity(product);
        Mockito.verify(productEntityRepository).save(productEntity);
        Mockito.verify(productMapper).productEntityToProduct(productEntity);
    }

    @Test
    void testDeleteProduct() {
        Mockito.when(productEntityRepository.deleteById(Mockito.anyInt())).thenReturn(Mono.empty());

        StepVerifier.create(adapter.deleteProduct(1))
                .expectNext(true)
                .verifyComplete();

        Mockito.verify(productEntityRepository).deleteById(1);
    }

    @Test
    void testDeleteProductFails() {
        Mockito.when(productEntityRepository.deleteById(Mockito.anyInt())).thenReturn(Mono.error(new RuntimeException("Database error")));

        StepVerifier.create(adapter.deleteProduct(1))
                .expectErrorMatches(throwable ->
                        throwable instanceof CustomException &&
                                ((CustomException) throwable).getResponseCode() == ResponseCode.DATABASE_ERROR &&
                                throwable.getMessage().contains("Error deleting product: Database error")
                )
                .verify();

        Mockito.verify(productEntityRepository).deleteById(1);
    }

    @Test
    void testUpdateProduct() {
        Mockito.when(productEntityRepository.findById(1)).thenReturn(Mono.just(productEntity));
        Mockito.when(productEntityRepository.save(Mockito.any(ProductEntity.class))).thenReturn(Mono.just(productEntity));
        Mockito.when(productMapper.productEntityToProduct(Mockito.any(ProductEntity.class))).thenReturn(product);

        StepVerifier.create(adapter.updateProduct(product, 1))
                .expectNext(product)
                .verifyComplete();

        Mockito.verify(productEntityRepository).findById(1);
        Mockito.verify(productEntityRepository).save(productEntity);
        Mockito.verify(productMapper).productEntityToProduct(productEntity);
    }

    @Test
    void testUpdateProductNotFound() {
        Mockito.when(productEntityRepository.findById(1)).thenReturn(Mono.empty());

        StepVerifier.create(adapter.updateProduct(product, 1))
                .expectErrorMatches(throwable -> throwable instanceof CustomException &&
                        throwable.getMessage().contains("No product found for id: 1"))
                .verify();

        Mockito.verify(productEntityRepository).findById(1);
        Mockito.verify(productEntityRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void testGetAll() {
        Mockito.when(productEntityRepository.findAll()).thenReturn(Flux.just(productEntity));
        Mockito.when(productMapper.productEntityToProduct(Mockito.any(ProductEntity.class))).thenReturn(product);

        StepVerifier.create(adapter.getAll())
                .expectNext(product)
                .verifyComplete();

        Mockito.verify(productEntityRepository).findAll();
        Mockito.verify(productMapper).productEntityToProduct(productEntity);
    }
}