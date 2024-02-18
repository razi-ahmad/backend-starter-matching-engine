package io.bux.matchingengine.engine;

import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface OrderBook {
    List<Pair<Trade, Trade>> processOrder(Order order);
}
