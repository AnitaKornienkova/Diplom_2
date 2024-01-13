package org.example;

import io.restassured.RestAssured;
import org.example.model.UserData;
import org.example.steps.UserSteps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class CreateUserInvalidDataTest {
    private final UserData userData;

    public CreateUserInvalidDataTest(UserData userData) {
        this.userData = userData;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Test
    public void testInvalid() {
        UserSteps.registerInvalidUserData(userData);
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {new UserData(null, "somepassword", "someusername0001")},
                {new UserData("someuser@email.com", null, "someusername0001")},
                {new UserData("someuser@email.com", "somepassword", null)},
        };
    }
}
