package io.bux.matchingengine.web;

import io.bux.matchingengine.dto.OrderRequest;
import io.bux.matchingengine.dto.OrderResponse;
import io.bux.matchingengine.service.OrderBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class TradingController {

    private final OrderBookService orderBookService;

    @Autowired
    public TradingController(OrderBookService orderBookService) {
        this.orderBookService = orderBookService;
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderResponse> placeOrder(@Valid @RequestBody OrderRequest request) {

        return new ResponseEntity<>(orderBookService.placeOrder(request),HttpStatus.CREATED);
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long orderId) {
        return new ResponseEntity<>(orderBookService.getOrder(orderId),HttpStatus.OK);
    }
}
