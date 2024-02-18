package io.bux.matchingengine.dao;

import io.bux.matchingengine.domain.OrderModel;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class OrderDaoImpl implements OrderDao {

    private final AtomicLong index;
    private final Map<Long, OrderModel> orders;

    public OrderDaoImpl() {
        index = new AtomicLong();
        orders = new HashMap<>();
    }

    public Optional<OrderModel> getById(Long id) {
        return Optional.ofNullable(orders.get(id));
    }

    public synchronized OrderModel save(OrderModel order) {
        order.setId(index.getAndIncrement());
        orders.put(order.getId(), order);
        return order;
    }
}
