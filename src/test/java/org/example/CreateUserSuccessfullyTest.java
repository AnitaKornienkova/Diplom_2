package org.example;

import io.restassured.RestAssured;
import org.example.model.UserData;
import org.example.steps.UserSteps;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreateUserSuccessfullyTest {
    private String token;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Test
    public void testCreateNewUser() {
        UserData userData = new UserData(
                "someuser@email.com",
                "somepassword",
                "someusername"
        );

        token = UserSteps.successfullyRegisterUser(userData);
    }

    @After
    public void cleanUp() {
        UserSteps.removeUser(token);
    }
}
