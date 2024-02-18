package io.bux.matchingengine.engine;

import io.bux.matchingengine.enums.Direction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

import static io.bux.matchingengine.util.MessageConstant.NEGATIVE_AMOUNT_ERROR;


@Getter
@Builder
@AllArgsConstructor
public class Order {
    private Long orderId;
    private Double amount;
    private Double price;
    private Direction direction;
    private Instant timestamp;

    public void setAmount(Double amount) {
        if (amount < Double.MIN_VALUE) {
            throw new ArithmeticException(NEGATIVE_AMOUNT_ERROR);
        }
        this.amount = amount;
    }
}
