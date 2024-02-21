package io.bux.matchingengine.util;

import io.bux.matchingengine.domain.OrderModel;
import io.bux.matchingengine.domain.TradeModel;
import io.bux.matchingengine.dto.OrderRequest;
import io.bux.matchingengine.dto.OrderResponse;
import io.bux.matchingengine.dto.TradeResponse;
import io.bux.matchingengine.engine.Order;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.stream.Collectors;

public final class MapperUtil {

    private MapperUtil() {
    }

    public static OrderResponse mapOrderModelToDto(OrderModel orderModel) {
        return new OrderResponse(orderModel.getId(),
                orderModel.getTimestamp(),
                orderModel.getAsset(),
                orderModel.getPrice(),
                orderModel.getAmount(),
                orderModel.getDirection(),
                orderModel.getTrades()
                        .stream()
                        .map(t -> new TradeResponse(t.getOrderId(), t.getAmount(), t.getPrice()))
                        .sorted(Comparator.comparingLong(TradeResponse::orderId))
                        .collect(Collectors.toList()),
                orderModel.getAmount().subtract(orderModel.getTrades().stream().map(TradeModel::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add)));
    }

    public static OrderModel mapOrderRequestToOrderModel(OrderRequest request) {
        return OrderModel.builder()
                .asset(request.asset())
                .price(request.price())
                .amount(request.amount())
                .direction(request.direction())
                .build();
    }

    public static Order mapOrderModelToOrder(OrderModel orderModel) {
        return new Order(
                orderModel.getId(),
                orderModel.getAmount(),
                orderModel.getPrice(),
                orderModel.getDirection(),
                orderModel.getTimestamp(),
                orderModel.getAsset()
        );
    }
}
