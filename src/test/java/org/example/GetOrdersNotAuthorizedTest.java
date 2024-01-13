package org.example;

import io.restassured.RestAssured;
import org.example.steps.OrderSteps;
import org.junit.Before;
import org.junit.Test;

public class GetOrdersNotAuthorizedTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Test
    public void getOrdersWithoutAuthorizationTest() {
        OrderSteps.getAllUserOrdersWithoutAuthorization();
    }
}
