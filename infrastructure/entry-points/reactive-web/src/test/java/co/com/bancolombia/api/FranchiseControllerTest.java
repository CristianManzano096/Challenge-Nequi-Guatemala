package co.com.bancolombia.api;

import co.com.bancolombia.api.handler.ResponseHandler;
import co.com.bancolombia.model.Branch;
import co.com.bancolombia.model.Franchise;
import co.com.bancolombia.model.enums.ResponseCode;
import co.com.bancolombia.usecase.BranchUseCase;
import co.com.bancolombia.usecase.FranchiseUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FranchiseControllerTest {

    @Mock
    private FranchiseUseCase franchiseUseCase;

    @InjectMocks
    private FranchiseController franchiseController;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToController(franchiseController).build();
    }

    @Test
    void getFranchise_ShouldReturnSuccessResponse() {

        Franchise franchise1 = new Franchise(1, "Franchise1", null);
        Franchise franchise2 = new Franchise(2, "Franchise2", null);
        List<Franchise> franchises = List.of(franchise1, franchise2);

        Mockito.when(franchiseUseCase.getAllFranchisesWithBranches()).thenReturn(Flux.fromIterable(franchises));

        webTestClient.get()
                .uri("/api/franchise")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void addFranchise_ShouldReturnSuccessResponse() {
        Franchise franchise = new Franchise(1, "New Franchise", null);

        Mockito.when(franchiseUseCase.createFranchise(franchise)).thenReturn(Mono.just(franchise));

        // Act & Assert
        webTestClient.post()
                .uri("/api/franchise")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(franchise)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void getMaxProducts_ShouldReturnSuccessResponse() {
        int franchiseId = 1;
        Franchise maxProductsResponse = new Franchise(1, "New Franchise", null);

        Mockito.when(franchiseUseCase.getMaxProductByBranch(franchiseId)).thenReturn(Mono.just(maxProductsResponse));

        // Act & Assert
        webTestClient.get()
                .uri("/api/franchise/{id}", franchiseId)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void updateFranchise_ShouldReturnSuccessResponse() {
        int franchiseId = 1;
        Franchise franchise = new Franchise(franchiseId, "Updated Franchise", null);

        Mockito.when(franchiseUseCase.updateFranchise(franchise, franchiseId)).thenReturn(Mono.just(franchise));

        // Act & Assert
        webTestClient.patch()
                .uri("/api/franchise/{id}", franchiseId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(franchise)
                .exchange()
                .expectStatus().isOk();
    }
}