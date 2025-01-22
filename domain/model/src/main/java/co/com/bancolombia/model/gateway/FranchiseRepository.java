package co.com.bancolombia.model.gateway;

import co.com.bancolombia.model.Branch;
import co.com.bancolombia.model.Franchise;
import co.com.bancolombia.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FranchiseRepository {
    Mono<Franchise> create(Franchise franchise);
    Flux<Franchise> getAll();
    Mono<Franchise> getMaxProductByBranch(Integer id);

    Mono<Franchise> updateFranchise(Franchise franchise, Integer id);
}
