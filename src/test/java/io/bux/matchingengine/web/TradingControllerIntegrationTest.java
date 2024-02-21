package io.bux.matchingengine.web;

import io.bux.matchingengine.dto.OrderRequest;
import io.bux.matchingengine.dto.OrderResponse;
import io.bux.matchingengine.enums.Direction;
import io.bux.matchingengine.util.MessageConstant;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TradingControllerIntegrationTest {
    public static final String BASE_URL = "/orders";
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void test_when_no_order_exist() {
        String content = this.restTemplate.getForObject(BASE_URL + "/" + Long.MAX_VALUE, String.class);
        Assertions.assertEquals("Order not found", content);
    }

    @Test(expected = RestClientException.class)
    public void test_place_order_when_request_is_null() {
        this.restTemplate.postForEntity(BASE_URL, null, OrderResponse.class);
    }

    @Test
    public void test_place_order_when_request_is_empty() {
        ResponseEntity<String> content=this.restTemplate.postForEntity(BASE_URL, new OrderRequest(null, null, null, null), String.class);
        Assertions.assertEquals(MessageConstant.EMPTY_ORDER_REQUEST_ASSET_ERROR,content.getBody());
    }

    @Test
    public void test_with_sample_data() {
        OrderRequest request = new OrderRequest("BTC", new BigDecimal("43251.00"), new BigDecimal("1.0"), Direction.SELL);
        ResponseEntity<OrderResponse> response = this.restTemplate.postForEntity(BASE_URL, request, OrderResponse.class);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        request = new OrderRequest("BTC", new BigDecimal("43250.00"), new BigDecimal("0.25"), Direction.BUY);
        response = this.restTemplate.postForEntity(BASE_URL, request, OrderResponse.class);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        request = new OrderRequest("BTC", new BigDecimal("43253.00"), new BigDecimal("0.35"), Direction.BUY);
        response = this.restTemplate.postForEntity(BASE_URL, request, OrderResponse.class);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        request = new OrderRequest("BTC", new BigDecimal("43251.00"), new BigDecimal("0.65"), Direction.BUY);
        response = this.restTemplate.postForEntity(BASE_URL, request, OrderResponse.class);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        OrderResponse orderResponse = this.restTemplate.getForObject(BASE_URL + "/3", OrderResponse.class);
        Assertions.assertNotNull(orderResponse);
        Assertions.assertNotNull(orderResponse.trades());
        Assertions.assertEquals(3, orderResponse.id());
        Assertions.assertEquals(43251.00, orderResponse.price());
        Assertions.assertEquals(0.65, orderResponse.amount());
        Assertions.assertEquals(Direction.BUY, orderResponse.direction());
        Assertions.assertEquals(0.00, orderResponse.pendingAmount());
        Assertions.assertEquals(1, orderResponse.trades().size());
        Assertions.assertEquals(0, orderResponse.trades().get(0).orderId());
        Assertions.assertEquals(0.65, orderResponse.trades().get(0).amount());
        Assertions.assertEquals(43251.00, orderResponse.trades().get(0).price());
    }
}