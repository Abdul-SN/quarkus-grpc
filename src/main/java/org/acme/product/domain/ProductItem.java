package org.acme.product.domain;

import java.math.BigDecimal;

public record ProductItem(Long id, String name, BigDecimal price) {}
