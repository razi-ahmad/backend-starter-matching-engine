package io.bux.matchingengine.dto;

import io.bux.matchingengine.enums.Direction;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public record OrderRequest(
        @NotNull
        String asset,
        @NotNull
        BigDecimal price,
        @NotNull
        BigDecimal amount,
        @NotNull
        Direction direction) {

        @Override
        public String toString() {
                return "OrderRequest{" +
                        "asset='" + asset + '\'' +
                        ", price=" + price +
                        ", amount=" + amount +
                        ", direction=" + direction +
                        '}';
        }
}
