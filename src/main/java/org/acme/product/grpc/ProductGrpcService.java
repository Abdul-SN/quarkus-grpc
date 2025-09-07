package org.acme.product.grpc;

import io.grpc.Status;
import io.quarkus.grpc.GrpcService;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import java.util.List;
import org.acme.product.application.ProductService;
import org.acme.product.mapping.ProductMapper;
import org.acme.product.proto.CreateProductRequest;
import org.acme.product.proto.GetProductRequest;
import org.acme.product.proto.ListProductsRequest;
import org.acme.product.proto.ListProductsResponse;
import org.acme.product.proto.MutinyProductServiceGrpc;
import org.acme.product.proto.Product;

@GrpcService
public class ProductGrpcService implements MutinyProductServiceGrpc.MutinyProductService {

    @Inject ProductService service;
    @Inject ProductMapper mapper;

    @Override
    @Blocking
    public Uni<Product> create(CreateProductRequest request) {
        return Uni.createFrom().item(() -> mapper.toProto(service.create(mapper.fromCreateRequest(request))));
    }

    @Override
    @Blocking
    public Uni<Product> get(GetProductRequest request) {
        return Uni.createFrom().item(() -> {
            try {
                return mapper.toProto(service.get(request.getId()));
            } catch (NotFoundException e) {
                throw Status.NOT_FOUND.withDescription("Product not found").asRuntimeException();
            }
        });
    }

    @Override
    @Blocking
    public Uni<ListProductsResponse> list(ListProductsRequest request) {
        return Uni.createFrom().item(() -> {
            List<Product> products = service.list().stream().map(mapper::toProto).toList();
            return ListProductsResponse.newBuilder().addAllProducts(products).build();
        });
    }
}
