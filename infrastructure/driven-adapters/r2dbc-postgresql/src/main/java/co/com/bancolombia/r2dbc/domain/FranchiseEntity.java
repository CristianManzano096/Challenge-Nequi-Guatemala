package co.com.bancolombia.r2dbc.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("franchise")
public class FranchiseEntity {
    @Id
    private Integer id;

    @Column("name")
    private String name;

    @Transient
    private List<BranchEntity> branches;
}
