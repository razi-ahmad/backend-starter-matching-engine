package io.bux.matchingengine.validation;

import io.bux.matchingengine.dto.OrderRequest;
import io.bux.matchingengine.engine.Order;

import java.util.Objects;

import static io.bux.matchingengine.util.MessageConstant.*;

public class OrderValidation {
    private OrderValidation() {

    }
    public static void validateOrder(Order order) {
        if (Objects.isNull(order)) {
            throw new IllegalArgumentException(EMPTY_ORDER_ERROR);
        }

        if (Objects.isNull(order.getOrderId())) {
            throw new IllegalArgumentException(EMPTY_ORDER_ID_ERROR);
        }

        if (Objects.isNull(order.getPrice())) {
            throw new IllegalArgumentException(EMPTY_PRICE_ERROR);
        }

        if (Objects.isNull(order.getDirection())) {
            throw new IllegalArgumentException(EMPTY_DIRECTION_ERROR);
        }

        if (Objects.isNull(order.getAmount())) {
            throw new IllegalArgumentException(EMPTY_AMOUNT_ERROR);
        }

        if (order.getAmount() < Double.MIN_VALUE)
            throw new ArithmeticException(NEGATIVE_AMOUNT_ERROR);
    }

    public static void validateOrderRequest(OrderRequest request){
        if (Objects.isNull(request)) {
            throw new IllegalArgumentException(EMPTY_ORDER_REQUEST_ERROR);
        }

        if (Objects.isNull(request.asset())) {
            throw new IllegalArgumentException(EMPTY_ORDER_REQUEST_ASSET_ERROR);
        }

        if (Objects.isNull(request.price())) {
            throw new IllegalArgumentException(EMPTY_ORDER_REQUEST_PRICE_ERROR);
        }

        if (Objects.isNull(request.direction())) {
            throw new IllegalArgumentException(EMPTY_ORDER_REQUEST_DIRECTION_ERROR);
        }

        if (Objects.isNull(request.amount())) {
            throw new IllegalArgumentException(EMPTY_ORDER_REQUEST_AMOUNT_ERROR);
        }

        if (request.amount() < Double.MIN_VALUE)
            throw new ArithmeticException(NEGATIVE_ORDER_REQUEST_AMOUNT_ERROR);
    }
}
