package org.example;

import io.restassured.RestAssured;
import org.example.model.OrderData;
import org.example.model.UserData;
import org.example.steps.OrderSteps;
import org.example.steps.UserSteps;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.example.utils.Constants.STELLAR_BURGERS_URL;

public class CreateOrderTest {
    private String token;

    @Before
    public void setUp() {
        RestAssured.baseURI = STELLAR_BURGERS_URL;
        UserData userData = new UserData(
                "someuser@email.com",
                "somepassword",
                "someusername0001"
        );

        token = UserSteps.registerUserAndExpectSuccess(userData);
    }

    @Test
    public void createOrderSuccessfully() {
        OrderData orderData = new OrderData(
                List.of(
                        "61c0c5a71d1f82001bdaaa72",
                        "61c0c5a71d1f82001bdaaa6f",
                        "61c0c5a71d1f82001bdaaa6c"
                )
        );

        OrderSteps.createOrderAndExpectSuccess(orderData, token);
    }

    @Test
    public void createOrderWithoutAuthorization() {
        OrderData orderData = new OrderData(
                List.of(
                        "61c0c5a71d1f82001bdaaa72",
                        "61c0c5a71d1f82001bdaaa6f",
                        "61c0c5a71d1f82001bdaaa6c"
                )
        );

        OrderSteps.expectErrorOnCreatingOrderWithoutAuthorization(orderData);
    }

    @Test
    public void createOrderWithoutIngredients() {
        OrderData orderData = new OrderData(emptyList());

        OrderSteps.expectErrorOnCreatingOrderWithoutIngredients(orderData, token);
    }

    @Test
    public void createOrderWithWrongIngredientsHash() {
        OrderData orderData = new OrderData(
                List.of(
                        "wrongHash",
                        "61c0c5a71d1f82001bdaaa6f",
                        "61c0c5a71d1f82001bdaaa6c"
                )
        );

        OrderSteps.expectErrorOnCreatingOrderWithWrongIngredientsHash(orderData, token);
    }

    @After
    public void cleanUp() {
        UserSteps.removeUser(token);
    }
}
