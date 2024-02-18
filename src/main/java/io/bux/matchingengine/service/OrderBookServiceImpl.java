package io.bux.matchingengine.service;

import io.bux.matchingengine.dao.OrderDao;
import io.bux.matchingengine.domain.OrderModel;
import io.bux.matchingengine.domain.TradeModel;
import io.bux.matchingengine.dto.OrderRequest;
import io.bux.matchingengine.dto.OrderResponse;
import io.bux.matchingengine.engine.Order;
import io.bux.matchingengine.engine.OrderBook;
import io.bux.matchingengine.engine.Trade;
import io.bux.matchingengine.exception.NotFoundException;
import io.bux.matchingengine.util.MapperUtil;
import io.bux.matchingengine.validation.OrderValidation;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderBookServiceImpl implements OrderBookService {

    private final OrderBook orderBook;
    private final OrderDao orderRepository;

    @Autowired
    public OrderBookServiceImpl(OrderBook orderBook, OrderDao orderRepository) {
        this.orderBook = orderBook;
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderResponse placeOrder(OrderRequest request) {
        OrderValidation.validateOrderRequest(request);

        OrderModel orderModel = saveOrder(request);

        Order order = MapperUtil.mapOrderModelToOrder(orderModel);
        List<Pair<Trade, Trade>> trades = orderBook.processOrder(order);
        if (!trades.isEmpty()) {
            updateBuyAndSellOrdersWithTrades(trades, orderModel);
        }

        return MapperUtil.mapOrderModelToDto(orderModel);
    }

    private OrderModel saveOrder(OrderRequest request) {
        OrderModel orderModel = MapperUtil.mapOrderRequestToOrderModel(request);
        orderModel = orderRepository.save(orderModel);
        return orderModel;
    }

    @Override
    public OrderResponse getOrder(Long orderId) {
        Optional<OrderModel> order = orderRepository.getById(orderId);
        if (order.isEmpty()) {
            throw new NotFoundException("Order not found");
        }
        return MapperUtil.mapOrderModelToDto(order.get());
    }

    private void updateBuyAndSellOrdersWithTrades(List<Pair<Trade, Trade>> trades, OrderModel order) {
        trades.forEach(tradePair -> {
            Trade trade = tradePair.getRight();
            order.getTrades().add(new TradeModel(trade.orderId(), trade.price(), trade.amount()));
            Optional<OrderModel> oppositeOrder = orderRepository.getById(trade.orderId());
            Trade oppositeTrade = tradePair.getLeft();
            oppositeOrder.get().getTrades().add(new TradeModel(oppositeTrade.orderId(), oppositeTrade.price(), oppositeTrade.amount()));
        });
    }
}
