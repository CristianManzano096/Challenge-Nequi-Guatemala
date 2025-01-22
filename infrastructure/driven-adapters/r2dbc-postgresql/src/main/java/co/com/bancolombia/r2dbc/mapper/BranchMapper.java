package co.com.bancolombia.r2dbc.mapper;

import co.com.bancolombia.model.Branch;
import co.com.bancolombia.model.Franchise;
import co.com.bancolombia.r2dbc.domain.BranchEntity;
import co.com.bancolombia.r2dbc.domain.FranchiseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BranchMapper {
    BranchMapper INSTANCE = Mappers.getMapper(BranchMapper.class);
    Branch branchEntityToBranch(BranchEntity branchEntity);
    BranchEntity branchToBranchEntity(Branch branch);
}