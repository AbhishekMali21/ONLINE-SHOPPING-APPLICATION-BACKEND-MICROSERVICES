package com.shopping.orderservice.service;

import com.shopping.orderservice.exception.InventoryServiceUnavailableException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Slf4j
public class InventoryClientService {

    private final RestTemplate restTemplate;

    @Value("${inventory.url}")
    private String inventoryServiceUrl;

    @Autowired
    public InventoryClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @Retry(name = "inventory")
    public boolean isInStock(String skuCode, Integer quantity) {
        String url = UriComponentsBuilder.fromUriString(inventoryServiceUrl + "/api/inventory")
                .queryParam("skuCode", skuCode)
                .queryParam("quantity", quantity)
                .toUriString();

        return restTemplate.getForObject(url, Boolean.class);
    }

    boolean fallbackMethod(String code, Integer quantity, Throwable throwable) {
        log.info("Cannot get inventory for skuCode {}, failure reason: {}", code, throwable.getMessage());
        throw new InventoryServiceUnavailableException("Inventory Service is down");
    }
}
