package co.com.bancolombia.usecase;

import co.com.bancolombia.model.Branch;
import co.com.bancolombia.model.Franchise;
import co.com.bancolombia.model.Product;
import co.com.bancolombia.model.gateway.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FranchiseUseCase {

    private final FranchiseRepository franchiseRepository;

    public Flux<Franchise> getAllFranchisesWithBranches() {
        return franchiseRepository.getAll();
    }

    public Mono<Franchise> getMaxProductByBranch(Integer id) {
        return franchiseRepository.getMaxProductByBranch(id);
    }

    public Mono<Franchise> createFranchise(Franchise franchise){
        return franchiseRepository.create(franchise);
    }

    public Mono<Franchise> updateFranchise(Franchise franchise, Integer id){
        return franchiseRepository.updateFranchise(franchise, id);
    }
}
