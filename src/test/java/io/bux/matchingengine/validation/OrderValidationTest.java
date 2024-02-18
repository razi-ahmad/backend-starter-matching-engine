package io.bux.matchingengine.validation;

import io.bux.matchingengine.dto.OrderRequest;
import io.bux.matchingengine.engine.Order;
import io.bux.matchingengine.enums.Direction;
import org.junit.jupiter.api.Test;

import static io.bux.matchingengine.util.MessageConstant.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

class OrderValidationTest {

    @Test
    public void test_validate_order_when_order_is_null() {
        final Throwable raisedException = catchThrowable(() -> OrderValidation.validateOrder(null));
        assertThat(raisedException).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(EMPTY_ORDER_ERROR);
    }

    @Test
    public void test_validate_order_when_order_id_is_null() {
        final Throwable raisedException = catchThrowable(() -> OrderValidation.validateOrder(Order.builder().build()));
        assertThat(raisedException).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(EMPTY_ORDER_ID_ERROR);
    }

    @Test
    public void test_validate_order_when_price_is_null() {
        final Throwable raisedException = catchThrowable(() -> OrderValidation.validateOrder(Order.builder().orderId(0L).build()));
        assertThat(raisedException).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(EMPTY_PRICE_ERROR);
    }

    @Test
    public void test_validate_order_when_direction_is_null() {
        final Throwable raisedException = catchThrowable(() -> OrderValidation.validateOrder(Order.builder().orderId(0L).price(0.01).build()));
        assertThat(raisedException).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(EMPTY_DIRECTION_ERROR);
    }

    @Test
    public void test_validate_order_when_amount_is_null() {
        final Throwable raisedException = catchThrowable(() -> OrderValidation.validateOrder(Order.builder().orderId(0L).price(0.01).direction(Direction.BUY).build()));
        assertThat(raisedException).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(EMPTY_AMOUNT_ERROR);
    }

    @Test
    public void test_validate_order_when_amount_is_negative() {
        final Throwable raisedException = catchThrowable(() -> OrderValidation.validateOrder(Order.builder().orderId(0L).price(0.01).amount(-Double.MIN_VALUE).direction(Direction.BUY).build()));
        assertThat(raisedException).isInstanceOf(ArithmeticException.class)
                .hasMessageContaining(NEGATIVE_AMOUNT_ERROR);
    }

    @Test
    public void test_validate_order_request_when_order_is_null() {
        final Throwable raisedException = catchThrowable(() -> OrderValidation.validateOrderRequest(null));
        assertThat(raisedException).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(EMPTY_ORDER_REQUEST_ERROR);
    }

    @Test
    public void test_validate_order_request_when_asset_is_null() {
        final Throwable raisedException = catchThrowable(() -> OrderValidation.validateOrderRequest(new OrderRequest(null,null,null,null)));
        assertThat(raisedException).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(EMPTY_ORDER_REQUEST_ASSET_ERROR);
    }

    @Test
    public void test_validate_order_request_when_price_is_null() {
        final Throwable raisedException = catchThrowable(() -> OrderValidation.validateOrderRequest(new OrderRequest("TST",null,null,null)));
        assertThat(raisedException).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(EMPTY_ORDER_REQUEST_PRICE_ERROR);
    }

    @Test
    public void test_validate_order_request_when_direction_is_null() {
        final Throwable raisedException = catchThrowable(() -> OrderValidation.validateOrderRequest(new OrderRequest("TST",100.00,null,null)));
        assertThat(raisedException).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(EMPTY_ORDER_REQUEST_DIRECTION_ERROR);
    }

    @Test
    public void test_validate_order_request_when_amount_is_null() {
        final Throwable raisedException = catchThrowable(() -> OrderValidation.validateOrderRequest(new OrderRequest("TST",100.00,null,Direction.SELL)));
        assertThat(raisedException).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(EMPTY_ORDER_REQUEST_AMOUNT_ERROR);
    }

    @Test
    public void test_validate_order_request_when_amount_is_negative() {
        final Throwable raisedException = catchThrowable(() -> OrderValidation.validateOrderRequest(new OrderRequest("TST",100.00,-Double.MIN_VALUE,Direction.SELL)));
        assertThat(raisedException).isInstanceOf(ArithmeticException.class)
                .hasMessageContaining(NEGATIVE_ORDER_REQUEST_AMOUNT_ERROR);
    }
}