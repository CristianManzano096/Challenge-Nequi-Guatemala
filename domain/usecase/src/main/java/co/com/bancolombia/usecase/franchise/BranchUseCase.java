package co.com.bancolombia.usecase.franchise;

import co.com.bancolombia.model.Branch;
import co.com.bancolombia.model.Franchise;
import co.com.bancolombia.model.gateway.BranchRepository;
import co.com.bancolombia.model.gateway.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class BranchUseCase {

    private final BranchRepository branchRepository;

    public Mono<Branch> createBranch(Branch branch){
        return branchRepository.createBranchWithFranchise(branch);
    }
}
