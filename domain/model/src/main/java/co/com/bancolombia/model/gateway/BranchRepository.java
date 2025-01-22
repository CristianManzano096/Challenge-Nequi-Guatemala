package co.com.bancolombia.model.gateway;

import co.com.bancolombia.model.Branch;
import co.com.bancolombia.model.Franchise;
import reactor.core.publisher.Mono;

public interface BranchRepository {
    Mono<Branch> createBranchWithFranchise(Branch branch);
}
