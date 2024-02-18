package io.bux.matchingengine.service;

import io.bux.matchingengine.dto.OrderRequest;
import io.bux.matchingengine.dto.OrderResponse;

public interface OrderBookService {
    OrderResponse placeOrder(OrderRequest orderRequest);

    OrderResponse getOrder(Long orderId);
}
