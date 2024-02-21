package io.bux.matchingengine.dao;

import io.bux.matchingengine.domain.OrderModel;
import io.bux.matchingengine.enums.Direction;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.Instant;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderDaoImplTest {
    @Autowired
    private OrderDao underTest;

    @Test
    @Order(1)
    void test_save() {
        OrderModel orderModel = buildOrderModel();
        OrderModel result = underTest.save(orderModel);
        Assertions.assertEquals(orderModel.getId(), result.getId());
        Assertions.assertEquals(orderModel.getAsset(), result.getAsset());
        Assertions.assertEquals(orderModel.getPrice(), result.getPrice());
        Assertions.assertEquals(orderModel.getAmount(), result.getAmount());
        Assertions.assertEquals(orderModel.getDirection(), result.getDirection());
        Assertions.assertEquals(orderModel.getTimestamp(), result.getTimestamp());
    }

    @Test
    @Order(2)
    void test_get_by_id() {
        OrderModel orderModel = buildOrderModel();
        OrderModel result = underTest.getById(0L).orElseThrow();
        Assertions.assertEquals(0L, result.getId());
        Assertions.assertEquals(orderModel.getAsset(), result.getAsset());
        Assertions.assertEquals(orderModel.getPrice(), result.getPrice());
        Assertions.assertEquals(orderModel.getAmount(), result.getAmount());
        Assertions.assertEquals(orderModel.getDirection(), result.getDirection());
    }

    private OrderModel buildOrderModel() {
        return OrderModel
                .builder()
                .asset("TST")
                .price(new BigDecimal("10.00"))
                .amount(new BigDecimal("100.0"))
                .direction(Direction.SELL)
                .timestamp(Instant.now())
                .build();
    }

}