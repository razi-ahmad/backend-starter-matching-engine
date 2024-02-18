package io.bux.matchingengine.dao;

import io.bux.matchingengine.domain.OrderModel;

import java.util.Optional;

public interface OrderDao {
    Optional<OrderModel> getById(Long id);

    OrderModel save(OrderModel order);

}
