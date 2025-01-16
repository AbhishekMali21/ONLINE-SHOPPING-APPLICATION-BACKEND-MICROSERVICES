package com.shopping.apigateway.routes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;

@Configuration
public class Routes {

    @Value("${product.service.url}")
    private String productServiceUrl;

    @Value("${order.service.url}")
    private String orderServiceUrl;

    @Value("${inventory.service.url}")
    private String inventoryServiceUrl;

    @Bean
    public RouterFunction<ServerResponse> productServiceRoute() {
        return createServiceRoute("product_service", "/api/product/**", productServiceUrl);
    }

    @Bean
    public RouterFunction<ServerResponse> productServiceSwaggerRoute() {
        return createSwaggerRoute("product_service_swagger", "/aggregate/product-service/v3/api-docs", productServiceUrl);
    }

    @Bean
    public RouterFunction<ServerResponse> orderServiceRoute() {
        return createServiceRoute("order_service", "/api/order/**", orderServiceUrl);
    }

    @Bean
    public RouterFunction<ServerResponse> orderServiceSwaggerRoute() {
        return createSwaggerRoute("order_service_swagger", "/aggregate/order-service/v3/api-docs", orderServiceUrl);
    }

    @Bean
    public RouterFunction<ServerResponse> inventoryServiceRoute() {
        return createServiceRoute("inventory_service", "/api/inventory/**", inventoryServiceUrl);
    }

    @Bean
    public RouterFunction<ServerResponse> inventoryServiceSwaggerRoute() {
        return createSwaggerRoute("inventory_service_swagger", "/aggregate/inventory-service/v3/api-docs", inventoryServiceUrl);
    }

    private RouterFunction<ServerResponse> createServiceRoute(String id, String path, String uri) {
        return GatewayRouterFunctions.route(id)
                .route(RequestPredicates.path(path), HandlerFunctions.http(uri))
                .build();
    }

    private RouterFunction<ServerResponse> createSwaggerRoute(String id, String path, String uri) {
        return GatewayRouterFunctions.route(id)
                .route(RequestPredicates.path(path), HandlerFunctions.http(uri))
                .filter(setPath("/api-docs"))
                .build();
    }
}