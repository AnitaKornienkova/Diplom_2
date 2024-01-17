package org.example;

import io.restassured.RestAssured;
import org.example.model.UserData;
import org.example.steps.UserSteps;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.example.utils.Constants.STELLAR_BURGERS_URL;

public class CreateUserSuccessfullyTest {
    private String token;

    @Before
    public void setUp() {
        RestAssured.baseURI = STELLAR_BURGERS_URL;
    }

    @Test
    public void testCreateNewUser() {
        UserData userData = new UserData(
                "someuser@email.com",
                "somepassword",
                "someusername"
        );

        token = UserSteps.registerUserAndExpectSuccess(userData);
    }

    @After
    public void cleanUp() {
        UserSteps.removeUser(token);
    }
}
