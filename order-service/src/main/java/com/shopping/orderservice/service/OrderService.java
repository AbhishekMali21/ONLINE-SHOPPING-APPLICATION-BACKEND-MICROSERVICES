package com.shopping.orderservice.service;

import com.shopping.orderservice.entities.Order;
import com.shopping.orderservice.exception.InventoryServiceUnavailableException;
import com.shopping.orderservice.repository.OrderRepository;
import com.shopping.orderservice.request.OrderRequest;
import com.shopping.orderservice.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClientService inventoryClientService;

    public String placeOrder(OrderRequest orderRequest) {
        try {
            boolean isProductInStock = inventoryClientService.isInStock(orderRequest.skuCode(), orderRequest.quantity());
            if (!isProductInStock) {
                throw new RuntimeException("Order Failed: Inventory is not in stock");
            }

            Order order = Order.builder()
                    .orderNumber(UUID.randomUUID().toString())
                    .skuCode(orderRequest.skuCode())
                    .price(orderRequest.price())
                    .quantity(orderRequest.quantity())
                    .build();
            orderRepository.save(order);
            log.info("order created with order id is: {}", order.getId());
            return "Order Placed Successfully";
        } catch (InventoryServiceUnavailableException e) {
            throw new InventoryServiceUnavailableException("Order Failed: Inventory service is down");
        }
    }

    public OrderResponse getOrderByOrderId(Long orderId) {
        return orderRepository.findById(orderId)
                .map(order -> new OrderResponse(order.getId(), order.getOrderNumber(), order.getSkuCode(), order.getPrice(), order.getQuantity()))
                .orElse(null);
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(order -> new OrderResponse(order.getId(), order.getOrderNumber(), order.getSkuCode(), order.getPrice(), order.getQuantity()))
                .toList();
    }
}
