package io.bux.matchingengine.dto;

import io.bux.matchingengine.enums.Direction;

import java.time.Instant;
import java.util.List;

public record OrderResponse(Long id,
                            //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
                            Instant timestamp,
                            String asset,
                            Double price,
                            Double amount,
                            Direction direction,
                            List<TradeResponse> trades,
                            Double pendingAmount) {

    @Override
    public String toString() {
        return "OrderResponse{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", asset='" + asset + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                ", direction=" + direction +
                ", trades=" + trades +
                ", pendingAmount=" + pendingAmount +
                '}';
    }
}
