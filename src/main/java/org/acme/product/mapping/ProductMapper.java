package org.acme.product.mapping;

import java.math.BigDecimal;
import org.acme.product.domain.ProductItem;
import org.acme.product.persistence.ProductEntity;
import org.acme.product.proto.CreateProductRequest;
import org.acme.product.proto.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface ProductMapper {
    ProductEntity toEntity(ProductItem item);

    ProductItem toDomain(ProductEntity entity);

    Product toProto(ProductItem item);

    default ProductItem fromCreateRequest(CreateProductRequest request) {
        return new ProductItem(null, request.getName(), BigDecimal.valueOf(request.getPrice()));
    }
}
