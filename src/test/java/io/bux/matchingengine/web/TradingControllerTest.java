package io.bux.matchingengine.web;

import io.bux.matchingengine.dto.OrderRequest;
import io.bux.matchingengine.dto.OrderResponse;
import io.bux.matchingengine.dto.TradeResponse;
import io.bux.matchingengine.enums.Direction;
import io.bux.matchingengine.service.OrderBookService;
import io.bux.matchingengine.util.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(TradingController.class)
public class TradingControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OrderBookService orderBookService;

    @Test
    public void test_place_order() throws Exception {
        Instant timestamp = Instant.now();
        OrderRequest request = new OrderRequest("BTC", 43251.00, 1.0, Direction.SELL);
        Mockito.when(orderBookService.placeOrder(ArgumentMatchers.any(OrderRequest.class))).thenReturn(buildOrderResponse(timestamp));

        mvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(0)))
                .andExpect(jsonPath("$.timestamp", is(timestamp.toString())))
                .andExpect(jsonPath("$.asset", is("BTC")))
                .andExpect(jsonPath("$.price", is(43251.00)))
                .andExpect(jsonPath("$.amount", is(1.0)))
                .andExpect(jsonPath("$.direction", is(Direction.SELL.toString())))
                .andExpect(jsonPath("$.trades[0].orderId", is(2)))
                .andExpect(jsonPath("$.trades[0].amount", is(0.35)))
                .andExpect(jsonPath("$.trades[0].price", is(43251.00)))
                .andExpect(jsonPath("$.pendingAmount", is(0.65)));
    }

    @Test
    public void test_get_order_by_id_thenStatus200() throws Exception {
        Instant timestamp = Instant.now();
        Mockito.when(orderBookService.getOrder(0L)).thenReturn(buildOrderResponse(timestamp));


        mvc.perform(get("/orders/0").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(0)))
                .andExpect(jsonPath("$.timestamp", is(timestamp.toString())))
                .andExpect(jsonPath("$.asset", is("BTC")))
                .andExpect(jsonPath("$.price", is(43251.00)))
                .andExpect(jsonPath("$.amount", is(1.0)))
                .andExpect(jsonPath("$.direction", is(Direction.SELL.toString())))
                .andExpect(jsonPath("$.trades[0].orderId", is(2)))
                .andExpect(jsonPath("$.trades[0].amount", is(0.35)))
                .andExpect(jsonPath("$.trades[0].price", is(43251.00)))
                .andExpect(jsonPath("$.pendingAmount", is(0.65)));
    }

    private OrderResponse buildOrderResponse(Instant timestamp) {
        return new OrderResponse(0L,
                timestamp,
                "BTC",
                43251.00,
                1.0,
                Direction.SELL,
                List.of(
                        new TradeResponse(2L, 0.35, 43251.00)
                ),
                0.65);
    }
}