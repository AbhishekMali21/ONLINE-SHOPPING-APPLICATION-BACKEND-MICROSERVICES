package com.shopping.productservice;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

import static org.hamcrest.Matchers.*;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void shouldCreateProduct() {
        // Create a category first
        String categoryRequest =
                """
                {
                    "cName": "Phones",
                    "cDescription": "Latest Smart Phones"
                }
                """;
        String cId = RestAssured.given()
                .contentType("application/json")
                .body(categoryRequest)
                .when()
                .post("/api/category")
                .then()
                .statusCode(201)
                .extract()
                .path("cId");

        // Use the generated cId to create a product
        String productRequest =
                """
                {
                    "pName": "iPhone 15",
                    "pDescription" : "iPhone 15 smartphone from Apple",
                    "pPrice": 100,
                    "cId": "%s"
                }
                """.formatted(cId);
        RestAssured.given()
                .contentType("application/json")
                .body(productRequest)
                .when()
                .post("/api/product")
                .then()
                .statusCode(201)
                .body("pId", notNullValue())
                .body("pName", equalTo("iPhone 15"))
                .body("pDescription", equalTo("iPhone 15 smartphone from Apple"))
                .body("cId", equalTo(cId));
    }
}
