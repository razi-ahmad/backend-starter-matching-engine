package io.bux.matchingengine.engine;

import io.bux.matchingengine.enums.Direction;
import io.bux.matchingengine.validation.OrderValidation;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class LimitOrderBookImpl implements OrderBook {

    private final OrderTree buySide;
    private final OrderTree sellSide;

    public LimitOrderBookImpl() {
        this.buySide = new OrderTree();
        this.sellSide = new OrderTree();
    }

    public List<Pair<Trade, Trade>> processOrder(Order order) {
        OrderValidation.validateOrder(order);
        return processLimitOrder(order);

    }

    private synchronized List<Pair<Trade, Trade>> processLimitOrder(Order order) {
        Direction direction = order.getDirection();
        if (direction == Direction.BUY) {
            buySide.addOrder(order);
            return matchAndExecuteOrder(order, sellSide);
        } else {
            sellSide.addOrder(order);
            return matchAndExecuteOrder(order, buySide);
        }
    }

    private List<Pair<Trade, Trade>> matchAndExecuteOrder(Order order, OrderTree orderTree) {

        double amount = order.getAmount();
        long orderId = order.getOrderId();
        double price = order.getPrice();
        if (order.getDirection() == Direction.BUY) {
            return processBuyOrder(order, orderTree, amount, price, orderId);
        }
        return processSellOrder(order, orderTree, amount, price, orderId);
    }

    private List<Pair<Trade, Trade>> processBuyOrder(Order order, OrderTree orderTree, double amount, double price, long orderId) {
        List<Pair<Trade, Trade>> trades = new LinkedList<>();
        while (amount > 0 && orderTree.isNotEmpty() && price >= orderTree.getLowestPrice()) {
            List<Order> minOrderList = orderTree.getMinPriceList();
            if (minOrderList == null || minOrderList.isEmpty()) break;

            for (Order o : minOrderList) {
                double tradedAmount;
                if (amount <= o.getAmount()) {
                    tradedAmount = amount;
                    this.buySide.deleteOrder(orderId);
                    amount = 0;
                    if (o.getAmount() - tradedAmount == 0) {
                        orderTree.deleteOrder(o.getOrderId());
                    } else {
                        o.setAmount(o.getAmount() - tradedAmount);
                    }
                } else {
                    tradedAmount = o.getAmount();
                    amount -= o.getAmount();
                    orderTree.deleteOrder(o.getOrderId());
                    order.setAmount(amount);
                }
                trades.add(Pair.of(new Trade(order.getOrderId(), tradedAmount, o.getPrice()), new Trade(o.getOrderId(), tradedAmount, o.getPrice())));
            }
        }
        return trades;
    }

    private List<Pair<Trade, Trade>> processSellOrder(Order order, OrderTree orderTree, double amount, double price, long orderId) {
        List<Pair<Trade, Trade>> trades = new LinkedList<>();
        while (amount > 0 && orderTree.isNotEmpty() && price <= orderTree.getHighestPrice()) {
            List<Order> maxOrderList = orderTree.getMaxPriceList();
            if (maxOrderList == null || maxOrderList.isEmpty()) break;
            for (Order o : maxOrderList) {
                double tradedAmount;
                if (amount <= o.getAmount()) {
                    tradedAmount = amount;
                    this.sellSide.deleteOrder(orderId);
                    amount = 0;
                    if (o.getAmount() - tradedAmount == 0) {
                        orderTree.deleteOrder(o.getOrderId());
                    } else {
                        o.setAmount(o.getAmount() - tradedAmount);
                    }
                } else {
                    tradedAmount = o.getAmount();
                    amount -= o.getAmount();
                    orderTree.deleteOrder(o.getOrderId());
                    order.setAmount(amount);
                }
                trades.add(Pair.of(new Trade(order.getOrderId(), tradedAmount, o.getPrice()), new Trade(o.getOrderId(), tradedAmount, o.getPrice())));
            }
        }
        return trades;
    }
}