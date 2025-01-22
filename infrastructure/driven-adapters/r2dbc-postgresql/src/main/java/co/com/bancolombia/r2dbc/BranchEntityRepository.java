package co.com.bancolombia.r2dbc;

import co.com.bancolombia.r2dbc.domain.BranchEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface BranchEntityRepository extends ReactiveCrudRepository<BranchEntity, Integer>, ReactiveQueryByExampleExecutor<BranchEntity> {
    Flux<BranchEntity> findAllByFranchiseId(Integer franchiseId);
}