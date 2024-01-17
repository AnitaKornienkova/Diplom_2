package org.example;

import io.restassured.RestAssured;
import org.example.model.UserCredentials;
import org.example.model.UserData;
import org.example.steps.UserSteps;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.example.utils.Constants.STELLAR_BURGERS_URL;

public class ModifyUserTest {
    private String token;

    @Before
    public void setUp() {
        RestAssured.baseURI = STELLAR_BURGERS_URL;
    }

    @Test
    public void changeEmailTest() {
        UserData userData = new UserData(
                "someuser@email.com",
                "somepassword",
                "someusername0001"
        );

        token = UserSteps.registerUserAndExpectSuccess(userData);

        UserData newUserData = new UserData("anotheruser@email.com", null, null);
        UserSteps.modifyUserDataAndExpectSuccess(newUserData, token);
    }

    @Test
    public void changeNameTest() {
        UserData userData = new UserData(
                "someuser@email.com",
                "somepassword",
                "someusername0001"
        );

        token = UserSteps.registerUserAndExpectSuccess(userData);

        UserData newUserData = new UserData(null, null, "anotherUserName");
        UserSteps.modifyUserDataAndExpectSuccess(newUserData, token);
    }

    @Test
    public void changePasswordTest() {
        UserData userData = new UserData(
                "someuser@email.com",
                "somepassword",
                "someusername0001"
        );

        token = UserSteps.registerUserAndExpectSuccess(userData);

        UserData newUserData = new UserData(null, "anotherPassword", null);
        UserSteps.modifyUserDataAndExpectSuccess(newUserData, token);
        token = UserSteps.authenticateAndExpectSuccess(new UserCredentials("someuser@email.com", "anotherPassword"));
    }

    @Test
    public void modifyNonAuthorizedUser() {
        UserData newUserData = new UserData("anotheruser@email.com", null, null);
        UserSteps.expectErrorOnNotAuthorizedUserModification(newUserData);
    }

    @After
    public void cleanUp() {
        if (token != null) {
            UserSteps.removeUser(token);
        }
    }
}
