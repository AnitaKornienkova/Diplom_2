package org.example;

import io.restassured.RestAssured;
import org.example.steps.OrderSteps;
import org.junit.Before;
import org.junit.Test;

import static org.example.utils.Constants.STELLAR_BURGERS_URL;

public class GetOrdersNotAuthorizedTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = STELLAR_BURGERS_URL;
    }

    @Test
    public void getOrdersWithoutAuthorizationTest() {
        OrderSteps.expectErrorOnRequestingAllUserOrdersWithoutAuthorization();
    }
}
