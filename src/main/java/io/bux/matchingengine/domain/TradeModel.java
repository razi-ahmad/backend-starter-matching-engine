package io.bux.matchingengine.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TradeModel {
    private final Long orderId;
    private final Double price;
    private final Double amount;
}
