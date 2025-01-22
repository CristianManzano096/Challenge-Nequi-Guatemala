package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.Branch;
import co.com.bancolombia.model.Franchise;
import co.com.bancolombia.model.Product;
import co.com.bancolombia.model.gateway.FranchiseRepository;
import co.com.bancolombia.r2dbc.domain.FranchiseEntity;
import co.com.bancolombia.r2dbc.domain.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FranchiseEntityRepositoryAdapter implements FranchiseRepository{

    private final FranchiseEntityRepository franchiseEntityRepository;
    private final BranchEntityRepository branchEntityRepository;
    private final ProductEntityRepository productEntityRepository;
    @Override
    public Mono<Franchise> create(Franchise franchise) {
        return  franchiseEntityRepository.save(FranchiseEntity.builder()
                .name(franchise.getName())
                .build()).map(franchiseEntity -> Franchise.builder()
                .id(franchiseEntity.getId())
                .name(franchiseEntity.getName())
                .build());
    }

    @Override
    public Flux<Franchise> getAll() {
        return franchiseEntityRepository.findAll().map(franchiseEntity ->
                Franchise.builder()
                        .id(franchiseEntity.getId())
                        .name(franchiseEntity.getName())
                        .build());
    }

    @Override
    public Flux<Branch> getMaxProductByBranch(Integer id) {
        return productEntityRepository.findAll()
                .groupBy(ProductEntity::getBranchId)
                .flatMap(group -> group
                        .reduce((product1, product2) ->
                                product1.getStock() > product2.getStock() ? product1 : product2)
                )
                .flatMap(productEntity -> branchEntityRepository.findById(productEntity.getBranchId())
                        .map(branchEntity -> Branch.builder()
                                .id(branchEntity.getId())
                                .franchiseId(branchEntity.getFranchiseId())
                                .name(branchEntity.getName())
                                .products(List.of(Product.builder()
                                        .id(productEntity.getId())
                                        .name(productEntity.getName())
                                        .stock(productEntity.getStock())
                                        .branchId(productEntity.getBranchId())
                                        .build()))
                                .build())
                )
                .filter(branch -> branch.getFranchiseId().equals(id));
    }
}