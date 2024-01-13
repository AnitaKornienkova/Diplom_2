package org.example;

import io.restassured.RestAssured;
import org.example.model.OrderData;
import org.example.model.UserData;
import org.example.steps.OrderSteps;
import org.example.steps.UserSteps;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

@RunWith(Parameterized.class)
public class GetOrdersTest {
    private String token;
    private final int expectedQuantity;

    public GetOrdersTest(int expectedQuantity) {
        this.expectedQuantity = expectedQuantity;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        UserData userData = new UserData(
                "someuser@email.com",
                "somepassword",
                "someusername0001"
        );

        token = UserSteps.successfullyRegisterUser(userData);

        OrderData orderData = new OrderData(
                List.of(
                        "61c0c5a71d1f82001bdaaa72",
                        "61c0c5a71d1f82001bdaaa6f",
                        "61c0c5a71d1f82001bdaaa6c"
                )
        );
        for (int i = 0; i < expectedQuantity; i++) {
            OrderSteps.successfullyCreateOrder(orderData, token);
        }
    }

    @Test
    public void getOrdersTest() {
        OrderSteps.getAllUserOrders(expectedQuantity, token);
    }

    @After
    public void cleanUp() {
        UserSteps.removeUser(token);
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {0},
                {1},
                {2},
                {3},
        };
    }
}
