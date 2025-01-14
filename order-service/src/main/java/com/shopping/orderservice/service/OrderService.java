package com.shopping.orderservice.service;

import com.shopping.orderservice.entities.Order;
import com.shopping.orderservice.repository.OrderRepository;
import com.shopping.orderservice.request.OrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .skuCode(orderRequest.skuCode())
                .price(orderRequest.price())
                .quantity(orderRequest.quantity())
                .build();
        orderRepository.save(order);
        log.info("order created with order id is: {}", order.getId());
    }
}
