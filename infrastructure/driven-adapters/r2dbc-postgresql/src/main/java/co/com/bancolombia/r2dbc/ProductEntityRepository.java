package co.com.bancolombia.r2dbc;

import co.com.bancolombia.r2dbc.domain.ProductEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductEntityRepository extends ReactiveCrudRepository<ProductEntity, Integer> {
    Flux<ProductEntity> findAllByBranchId(Integer id);
}