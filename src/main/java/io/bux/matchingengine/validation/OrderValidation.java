package io.bux.matchingengine.validation;

import io.bux.matchingengine.dto.OrderRequest;
import io.bux.matchingengine.engine.Order;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Objects;

import static io.bux.matchingengine.util.MessageConstant.*;

@Slf4j
public class OrderValidation {
    private OrderValidation() {

    }

    public static void validateOrder(Order order) {
        log.info("====>> Validate order {}", order);
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
        if (Objects.isNull(order.getAsset())) {
            throw new IllegalArgumentException(EMPTY_ASSET_ERROR);
        }
        if (Objects.isNull(order.getAmount())) {
            throw new IllegalArgumentException(EMPTY_AMOUNT_ERROR);
        }

        if (BigDecimal.ZERO.compareTo(order.getAmount()) >= 0) {
            throw new ArithmeticException(ZERO_AMOUNT_ERROR);
        }
    }

    public static void validateOrderRequest(OrderRequest request) {
        log.info("====>> Validate order request {}", request);
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
        if (BigDecimal.ZERO.compareTo(request.amount()) >= 0) {
            throw new ArithmeticException(NEGATIVE_ORDER_REQUEST_AMOUNT_ERROR);
        }
    }
}
