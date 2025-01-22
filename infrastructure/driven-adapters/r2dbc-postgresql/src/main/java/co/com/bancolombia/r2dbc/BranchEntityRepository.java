package co.com.bancolombia.r2dbc;

import co.com.bancolombia.r2dbc.domain.BranchEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BranchEntityRepository extends ReactiveCrudRepository<BranchEntity, Integer>, ReactiveQueryByExampleExecutor<BranchEntity> {
}