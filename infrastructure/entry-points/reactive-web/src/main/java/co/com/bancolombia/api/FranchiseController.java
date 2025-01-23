package co.com.bancolombia.api;
import co.com.bancolombia.api.handler.ResponseHandler;
import co.com.bancolombia.model.Branch;
import co.com.bancolombia.model.Franchise;
import co.com.bancolombia.model.Product;
import co.com.bancolombia.model.enums.ResponseCode;
import co.com.bancolombia.usecase.franchise.FranchiseUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class FranchiseController {

    private final FranchiseUseCase franchiseUseCase;
    @GetMapping(path = "/franchise")
    public Mono<ResponseEntity<Map<String, Object>>> getFranchises() {
        return franchiseUseCase.getAllFranchisesWithBranches()
                .collectList()
                .flatMap(franchises -> ResponseHandler.success(franchises, ResponseCode.SUCCESS));
    }
    @PostMapping(path = "/franchise")
    public Mono<ResponseEntity<Map<String, Object>>> addFranchise(@RequestBody Franchise franchise) {
        return franchiseUseCase.createFranchise(franchise)
                .flatMap(franchiseResponse -> ResponseHandler.success(franchiseResponse,ResponseCode.SUCCESS));
    }
    @GetMapping(path = "/franchise/{id}")
    public Mono<ResponseEntity<Map<String, Object>>> getMaxProducts(@PathVariable("id") Integer id) {
        return franchiseUseCase.getMaxProductByBranch(id)
                .flatMap(franchiseResponse -> ResponseHandler.success(franchiseResponse,ResponseCode.SUCCESS));
    }
    @PatchMapping(path = "/franchise/{id}")
    public Mono<ResponseEntity<Map<String, Object>>> updateFranchise(@RequestBody Franchise franchise, @PathVariable("id") Integer franchiseId) {
        return franchiseUseCase.updateFranchise(franchise, franchiseId)
                .flatMap(franchiseResponse -> ResponseHandler.success(franchiseResponse,ResponseCode.SUCCESS));
    }
}
