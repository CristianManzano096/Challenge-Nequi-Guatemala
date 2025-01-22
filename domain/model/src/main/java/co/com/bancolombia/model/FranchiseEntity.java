package co.com.bancolombia.model;

import lombok.Data;

import java.util.List;

@Data
public class FranchiseEntity {
    private String name;
    private List<BranchEntity> branches;

}
