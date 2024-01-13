package org.example;

import io.restassured.RestAssured;
import org.example.model.UserCredentials;
import org.example.model.UserData;
import org.example.steps.UserSteps;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ModifyUserTest {
    private String token;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Test
    public void changeEmailTest() {
        UserData userData = new UserData(
                "someuser@email.com",
                "somepassword",
                "someusername0001"
        );

        token = UserSteps.successfullyRegisterUser(userData);

        UserData newUserData = new UserData("anotheruser@email.com", null, null);
        UserSteps.modifyUserData(newUserData, token);
    }

    @Test
    public void changeNameTest() {
        UserData userData = new UserData(
                "someuser@email.com",
                "somepassword",
                "someusername0001"
        );

        token = UserSteps.successfullyRegisterUser(userData);

        UserData newUserData = new UserData(null, null, "anotherUserName");
        UserSteps.modifyUserData(newUserData, token);
    }

    @Test
    public void changePasswordTest() {
        UserData userData = new UserData(
                "someuser@email.com",
                "somepassword",
                "someusername0001"
        );

        token = UserSteps.successfullyRegisterUser(userData);

        UserData newUserData = new UserData(null, "anotherPassword", null);
        UserSteps.modifyUserData(newUserData, token);
        token = UserSteps.successfullyAuthUser(new UserCredentials("someuser@email.com", "anotherPassword"));
    }

    @Test
    public void modifyNonAuthorizedUser() {
        UserData newUserData = new UserData("anotheruser@email.com", null, null);
        UserSteps.modifyNotAuthorizedUser(newUserData);
    }

    @After
    public void cleanUp() {
        if (token != null) {
            UserSteps.removeUser(token);
        }
    }
}
