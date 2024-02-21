package io.bux.matchingengine.engine;

import java.util.*;

public class OrderTree {
    private final Map<String, TreeMap<Double, LinkedList<Order>>> assets = new TreeMap<>();
    private final HashMap<Long, Order> orderMap = new HashMap<>();

    public void addOrder(Order order) {
        String asset = order.getAsset();
        Long orderId = order.getOrderId();
        Double price = order.getPrice();


        orderMap.put(orderId, order);
        if (assets.containsKey(asset)) {
            assets.get(asset).get(price).add(order);
        } else {
            LinkedList<Order> priceList = new LinkedList<>();
            priceList.add(order);
            TreeMap<Double, LinkedList<Order>> orderTree = new TreeMap<>();
            orderTree.put(price, priceList);
            assets.put(asset, orderTree);
        }
    }

    public void deleteOrder(Long orderId) {
        if (orderMap.containsKey(orderId)) {
            Order order = orderMap.get(orderId);
            double price = order.getPrice();
            String asset = order.getAsset();
            if (assets.containsKey(asset)) {
                LinkedList<Order> priceList = assets.get(asset).get(price);
                priceList.remove(order);
                if (priceList.isEmpty()) {
                    assets.remove(asset);
                }
            }
            orderMap.remove(orderId);
        }
    }

    public List<Order> getMinPriceList(String asset) {
        if (assets.isEmpty() || assets.get(asset).isEmpty()) return Collections.unmodifiableList(new LinkedList<>());
        return List.copyOf(assets.get(asset).firstEntry().getValue());
    }

    public double getLowestPrice(String asset) {
        return assets.isEmpty() || assets.get(asset).isEmpty() ? Double.MAX_VALUE : assets.get(asset).firstKey();
    }

    public double getHighestPrice(String asset) {
        return assets.isEmpty() || assets.get(asset).isEmpty() ? Double.MAX_VALUE : assets.get(asset).lastKey();
    }

    public List<Order> getMaxPriceList(String asset) {
        if (assets.isEmpty() || assets.get(asset).isEmpty()) return Collections.unmodifiableList(new LinkedList<>());
        return List.copyOf(assets.get(asset).lastEntry().getValue());
    }

    public boolean isNotEmpty(String asset) {
        return !(asset.isEmpty() || assets.get(asset).isEmpty());
    }
}
