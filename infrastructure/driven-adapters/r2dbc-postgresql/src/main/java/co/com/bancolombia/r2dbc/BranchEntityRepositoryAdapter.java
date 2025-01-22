package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.Branch;
import co.com.bancolombia.model.gateway.BranchRepository;
import co.com.bancolombia.r2dbc.domain.BranchEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class BranchEntityRepositoryAdapter implements BranchRepository {

    private final BranchEntityRepository branchEntityRepository;
    @Override
    public Mono<Branch> createBranchWithFranchise(Branch branch) {
        return branchEntityRepository.save(BranchEntity.builder()
                .franchiseId(branch.getFranchiseId())
                .name(branch.getName())
                .build()
                ).map(branchEntity -> Branch.builder()
                .id(branchEntity.getId())
                .name(branchEntity.getName())
                .franchiseId(branchEntity.getFranchiseId())
                .build());
    }
}