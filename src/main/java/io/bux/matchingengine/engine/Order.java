package io.bux.matchingengine.engine;

import io.bux.matchingengine.enums.Direction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

import static io.bux.matchingengine.util.MessageConstant.ZERO_AMOUNT_ERROR;


@Getter
@Builder
@AllArgsConstructor
public class Order {
    private Long orderId;
    private BigDecimal amount;
    private BigDecimal price;
    private Direction direction;
    private Instant timestamp;
    private String asset;

    public void setAmount(BigDecimal amount) {
        if (BigDecimal.ZERO.equals(amount)) {
            throw new ArithmeticException(ZERO_AMOUNT_ERROR);
        }
        this.amount = amount;
    }
}
