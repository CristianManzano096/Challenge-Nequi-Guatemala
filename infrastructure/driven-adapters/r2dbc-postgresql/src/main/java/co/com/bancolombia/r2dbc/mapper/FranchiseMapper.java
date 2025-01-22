package co.com.bancolombia.r2dbc.mapper;

import co.com.bancolombia.model.Franchise;
import co.com.bancolombia.r2dbc.domain.FranchiseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FranchiseMapper {
    FranchiseMapper INSTANCE = Mappers.getMapper(FranchiseMapper.class);
    Franchise franchiseEntityToFranchise(FranchiseEntity franchiseEntity);
    FranchiseEntity franchiseToFranchiseEntity(Franchise franchise);
}