package io.bux.matchingengine.engine;

import java.math.BigDecimal;
import java.util.Objects;

public record Trade(Long orderId, BigDecimal amount, BigDecimal price){

    @Override
    public String toString() {
        return "Trade{" +
                "orderId=" + orderId +
                ", amount=" + amount +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trade trade = (Trade) o;
        return Objects.equals(orderId, trade.orderId) && Objects.equals(amount, trade.amount) && Objects.equals(price, trade.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, amount, price);
    }
}
