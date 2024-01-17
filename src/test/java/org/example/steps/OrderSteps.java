package org.example.steps;

import io.qameta.allure.Step;
import org.example.model.OrderData;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

public class OrderSteps {
    @Step("Successfully create order")
    public static void createOrderAndExpectSuccess(OrderData orderData, String token) {
        given()
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .body(orderData)
                .when()
                .post("/api/orders")
                .then()
                .statusCode(200)
                .body("success", is(true))
                .body("order.number", is(greaterThan(0)))
                .body("name", notNullValue());
        ;
    }

    @Step("Error on creating order without authorization")
    public static void expectErrorOnCreatingOrderWithoutAuthorization(OrderData orderData) {
        // This case works strange due to real API response (it returns a random created before order).
        // In normal situation I would expect 401 response here, not an old already existing order.
        // Seems like it is a bug, but any information about expected behaviour is not provided in the API
        // documentation, so I made a test for checking current behaviour as an expected one.
        given()
                .header("Content-type", "application/json")
                .body(orderData)
                .when()
                .post("/api/orders")
                .then()
                .statusCode(200)
                .body("success", is(true))
                .body("order.number", is(greaterThan(0)))
                .body("name", notNullValue());
    }

    @Step("Error on creating order without ingredients")
    public static void expectErrorOnCreatingOrderWithoutIngredients(OrderData orderData, String token) {
        given()
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .body(orderData)
                .when()
                .post("/api/orders")
                .then()
                .statusCode(400)
                .body("success", is(false))
                .body("message", is("Ingredient ids must be provided"));
    }

    @Step("Error on creating order with wrong ingredients hash")
    public static void expectErrorOnCreatingOrderWithWrongIngredientsHash(OrderData orderData, String token) {
        given()
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .body(orderData)
                .when()
                .post("/api/orders")
                .then()
                .statusCode(500);
    }

    @Step("Get all user orders")
    public static void requestAllUserOrdersAndExpectSuccess(int expectedQuantity, String token) {
        given()
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .when()
                .get("/api/orders")
                .then()
                .statusCode(200)
                .body("success", is(true))
                .body("orders", hasSize(expectedQuantity));
    }

    @Step("Error on getting all user orders without authorization")
    public static void expectErrorOnRequestingAllUserOrdersWithoutAuthorization() {
        given()
                .header("Content-type", "application/json")
                .when()
                .get("/api/orders")
                .then()
                .statusCode(401)
                .body("success", is(false))
                .body("message", is("You should be authorised"));
    }
}
