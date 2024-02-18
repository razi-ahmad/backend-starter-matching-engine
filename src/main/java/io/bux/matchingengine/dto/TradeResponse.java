package io.bux.matchingengine.dto;

public record TradeResponse(Long orderId, Double amount, Double price) {

    @Override
    public String toString() {
        return "Trade{" +
                "orderId=" + orderId +
                ", amount=" + amount +
                ", price=" + price +
                '}';
    }
}
