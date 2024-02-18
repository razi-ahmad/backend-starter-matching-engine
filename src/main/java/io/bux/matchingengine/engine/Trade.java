package io.bux.matchingengine.engine;

public record Trade(Long orderId, Double amount, Double price){

    @Override
    public String toString() {
        return "Trade{" +
                "orderId=" + orderId +
                ", amount=" + amount +
                ", price=" + price +
                '}';
    }
}
