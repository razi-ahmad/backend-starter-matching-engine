package io.bux.matchingengine.domain;

import io.bux.matchingengine.enums.Direction;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;


@Getter
@Builder
public class OrderModel {
    @Setter
    private Long id;
    @Setter
    private Instant timestamp;
    private final String asset;
    private final BigDecimal price;
    private final BigDecimal amount;
    private final Direction direction;
    private final Set<TradeModel> trades = new HashSet<>();
}
