package com.shopping.orderservice.controller;

import com.shopping.orderservice.request.OrderRequest;
import com.shopping.orderservice.response.OrderResponse;
import com.shopping.orderservice.service.InventoryClientService;
import com.shopping.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/placeOrder")
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.placeOrder(orderRequest);
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse getOrderById(@PathVariable("orderId") Long orderId) {
        return orderService.getOrderByOrderId(orderId);
    }

    @GetMapping("/getAllOrders")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders();
    }
}
