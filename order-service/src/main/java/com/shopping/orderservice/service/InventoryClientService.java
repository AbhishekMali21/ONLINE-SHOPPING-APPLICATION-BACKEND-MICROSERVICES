package com.shopping.orderservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class InventoryClientService {

    private final RestTemplate restTemplate;
    @Value("${inventory.url}")
    private String inventoryServiceUrl;

    @Autowired
    public InventoryClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean isInStock(String skuCode, Integer quantity) {
        String url = UriComponentsBuilder.fromUriString(inventoryServiceUrl + "/api/inventory")
                .queryParam("skuCode", skuCode)
                .queryParam("quantity", quantity)
                .toUriString();

        return restTemplate.getForObject(url, Boolean.class);
    }
}