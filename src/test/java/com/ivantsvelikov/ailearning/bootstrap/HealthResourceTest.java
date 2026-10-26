package com.ivantsvelikov.ailearning.bootstrap;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
class HealthResourceTest {

    @Test
    void testHealthReturnsUp() {
        given().when().get("/health")
                .then().statusCode(200)
                .contentType("application/json")
                .body("status", equalTo("UP"));
    }

    @Test
    void testUnknownEndpointReturnsNotFound() {
        given().when().get("/not-a-route").then().statusCode(404);
    }
}
