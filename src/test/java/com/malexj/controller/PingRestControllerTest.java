package com.malexj.controller;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

@QuarkusTest
class PingRestControllerTest {

    @Test
    void testHelloEndpoint() {
        given()
                .when().get("/api/v1/ping")
                .then()
                .statusCode(200)
                .body("message", equalTo("pong"));
    }
}
