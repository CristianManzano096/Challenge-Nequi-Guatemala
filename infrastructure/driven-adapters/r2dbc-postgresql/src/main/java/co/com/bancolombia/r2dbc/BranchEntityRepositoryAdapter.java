package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.Branch;
import co.com.bancolombia.model.Franchise;
import co.com.bancolombia.model.enums.ResponseCode;
import co.com.bancolombia.model.exception.CustomException;
import co.com.bancolombia.model.gateway.BranchRepository;
import co.com.bancolombia.r2dbc.domain.BranchEntity;
import co.com.bancolombia.r2dbc.mapper.BranchMapper;
import co.com.bancolombia.r2dbc.mapper.FranchiseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BranchEntityRepositoryAdapter implements BranchRepository {

    private final BranchEntityRepository branchEntityRepository;
    private final BranchMapper mapper;

    @Override
    public Mono<Branch> createBranchWithFranchise(Branch branch) {
        return Mono.just(branch)
            .map(mapper::branchToBranchEntity)
            .flatMap(branchEntityRepository::save)
            .map(mapper::branchEntityToBranch)
            .onErrorMap(e -> new CustomException(ResponseCode.DATABASE_ERROR, "Error creating branch: " + e.getMessage()));
    }

    @Override
    public Mono<Branch> updateBranch(Branch branch, Integer id) {
        return Mono.just(id)
                .flatMap(branchEntityRepository::findById)
                .switchIfEmpty(Mono.error(new CustomException(ResponseCode.NOT_FOUND,"No branch found for id: "+id)))
                .flatMap(branchEntity -> {
                    Optional.ofNullable(branch.getName()).ifPresent(branchEntity::setName);
                    return branchEntityRepository.save(branchEntity);
                }).map(mapper::branchEntityToBranch)
                .onErrorMap(e -> new CustomException(ResponseCode.DATABASE_ERROR, "Error updating branch: " + e.getMessage()));
    }
}