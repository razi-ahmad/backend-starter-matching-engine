package io.bux.matchingengine.engine;

import io.bux.matchingengine.enums.Direction;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LimitOrderBookImplTest {

    @Autowired
    private OrderBook underTest;

    @Test
    @org.junit.jupiter.api.Order(1)
    public void test_process_order_with_no_sell_trade() {
        List<Pair<Trade, Trade>> trades = underTest.processOrder(Order.builder().orderId(1L).price(new BigDecimal("10.05")).amount(new BigDecimal("20.0")).timestamp(Instant.now()).direction(Direction.SELL).asset("BTC").build());
        trades.addAll(underTest.processOrder(Order.builder().orderId(2L).price(new BigDecimal("10.04")).amount(new BigDecimal("20.0")).timestamp(Instant.now()).direction(Direction.SELL).asset("BTC").build()));
        trades.addAll(underTest.processOrder(Order.builder().orderId(3L).price(new BigDecimal("10.05")).amount(new BigDecimal("40.0")).timestamp(Instant.now()).direction(Direction.SELL).asset("BTC").build()));
        Assertions.assertTrue(trades.isEmpty());
    }


    @Test
    @org.junit.jupiter.api.Order(2)
    public void test_process_order_with_no_buy_trade() {
        List<Pair<Trade, Trade>> trades = underTest.processOrder(Order.builder().orderId(4L).price(new BigDecimal("10.00")).amount(new BigDecimal("20.0")).timestamp(Instant.now()).direction(Direction.BUY).asset("BTC").build());
        trades.addAll(underTest.processOrder(Order.builder().orderId(5L).price(new BigDecimal("10.02")).amount(new BigDecimal("40.00")).timestamp(Instant.now()).direction(Direction.BUY).asset("BTC").build()));
        trades.addAll(underTest.processOrder(Order.builder().orderId(6L).price(new BigDecimal("10.00")).amount(new BigDecimal("40.00")).timestamp(Instant.now()).direction(Direction.BUY).asset("BTC").build()));
        Assertions.assertTrue(trades.isEmpty());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    public void test_process_order_with_buy_trade() {
        List<Pair<Trade, Trade>> trades = underTest.processOrder(Order.builder().orderId(7L).price(new BigDecimal("10.06")).amount(new BigDecimal("55.0")).timestamp(Instant.now()).direction(Direction.BUY).asset("BTC").build());
        Assertions.assertEquals(3, trades.size());
        List<Pair<Trade, Trade>> expectedTrades = List.of(
                Pair.of(new Trade(7L, new BigDecimal("20.0"), new BigDecimal("10.04")), new Trade(2L, new BigDecimal("20.0"), new BigDecimal("10.04"))),
                Pair.of(new Trade(7L, new BigDecimal("20.0"), new BigDecimal("10.05")), new Trade(1L, new BigDecimal("20.0"), new BigDecimal("10.05"))),
                Pair.of(new Trade(7L, new BigDecimal("15.0"), new BigDecimal("10.05")), new Trade(3L, new BigDecimal("15.0"), new BigDecimal("10.05")))

        );
        Assertions.assertArrayEquals(expectedTrades.toArray(), trades.toArray());
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    public void test_process_order_with_sell_trade() {
        List<Pair<Trade, Trade>> trades = underTest.processOrder(Order.builder().orderId(8L).price(new BigDecimal("9.99")).amount(new BigDecimal("75.0")).timestamp(Instant.now()).direction(Direction.SELL).asset("BTC").build());
        Assertions.assertEquals(3, trades.size());
        List<Pair<Trade, Trade>> expectedTrades = List.of(
                Pair.of(new Trade(8L, new BigDecimal("40.00"), new BigDecimal("10.02")), new Trade(5L, new BigDecimal("40.00"), new BigDecimal("10.02"))),
                Pair.of(new Trade(8L, new BigDecimal("20.0"), new BigDecimal("10.00")), new Trade(4L, new BigDecimal("20.0"), new BigDecimal("10.00"))),
                Pair.of(new Trade(8L, new BigDecimal("15.00"), new BigDecimal("10.00")), new Trade(6L, new BigDecimal("15.00"), new BigDecimal("10.00")))

        );
        Assertions.assertArrayEquals(expectedTrades.toArray(), trades.toArray());
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    public void test_process_order_with_sell_trade_complete() {
        List<Pair<Trade, Trade>> trades = underTest.processOrder(Order.builder().orderId(9L).price(new BigDecimal("9.99")).amount(new BigDecimal("25.0")).timestamp(Instant.now()).direction(Direction.SELL).asset("BTC").build());
        Assertions.assertEquals(1, trades.size());
        List<Pair<Trade, Trade>> expectedTrades = List.of(
                Pair.of(new Trade(9L, new BigDecimal("25.0"), new BigDecimal("10.00")), new Trade(6L, new BigDecimal("25.0"), new BigDecimal("10.00")))

        );
        Assertions.assertArrayEquals(expectedTrades.toArray(), trades.toArray());
    }

    @Test
    @org.junit.jupiter.api.Order(6)
    public void test_process_order_with_buy_trade_complete() {
        List<Pair<Trade, Trade>> trades = underTest.processOrder(Order.builder().orderId(10L).price(new BigDecimal("10.06")).amount(new BigDecimal("25.0")).timestamp(Instant.now()).direction(Direction.BUY).asset("BTC").build());
        Assertions.assertEquals(1, trades.size());
        List<Pair<Trade, Trade>> expectedTrades = List.of(
                Pair.of(new Trade(10L, new BigDecimal("25.0"), new BigDecimal("10.05")), new Trade(3L, new BigDecimal("25.0"), new BigDecimal("10.05")))

        );
        Assertions.assertArrayEquals(expectedTrades.toArray(), trades.toArray());
    }
}