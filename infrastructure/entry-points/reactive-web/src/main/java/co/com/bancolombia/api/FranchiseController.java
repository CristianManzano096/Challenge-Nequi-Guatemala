package co.com.bancolombia.api;
import co.com.bancolombia.model.Branch;
import co.com.bancolombia.model.Franchise;
import co.com.bancolombia.model.Product;
import co.com.bancolombia.usecase.franchise.FranchiseUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class FranchiseController {

    private final FranchiseUseCase franchiseUseCase;
    @GetMapping(path = "/franchise")
    public Flux<Franchise> getFranchise() {
        return franchiseUseCase.getAllFranchisesWithBranches();
    }

    @PostMapping(path = "/franchise")
    public Mono<Franchise> addFranchise(@RequestBody Franchise franchise) {
        System.out.println("*******"+franchise.getName());
        return franchiseUseCase.createFranchise(franchise);
    }

    @GetMapping(path = "/franchise/{id}")
    public Flux<Branch> getMaxProducts(@PathVariable("id") Integer id) {
        System.out.println("*******"+id);
        return franchiseUseCase.getMaxProductByBranch(id);
    }
}
