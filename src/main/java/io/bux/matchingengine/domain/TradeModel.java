package io.bux.matchingengine.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class TradeModel {
    private final Long orderId;
    private final BigDecimal price;
    private final BigDecimal amount;
}
