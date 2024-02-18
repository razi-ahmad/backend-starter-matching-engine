package io.bux.matchingengine.dto;

import io.bux.matchingengine.enums.Direction;

import javax.validation.constraints.NotNull;

public record OrderRequest(
        @NotNull
        String asset,
        @NotNull
        Double price,
        @NotNull
        Double amount,
        @NotNull
        Direction direction) {
}
