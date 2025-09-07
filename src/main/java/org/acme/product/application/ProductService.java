package org.acme.product.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import java.util.List;
import org.acme.product.domain.ProductItem;
import org.acme.product.mapping.ProductMapper;
import org.acme.product.persistence.ProductEntity;
import org.acme.product.persistence.ProductRepository;

@ApplicationScoped
public class ProductService {

    @Inject ProductRepository repository;
    @Inject ProductMapper mapper;

    @Transactional
    public ProductItem create(ProductItem item) {
        ProductEntity entity = mapper.toEntity(item);
        repository.persist(entity);
        return mapper.toDomain(entity);
    }

    public ProductItem get(long id) {
        return repository.findByIdOptional(id)
                .map(mapper::toDomain)
                .orElseThrow(NotFoundException::new);
    }

    public List<ProductItem> list() {
        return repository.listAll().stream().map(mapper::toDomain).toList();
    }
}
