package co.com.bancolombia.r2dbc.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("product")
public class ProductEntity {
    @Id
    private Integer id;

    @Column("name")
    private String name;

    @Column("stock")
    private Integer stock;

    @Column("branch_id")
    private Integer branchId;

    @Transient
    private BranchEntity branch;
}
