package com.shopping.apigateway.routes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.HandlerFilterFunction;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;

import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;

@Configuration
public class Routes {

    private static final Logger logger = LoggerFactory.getLogger(Routes.class);

    @Value("${product.service.url}")
    private String productServiceUrl;

    @Value("${order.service.url}")
    private String orderServiceUrl;

    @Value("${inventory.service.url}")
    private String inventoryServiceUrl;

    @Bean
    public RouterFunction<ServerResponse> productServiceRoute() {
        return createServiceRoute("product_service", "/api/product/**", productServiceUrl, "productServiceCircuitBreaker");
    }

    @Bean
    public RouterFunction<ServerResponse> productServiceSwaggerRoute() {
        return createSwaggerRoute("product_service_swagger", "/aggregate/product-service/v3/api-docs", productServiceUrl, "productServiceSwaggerCircuitBreaker");
    }

    @Bean
    public RouterFunction<ServerResponse> categoryServiceRoute() {
        return createServiceRoute("category_service", "/api/category/**", productServiceUrl, "categoryServiceCircuitBreaker");
    }

    @Bean
    public RouterFunction<ServerResponse> categoryServiceSwaggerRoute() {
        return createSwaggerRoute("category_service_swagger", "/aggregate/product-service/v3/api-docs", productServiceUrl, "categoryServiceSwaggerCircuitBreaker");
    }

    @Bean
    public RouterFunction<ServerResponse> orderServiceRoute() {
        return createServiceRoute("order_service", "/api/order/**", orderServiceUrl, "orderServiceCircuitBreaker");
    }

    @Bean
    public RouterFunction<ServerResponse> orderServiceSwaggerRoute() {
        return createSwaggerRoute("order_service_swagger", "/aggregate/order-service/v3/api-docs", orderServiceUrl, "orderServiceSwaggerCircuitBreaker");
    }

    @Bean
    public RouterFunction<ServerResponse> inventoryServiceRoute() {
        return createServiceRoute("inventory_service", "/api/inventory/**", inventoryServiceUrl, "inventoryServiceCircuitBreaker");
    }

    @Bean
    public RouterFunction<ServerResponse> inventoryServiceSwaggerRoute() {
        return createSwaggerRoute("inventory_service_swagger", "/aggregate/inventory-service/v3/api-docs", inventoryServiceUrl, "inventoryServiceSwaggerCircuitBreaker");
    }

    private RouterFunction<ServerResponse> createServiceRoute(String id, String path, String uri, String circuitBreakerId) {
        return GatewayRouterFunctions.route(id)
                .route(RequestPredicates.path(path), HandlerFunctions.http(uri))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker(circuitBreakerId, URI.create("forward:/fallbackRoute")))
                .filter(logRequest(id))
                .build();
    }

    private RouterFunction<ServerResponse> createSwaggerRoute(String id, String path, String uri, String circuitBreakerId) {
        return GatewayRouterFunctions.route(id)
                .route(RequestPredicates.path(path), HandlerFunctions.http(uri))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker(circuitBreakerId, URI.create("forward:/fallbackRoute")))
                .filter(setPath("/v3/api-docs"))
                .filter(logRequest(id))
                .build();
    }

    private HandlerFilterFunction<ServerResponse, ServerResponse> logRequest(String id) {
        return (request, next) -> {
            logger.info("Request: for id {} {} {}", id, request.method(), request.uri());
            return next.handle(request);
        };
    }

    @Bean
    public RouterFunction<ServerResponse> fallBackRoute() {
        return GatewayRouterFunctions.route("fallbackRoute")
                .GET("/fallbackRoute", request -> ServerResponse
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body("Service Unavailable, Please try again later"))
                .build();
    }
}
