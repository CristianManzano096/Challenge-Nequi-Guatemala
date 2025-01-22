package co.com.bancolombia.r2dbc;

import co.com.bancolombia.r2dbc.domain.FranchiseEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface FranchiseEntityRepository extends ReactiveCrudRepository<FranchiseEntity, Integer>{
}