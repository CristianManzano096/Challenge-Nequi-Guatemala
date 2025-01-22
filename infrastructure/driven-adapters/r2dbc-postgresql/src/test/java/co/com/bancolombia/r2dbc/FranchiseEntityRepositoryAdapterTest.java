package co.com.bancolombia.r2dbc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class FranchiseEntityRepositoryAdapterTest {
    // TODO: change four you own tests

    @InjectMocks
    FranchiseEntityRepositoryAdapter repositoryAdapter;

    @Mock
    FranchiseEntityRepository repository;

    @Mock
    ObjectMapper mapper;

    @Test
    void mustFindValueById() {
    }
}
