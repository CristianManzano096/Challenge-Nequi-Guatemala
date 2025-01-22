package co.com.bancolombia.api;
import co.com.bancolombia.model.Branch;
import co.com.bancolombia.usecase.franchise.BranchUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class BranchController {

    private final BranchUseCase branchUseCase;

    @PostMapping(path = "/branch")
    public Mono<Branch> addFranchise(@RequestBody Branch branch) {
        System.out.println("*******"+branch.toString());
        return branchUseCase.createBranch(branch);
    }
}
