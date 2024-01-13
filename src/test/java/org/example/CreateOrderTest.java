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

public class CreateOrderTest {
    private String token;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        UserData userData = new UserData(
                "someuser@email.com",
                "somepassword",
                "someusername0001"
        );

        token = UserSteps.successfullyRegisterUser(userData);
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

        OrderSteps.successfullyCreateOrder(orderData, token);
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

        OrderSteps.errorOnCreatingOrderWithoutAuthorization(orderData);
    }

    @Test
    public void createOrderWithoutIngredients() {
        OrderData orderData = new OrderData(emptyList());

        OrderSteps.errorOnCreatingOrderWithoutIngredients(orderData, token);
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

        OrderSteps.errorOnCreatingOrderWithWrongIngredientsHash(orderData, token);
    }

    @After
    public void cleanUp() {
        UserSteps.removeUser(token);
    }
}
