package org.example;

import io.restassured.RestAssured;
import org.example.model.UserData;
import org.example.steps.UserSteps;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreateUserDuplicateTest {
    private String token;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Test
    public void testDuplicate() {
        UserData userData = new UserData(
                "someuser@email.com",
                "somepassword",
                "someusername0001"
        );

        token = UserSteps.successfullyRegisterUser(userData);

        UserSteps.registerDuplicateUser(userData);
    }

    @After
    public void cleanUp() {
        UserSteps.removeUser(token);
    }
}
