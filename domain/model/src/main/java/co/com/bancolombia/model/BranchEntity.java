package co.com.bancolombia.model;

import lombok.Data;

import java.util.List;
@Data
public class BranchEntity {
    private String name;
    private List<ProductEntity> products;

}
