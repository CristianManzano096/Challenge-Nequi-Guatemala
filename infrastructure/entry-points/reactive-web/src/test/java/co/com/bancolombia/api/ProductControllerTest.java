package co.com.bancolombia.api;

import co.com.bancolombia.model.Product;
import co.com.bancolombia.usecase.FranchiseUseCase;
import co.com.bancolombia.usecase.ProductUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ProductControllerTest {
    @Mock
    private ProductUseCase productUseCase;

    @InjectMocks
    private ProductController productController;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToController(productController).build();
    }

    @Test
    void deleteProduct_ShouldReturnSuccessResponse() {
        int productId = 1;
        Map<String, String> response = Map.of("message", "Product deleted successfully");

        Mockito.when(productUseCase.deleteProduct(productId)).thenReturn(Mono.just(Boolean.TRUE));

        webTestClient.delete()
                .uri("/api/product/{productId}", productId)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void addProduct_ShouldReturnSuccessResponse() {
        Product product = new Product(1, "New Product", 123123,1);

        Mockito.when(productUseCase.createProduct(product)).thenReturn(Mono.just(product));

        webTestClient.post()
                .uri("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(product)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void updateProduct_ShouldReturnSuccessResponse() {
        int productId = 1;
        Product product = new Product(productId, "Updated Product", 124124,2);

        Mockito.when(productUseCase.updateProduct(product, productId)).thenReturn(Mono.just(product));

        webTestClient.patch()
                .uri("/api/product/{productId}", productId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(product)
                .exchange()
                .expectStatus().isOk();
    }
}