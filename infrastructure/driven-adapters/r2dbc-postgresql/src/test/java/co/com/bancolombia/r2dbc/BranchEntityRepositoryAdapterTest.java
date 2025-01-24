package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.Branch;
import co.com.bancolombia.model.enums.ResponseCode;
import co.com.bancolombia.model.exception.CustomException;
import co.com.bancolombia.r2dbc.domain.BranchEntity;
import co.com.bancolombia.r2dbc.mapper.BranchMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class BranchEntityRepositoryAdapterTest {

    private BranchEntityRepositoryAdapter adapter;

    @Mock
    private BranchEntityRepository branchEntityRepository;

    @Mock
    private BranchMapper branchMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        adapter = new BranchEntityRepositoryAdapter(branchEntityRepository, branchMapper);
    }

    @Test
    void createBranchWithFranchise_ShouldSaveAndReturnBranch() {
        Branch branch = new Branch(null, "New Branch", null, 1);
        BranchEntity branchEntity = new BranchEntity(null, "New Branch", 1,null);

        Mockito.when(branchMapper.branchToBranchEntity(branch))
                .thenReturn(branchEntity);
        Mockito.when(branchEntityRepository.save(branchEntity))
                .thenReturn(Mono.just(branchEntity));
        Mockito.when(branchMapper.branchEntityToBranch(branchEntity))
                .thenReturn(branch);

        Mono<Branch> result = adapter.createBranchWithFranchise(branch);

        StepVerifier.create(result)
                .expectNext(branch)
                .verifyComplete();

        Mockito.verify(branchMapper).branchToBranchEntity(branch);
        Mockito.verify(branchEntityRepository).save(branchEntity);
        Mockito.verify(branchMapper).branchEntityToBranch(branchEntity);
    }
    @Test
    void updateBranch_ShouldUpdateAndReturnBranch() {
        Integer branchId = 1;
        Branch existingBranch = new Branch(branchId, "Existing Branch", null, 1);
        BranchEntity existingBranchEntity = new BranchEntity(branchId, "Existing Branch", 1,null);

        Branch updatedBranch = new Branch(branchId, "Updated Branch", null, 1);
        BranchEntity updatedBranchEntity = new BranchEntity(branchId, "Updated Branch", 1,null);

        Mockito.when(branchEntityRepository.findById(branchId))
                .thenReturn(Mono.just(existingBranchEntity));
        Mockito.when(branchEntityRepository.save(existingBranchEntity))
                .thenReturn(Mono.just(updatedBranchEntity));
        Mockito.when(branchMapper.branchEntityToBranch(updatedBranchEntity))
                .thenReturn(updatedBranch);

        Mono<Branch> result = adapter.updateBranch(updatedBranch, branchId);

        StepVerifier.create(result)
                .expectNext(updatedBranch)
                .verifyComplete();

        Mockito.verify(branchEntityRepository).findById(branchId);
        Mockito.verify(branchEntityRepository).save(existingBranchEntity);
        Mockito.verify(branchMapper).branchEntityToBranch(updatedBranchEntity);
    }

    @Test
    void testUpdateBranchDatabaseError() {
        Integer branchId = 1;
        Branch branch = new Branch();
        branch.setName("Updated Branch");

        BranchEntity existingBranchEntity = new BranchEntity();
        existingBranchEntity.setId(branchId);
        existingBranchEntity.setName("Old Branch");

        Mockito.when(branchEntityRepository.findById(branchId)).thenReturn(Mono.just(existingBranchEntity));
        Mockito.when(branchEntityRepository.save(existingBranchEntity))
                .thenReturn(Mono.error(new RuntimeException("Database error")));

        StepVerifier.create(adapter.updateBranch(branch, branchId))
                .expectErrorMatches(throwable ->
                        throwable instanceof CustomException &&
                                ((CustomException) throwable).getResponseCode() == ResponseCode.DATABASE_ERROR &&
                                throwable.getMessage().contains("Error updating branch: Database error"))
                .verify();

        Mockito.verify(branchEntityRepository).findById(branchId);
        Mockito.verify(branchEntityRepository).save(existingBranchEntity);
    }

}