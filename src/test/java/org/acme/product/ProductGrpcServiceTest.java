package org.acme.product;

import static org.assertj.core.api.Assertions.assertThat;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.quarkus.test.junit.QuarkusTest;
import org.acme.product.proto.CreateProductRequest;
import org.acme.product.proto.GetProductRequest;
import org.acme.product.proto.MutinyProductServiceGrpc;
import org.acme.product.proto.Product;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class ProductGrpcServiceTest {

    @Test
    void createAndGet() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9000).usePlaintext().build();
        MutinyProductServiceGrpc.MutinyProductServiceStub stub = MutinyProductServiceGrpc.newMutinyStub(channel);

        Product created = stub.create(
                CreateProductRequest.newBuilder().setName("Coffee").setPrice(10.0).build())
                .await().indefinitely();
        Product fetched = stub.get(
                GetProductRequest.newBuilder().setId(created.getId()).build())
                .await().indefinitely();

        assertThat(fetched.getName()).isEqualTo("Coffee");
        assertThat(fetched.getPrice()).isEqualTo(10.0);

        channel.shutdownNow();
    }
}
