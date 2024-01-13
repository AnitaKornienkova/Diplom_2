package org.example;

import io.restassured.RestAssured;
import org.example.model.UserCredentials;
import org.example.model.UserData;
import org.example.steps.UserSteps;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AuthUserSuccessfullyTest {
    private String token;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Test
    public void authUserSuccessfully() {
        String email = "someuser@email.com";
        String password = "somepassword";
        UserData userData = new UserData(email, password, "someusername");

        token = UserSteps.successfullyRegisterUser(userData);

        UserSteps.successfullyAuthUser(new UserCredentials(email, password));
    }

    @After
    public void cleanUp() {
        UserSteps.removeUser(token);
    }
}
