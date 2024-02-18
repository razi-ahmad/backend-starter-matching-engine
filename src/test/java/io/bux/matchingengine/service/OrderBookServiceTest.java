package io.bux.matchingengine.service;

import io.bux.matchingengine.dao.OrderDao;
import io.bux.matchingengine.domain.OrderModel;
import io.bux.matchingengine.dto.OrderRequest;
import io.bux.matchingengine.dto.OrderResponse;
import io.bux.matchingengine.dto.TradeResponse;
import io.bux.matchingengine.engine.Order;
import io.bux.matchingengine.engine.OrderBook;
import io.bux.matchingengine.engine.Trade;
import io.bux.matchingengine.enums.Direction;
import io.bux.matchingengine.exception.NotFoundException;
import io.bux.matchingengine.util.MapperUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class OrderBookServiceTest {
    @Autowired
    private OrderBookService underTest;
    @MockBean
    private OrderBook orderBook;
    @MockBean
    private OrderDao orderRepository;

    @Test
    void test_place_order_successfully(){
        OrderRequest request=new OrderRequest("TST",10.0,100.0,Direction.SELL);
        OrderModel orderModel= MapperUtil.mapOrderRequestToOrderModel(request);
        orderModel.setId(2L);
        orderModel.setTimestamp(Instant.now());
        Mockito.when(orderRepository.save(ArgumentMatchers.any(OrderModel.class))).thenReturn(orderModel);

        List<Pair<Trade, Trade>> mockTrades = List.of(
                Pair.of(new Trade(2L,10.0,10.0),new Trade(0L,10.0,10.0))
        );
        Mockito.when(orderBook.processOrder(ArgumentMatchers.any(Order.class))).thenReturn(mockTrades);
        Mockito.when(orderRepository.getById(0L)).thenReturn(Optional.of(OrderModel.builder().build()));

        OrderResponse response=underTest.placeOrder(request);

        Assertions.assertEquals(orderModel.getId(),response.id());
        Assertions.assertEquals(orderModel.getAmount(),response.amount());
        Assertions.assertEquals(orderModel.getPrice(),response.price());
        Assertions.assertEquals(orderModel.getDirection(),response.direction());
        Assertions.assertEquals(orderModel.getAsset(),response.asset());
        Assertions.assertEquals(90.0,response.pendingAmount());
        Assertions.assertEquals(1,orderModel.getTrades().size());
        Assertions.assertArrayEquals(List.of(new TradeResponse(0L,10.0,10.0)).toArray(), response.trades().toArray());
    }

    @Test
    void test_get_order_when_not_exist() {
        Long orderId = 11223344L;
        Mockito.when(orderRepository.getById(orderId)).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> underTest.getOrder(orderId));
    }

    @Test
    void test_get_order_when_exist() {
        Long orderId = 11223344L;
        OrderModel orderModel = OrderModel.builder()
                .id(orderId)
                .amount(10.0)
                .amount(100.00)
                .asset("TST")
                .direction(Direction.SELL)
                .timestamp(Instant.now())
                .build();
        Mockito.when(orderRepository.getById(orderId)).thenReturn(Optional.of(orderModel));
        OrderResponse response=underTest.getOrder(orderId);
        Assertions.assertEquals(orderModel.getId(),response.id());
        Assertions.assertEquals(orderModel.getAmount(),response.amount());
        Assertions.assertEquals(orderModel.getPrice(),response.price());
        Assertions.assertEquals(orderModel.getDirection(),response.direction());
        Assertions.assertEquals(orderModel.getAsset(),response.asset());
        Assertions.assertEquals(orderModel.getAmount(),response.pendingAmount());
        Assertions.assertTrue(response.trades().isEmpty());

    }

}