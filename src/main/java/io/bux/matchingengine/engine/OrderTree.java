package io.bux.matchingengine.engine;

import java.math.BigDecimal;
import java.util.*;

public class OrderTree {
    private final Map<String, TreeMap<BigDecimal, PriorityQueue<Order>>> assets = new TreeMap<>();
    private final HashMap<Long, Order> orderMap = new HashMap<>();

    public void addOrder(Order order) {
        String asset = order.getAsset();
        Long orderId = order.getOrderId();
        BigDecimal price = order.getPrice();


        orderMap.put(orderId, order);
        if (assets.containsKey(asset)) {
            if (assets.get(asset).containsKey(price)) {
                assets.get(asset).get(price).add(order);
            } else {
                PriorityQueue<Order> priceList = new PriorityQueue<>(Comparator.comparing(Order::getTimestamp));
                priceList.add(order);
                TreeMap<BigDecimal, PriorityQueue<Order>> orderTree = assets.get(asset);
                orderTree.put(order.getPrice(), priceList);
            }
        } else {
            saveOrder(order);
        }
    }

    private void saveOrder(Order order) {
        PriorityQueue<Order> priceList = new PriorityQueue<>(Comparator.comparing(Order::getTimestamp));
        priceList.add(order);
        TreeMap<BigDecimal, PriorityQueue<Order>> orderTree = new TreeMap<>();
        orderTree.put(order.getPrice(), priceList);
        assets.put(order.getAsset(), orderTree);
    }

    public void deleteOrder(Long orderId) {
        if (orderMap.containsKey(orderId)) {
            Order order = orderMap.get(orderId);
            BigDecimal price = order.getPrice();
            String asset = order.getAsset();
            if (assets.containsKey(asset)) {
                if (assets.get(asset).containsKey(price)) {
                    PriorityQueue<Order> priceList = assets.get(asset).get(price);
                    priceList.remove(order);
                    if (priceList.isEmpty()) {
                        assets.get(asset).remove(price);
                        if (assets.get(asset).isEmpty()) {
                            assets.remove(asset);
                        }
                    }
                }
            }
            orderMap.remove(orderId);
        }
    }

    public PriorityQueue<Order> getMinPriceList(String asset) {
        if (assets.isEmpty() || assets.get(asset).isEmpty()) return new PriorityQueue<>();
        return new PriorityQueue<Order>(assets.get(asset).firstEntry().getValue());
    }

    public BigDecimal getLowestPrice(String asset) {
        return assets.isEmpty() || assets.get(asset).isEmpty() ? BigDecimal.valueOf(Double.MAX_VALUE) : assets.get(asset).firstKey();
    }

    public BigDecimal getHighestPrice(String asset) {
        return assets.isEmpty() || assets.get(asset).isEmpty() ? BigDecimal.valueOf(Double.MAX_VALUE) : assets.get(asset).lastKey();
    }

    public PriorityQueue<Order> getMaxPriceList(String asset) {
        if (assets.isEmpty() || assets.get(asset).isEmpty()) return new PriorityQueue<>();
        return new PriorityQueue<>(assets.get(asset).lastEntry().getValue());
    }

    public boolean isNotEmpty(String asset) {
        return !(assets.isEmpty() || assets.get(asset).isEmpty());
    }
}
