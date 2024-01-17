package org.example;

import io.restassured.RestAssured;
import org.example.model.UserData;
import org.example.steps.UserSteps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.example.utils.Constants.STELLAR_BURGERS_URL;

@RunWith(Parameterized.class)
public class CreateUserInvalidDataTest {
    private final UserData userData;

    public CreateUserInvalidDataTest(UserData userData) {
        this.userData = userData;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = STELLAR_BURGERS_URL;
    }

    @Test
    public void testInvalid() {
        UserSteps.expectErrorOnRegistrationUserWithInvalidData(userData);
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
