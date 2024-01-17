package org.example;

import io.restassured.RestAssured;
import org.example.model.UserCredentials;
import org.example.model.UserData;
import org.example.steps.UserSteps;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.example.utils.Constants.STELLAR_BURGERS_URL;

public class AuthUserSuccessfullyTest {
    private String token;

    @Before
    public void setUp() {
        RestAssured.baseURI = STELLAR_BURGERS_URL;
    }

    @Test
    public void authUserSuccessfully() {
        String email = "someuser@email.com";
        String password = "somepassword";
        UserData userData = new UserData(email, password, "someusername");

        token = UserSteps.registerUserAndExpectSuccess(userData);

        UserSteps.authenticateAndExpectSuccess(new UserCredentials(email, password));
    }

    @After
    public void cleanUp() {
        UserSteps.removeUser(token);
    }
}
