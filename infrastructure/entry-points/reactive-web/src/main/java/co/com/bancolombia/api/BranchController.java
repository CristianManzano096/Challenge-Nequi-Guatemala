package co.com.bancolombia.api;
import co.com.bancolombia.api.handler.ResponseHandler;
import co.com.bancolombia.model.Branch;
import co.com.bancolombia.model.enums.ResponseCode;
import co.com.bancolombia.usecase.BranchUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class BranchController {

    private final BranchUseCase branchUseCase;

    @PostMapping(path = "/branch")
    public Mono<ResponseEntity<Map<String, Object>>> addBranch(@RequestBody Branch branch) {
        return branchUseCase.createBranch(branch)
                .flatMap(branchResponse -> ResponseHandler.success(branchResponse, ResponseCode.SUCCESS));
    }
    @PatchMapping(path = "/branch/{id}")
    public Mono<ResponseEntity<Map<String, Object>>> updateBranch(@RequestBody Branch branch, @PathVariable("id") Integer branchId) {
        return branchUseCase.updateFranchise(branch, branchId)
                .flatMap(branchResponse -> ResponseHandler.success(branchResponse, ResponseCode.SUCCESS));
    }
}
