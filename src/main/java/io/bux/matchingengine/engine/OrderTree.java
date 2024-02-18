package io.bux.matchingengine.engine;

import java.util.*;

public class OrderTree {
    private final TreeMap<Double, LinkedList<Order>> orderTree = new TreeMap<>();
    private final HashMap<Long, Order> orderMap = new HashMap<>();

    public void addOrder(Order order) {
        Long orderId = order.getOrderId();
        Double price = order.getPrice();

        orderMap.put(orderId, order);
        if (orderTree.containsKey(price)) {
            orderTree.get(price).add(order);
        } else {
            LinkedList<Order> priceList = new LinkedList<>();
            priceList.add(order);
            orderTree.put(price, priceList);
        }
    }

    public void deleteOrder(Long orderId) {
        if (orderMap.containsKey(orderId)) {
            Order order = orderMap.get(orderId);
            double price = order.getPrice();
            if (orderTree.containsKey(price)) {
                LinkedList<Order> priceList = orderTree.get(price);
                priceList.remove(order);
                if (priceList.isEmpty()) {
                    orderTree.remove(price);
                }
            }
            orderMap.remove(orderId);
        }
    }

    public List<Order> getMinPriceList() {
        if (orderTree.isEmpty()) return Collections.unmodifiableList(new LinkedList<>());
        return List.copyOf(orderTree.firstEntry().getValue());
    }

    public double getLowestPrice() {
        return orderTree.isEmpty() ? Double.MAX_VALUE : orderTree.firstKey();
    }

    public double getHighestPrice() {
        return orderTree.isEmpty() ? Double.MAX_VALUE : orderTree.lastKey();
    }

    public List<Order> getMaxPriceList() {
        if (orderTree.isEmpty()) return Collections.unmodifiableList(new LinkedList<>());
        return List.copyOf(orderTree.lastEntry().getValue());
    }

    public boolean isNotEmpty() {
        return !orderTree.isEmpty();
    }
}
