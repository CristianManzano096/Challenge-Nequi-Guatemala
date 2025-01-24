package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.Branch;
import co.com.bancolombia.model.Franchise;
import co.com.bancolombia.model.Product;
import co.com.bancolombia.r2dbc.domain.BranchEntity;
import co.com.bancolombia.r2dbc.domain.FranchiseEntity;
import co.com.bancolombia.r2dbc.domain.ProductEntity;
import co.com.bancolombia.r2dbc.mapper.BranchMapper;
import co.com.bancolombia.r2dbc.mapper.FranchiseMapper;
import co.com.bancolombia.r2dbc.mapper.ProductMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class FranchiseEntityRepositoryAdapterTest {
    private FranchiseEntityRepositoryAdapter adapter;

    @Mock
    private FranchiseEntityRepository franchiseEntityRepository;

    @Mock
    private BranchEntityRepository branchEntityRepository;

    @Mock
    private ProductEntityRepository productEntityRepository;

    @Mock
    private FranchiseMapper franchiseMapper;

    @Mock
    private BranchMapper branchMapper;

    @Mock
    private ProductMapper productMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        adapter = new FranchiseEntityRepositoryAdapter(
                franchiseEntityRepository, branchEntityRepository, productEntityRepository,
                franchiseMapper, branchMapper, productMapper);
    }

    @Test
    void create_ShouldSaveAndReturnFranchise() {
        Franchise franchise = new Franchise(1, "Franchise 1", null);
        FranchiseEntity franchiseEntity = new FranchiseEntity(1, "Franchise 1",null);

        Mockito.when(franchiseMapper.franchiseToFranchiseEntity(franchise))
                .thenReturn(franchiseEntity);
        Mockito.when(franchiseEntityRepository.save(franchiseEntity))
                .thenReturn(Mono.just(franchiseEntity));
        Mockito.when(franchiseMapper.franchiseEntityToFranchise(franchiseEntity))
                .thenReturn(franchise);

        Mono<Franchise> result = adapter.create(franchise);

        StepVerifier.create(result)
                .expectNext(franchise)
                .verifyComplete();

        Mockito.verify(franchiseMapper).franchiseToFranchiseEntity(franchise);
        Mockito.verify(franchiseEntityRepository).save(franchiseEntity);
        Mockito.verify(franchiseMapper).franchiseEntityToFranchise(franchiseEntity);
    }
    @Test
    void getAll_ShouldReturnListOfFranchises() {
        FranchiseEntity franchiseEntity = new FranchiseEntity(1, "Franchise 1",null);
        Franchise franchise = new Franchise(1, "Franchise 1", null);

        Mockito.when(franchiseEntityRepository.findAll())
                .thenReturn(Flux.just(franchiseEntity));
        Mockito.when(franchiseMapper.franchiseEntityToFranchise(franchiseEntity))
                .thenReturn(franchise);

        Flux<Franchise> result = adapter.getAll();

        StepVerifier.create(result)
                .expectNext(franchise)
                .verifyComplete();

        Mockito.verify(franchiseEntityRepository).findAll();
        Mockito.verify(franchiseMapper).franchiseEntityToFranchise(franchiseEntity);
    }

//    @Test
//    void getMaxProductByBranch_ShouldReturnFranchiseWithMaxStockProduct() {
//        Integer franchiseId = 1;
//
//        FranchiseEntity franchiseEntity = new FranchiseEntity(franchiseId, "Franchise 1",null);
//        Franchise franchise = new Franchise(franchiseId, "Franchise 1", null);
//
//        BranchEntity branchEntity = new BranchEntity(1, "Branch 1", franchiseId,null);
//        Branch branch = new Branch(1, "Branch 1", null, franchiseId);
//
//        ProductEntity productEntity1 = new ProductEntity(1, "Product 1", 10, 1,null);
//        ProductEntity productEntity2 = new ProductEntity(2, "Product 2", 20, 1,null);
//        Product maxProduct = new Product(2, "Product 2", 20, 1);
//
//        Mockito.when(franchiseEntityRepository.findById(franchiseId))
//                .thenReturn(Mono.just(franchiseEntity));
//        Mockito.when(franchiseMapper.franchiseEntityToFranchise(franchiseEntity))
//                .thenReturn(franchise);
//
//        Mockito.when(branchEntityRepository.findAllByFranchiseId(franchiseId))
//                .thenReturn(Flux.just(branchEntity));
//        Mockito.when(branchMapper.branchEntityToBranch(branchEntity))
//                .thenReturn(branch);
//
//        Mockito.when(productEntityRepository.findAllByBranchId(branch.getId()))
//                .thenReturn(Flux.just(productEntity1, productEntity2));
//        Mockito.when(productMapper.productEntityToProduct(productEntity2))
//                .thenReturn(maxProduct);
//
//        Mono<Franchise> result = adapter.getMaxProductByBranch(franchiseId);
//
//        StepVerifier.create(result)
//                .assertNext(franchiseWithProducts -> {
//                    Branch branchWithProduct = franchiseWithProducts.getBranches().get(0);
//                    Product product = branchWithProduct.getProducts().get(0);
//                    assert product.getId().equals(maxProduct.getId());
//                })
//                .verifyComplete();
//
//        Mockito.verify(franchiseEntityRepository).findById(franchiseId);
//        Mockito.verify(branchEntityRepository).findAllByFranchiseId(franchiseId);
//        Mockito.verify(productEntityRepository).findAllByBranchId(branch.getId());
//    }

    @Test
    void updateFranchise_ShouldUpdateAndReturnFranchise() {
        Integer franchiseId = 1;
        Franchise existingFranchise = new Franchise(franchiseId, "Franchise 1", null);
        FranchiseEntity existingEntity = new FranchiseEntity(franchiseId, "Franchise 1",null);

        Franchise updatedFranchise = new Franchise(franchiseId, "Updated Franchise", null);
        FranchiseEntity updatedEntity = new FranchiseEntity(franchiseId, "Updated Franchise",null);

        Mockito.when(franchiseEntityRepository.findById(franchiseId))
                .thenReturn(Mono.just(existingEntity));
        Mockito.when(franchiseEntityRepository.save(existingEntity))
                .thenReturn(Mono.just(updatedEntity));
        Mockito.when(franchiseMapper.franchiseEntityToFranchise(updatedEntity))
                .thenReturn(updatedFranchise);

        Mono<Franchise> result = adapter.updateFranchise(updatedFranchise, franchiseId);

        StepVerifier.create(result)
                .expectNext(updatedFranchise)
                .verifyComplete();

        Mockito.verify(franchiseEntityRepository).findById(franchiseId);
        Mockito.verify(franchiseEntityRepository).save(existingEntity);
        Mockito.verify(franchiseMapper).franchiseEntityToFranchise(updatedEntity);
    }

}