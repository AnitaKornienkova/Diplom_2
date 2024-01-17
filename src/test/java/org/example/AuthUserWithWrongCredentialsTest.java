package org.example;

import io.restassured.RestAssured;
import org.example.model.UserCredentials;
import org.example.model.UserData;
import org.example.steps.UserSteps;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.example.utils.Constants.STELLAR_BURGERS_URL;

@RunWith(Parameterized.class)
public class AuthUserWithWrongCredentialsTest {
    private String token;
    private final UserCredentials userCredentials;

    public AuthUserWithWrongCredentialsTest(UserCredentials userCredentials) {
        this.userCredentials = userCredentials;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = STELLAR_BURGERS_URL;
    }

    @Test
    public void authWithWrongCredentials() {
        String email = "someuser@email.com";
        String password = "somepassword";
        UserData userData = new UserData(email, password, "someusername");

        token = UserSteps.registerUserAndExpectSuccess(userData);

        UserSteps.expectErrorOnAuthenticationWithWrongCredentials(userCredentials);
    }

    @After
    public void cleanUp() {
        UserSteps.removeUser(token);
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {new UserCredentials("someuser1@email.com", "somepassword")},
                {new UserCredentials(null, "somepassword")},
                {new UserCredentials("someuser@email.com", "somepassword1")},
                {new UserCredentials("someuser@email.com", null)},
        };
    }
}
