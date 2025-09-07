# Product Service

Демонстрационный сервис на Quarkus с gRPC для управления товарами.

## Запуск
```bash
mvn quarkus:dev
```

## Примеры gRPC
```bash
grpcurl -plaintext -d '{"name":"Coffee","price":10.0}' localhost:9000 product.v1.ProductService/Create
grpcurl -plaintext localhost:9000 product.v1.ProductService/List
```
