package io.bux.matchingengine.dto;

import java.math.BigDecimal;

public record TradeResponse(Long orderId, BigDecimal amount, BigDecimal price) {

    @Override
    public String toString() {
        return "Trade{" +
                "orderId=" + orderId +
                ", amount=" + amount +
                ", price=" + price +
                '}';
    }
}
