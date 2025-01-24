package co.com.bancolombia.api;

import co.com.bancolombia.api.handler.ResponseHandler;
import co.com.bancolombia.model.Branch;
import co.com.bancolombia.model.enums.ResponseCode;
import co.com.bancolombia.usecase.BranchUseCase;
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


class BranchControllerTest {

    @Mock
    private BranchUseCase branchUseCase;

    @InjectMocks
    private BranchController branchController;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToController(branchController).build();
    }

    @Test
    void addBranch_ShouldReturnSuccessResponse() {
        Branch branch = new Branch();

        Map<String, Object> successResponse = ResponseHandler.successResponse(branch, ResponseCode.SUCCESS);
        Mockito.when(branchUseCase.createBranch(branch)).thenReturn(Mono.just(branch));

        webTestClient.post()
                .uri("/api/branch")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(branch)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void updateBranch_ShouldReturnSuccessResponse() {
        Integer branchId = 1;
        Branch branch = new Branch();
        Map<String, Object> successResponse = ResponseHandler.successResponse(branch, ResponseCode.SUCCESS);

        Mockito.when(branchUseCase.updateFranchise(branch, branchId)).thenReturn(Mono.just(branch));

        webTestClient.patch()
                .uri("/api/branch/{id}", branchId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(branch)
                .exchange()
                .expectStatus().isOk();
    }
}