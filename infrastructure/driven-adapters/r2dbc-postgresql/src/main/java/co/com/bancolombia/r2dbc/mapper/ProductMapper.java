package co.com.bancolombia.r2dbc.mapper;

import co.com.bancolombia.model.Franchise;
import co.com.bancolombia.model.Product;
import co.com.bancolombia.r2dbc.domain.FranchiseEntity;
import co.com.bancolombia.r2dbc.domain.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    Product productEntityToProduct(ProductEntity productEntity);
    ProductEntity productToProductEntity(Product product);
}