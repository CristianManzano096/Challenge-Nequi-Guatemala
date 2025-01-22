package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.Branch;
import co.com.bancolombia.model.Franchise;
import co.com.bancolombia.model.Product;
import co.com.bancolombia.model.gateway.FranchiseRepository;
import co.com.bancolombia.r2dbc.domain.FranchiseEntity;
import co.com.bancolombia.r2dbc.domain.ProductEntity;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.mapper.BranchMapper;
import co.com.bancolombia.r2dbc.mapper.FranchiseMapper;
import co.com.bancolombia.r2dbc.mapper.ProductMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FranchiseEntityRepositoryAdapter implements FranchiseRepository{

    private final FranchiseEntityRepository franchiseEntityRepository;
    private final BranchEntityRepository branchEntityRepository;
    private final ProductEntityRepository productEntityRepository;
    private final FranchiseMapper mapper;
    private final BranchMapper branchMapper;
    private final ProductMapper productMapper;
    @Override
    public Mono<Franchise> create(Franchise franchise) {
        return Mono.just(franchise)
                .map(mapper::franchiseToFranchiseEntity)
                .flatMap(franchiseEntityRepository::save)
                .map(mapper::franchiseEntityToFranchise);
    }

    @Override
    public Flux<Franchise> getAll() {
        return franchiseEntityRepository.findAll().map(mapper::franchiseEntityToFranchise);
    }

    @Override
    public Mono<Franchise> getMaxProductByBranch(Integer id) {
        return Mono.just(id)
                .flatMap(this::findFranchiseById)
                .flatMap(this::addBranchesToFranchise)
                .flatMap(this::addProductsToBranches);
    }

    private Mono<Franchise> findFranchiseById(Integer id) {
        return franchiseEntityRepository.findById(id)
                .map(mapper::franchiseEntityToFranchise);
    }

    private Mono<Franchise> addBranchesToFranchise(Franchise franchise) {
        return branchEntityRepository.findAllByFranchiseId(franchise.getId())
                .map(branchMapper::branchEntityToBranch)
                .collectList()
                .map(branches -> {
                    franchise.setBranches(branches);
                    return franchise;
                });
    }

    private Mono<Franchise> addProductsToBranches(Franchise franchise) {
        return Flux.fromIterable(franchise.getBranches())
                .flatMap(this::addProductsToBranch)
                .collectList()
                .map(branches -> {
                    franchise.setBranches(branches);
                    return franchise;
                });
    }

    private Mono<Branch> addProductsToBranch(Branch branch) {
        return productEntityRepository.findAllByBranchId(branch.getId())
                .map(productMapper::productEntityToProduct)
                .reduce((product1, product2) -> product1.getStock() > product2.getStock() ? product1 : product2)
                .map(maxStockProduct -> {
                    branch.setProducts(Collections.singletonList(maxStockProduct));
                    return branch;
                });
    }
    @Override
    public Mono<Franchise> updateFranchise(Franchise franchise, Integer id) {
        return Mono.just(id)
                .flatMap(franchiseEntityRepository::findById)
                .switchIfEmpty(Mono.error(new RuntimeException("Franchise not found for id: " + id)))
                .flatMap(franchiseEntity -> {
                    Optional.ofNullable(franchise.getName()).ifPresent(franchiseEntity::setName);
                    return franchiseEntityRepository.save(franchiseEntity);
                }).map(mapper::franchiseEntityToFranchise);
    }

}