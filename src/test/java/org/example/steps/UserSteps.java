package org.example.steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.model.UserCredentials;
import org.example.model.UserData;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

public class UserSteps {
    @Step("Successfully register user")
    public static String registerUserAndExpectSuccess(UserData userData) {
        ValidatableResponse response = given()
                .header("Content-type", "application/json")
                .body(userData)
                .when()
                .post("/api/auth/register")
                .then()
                .statusCode(200)
                .body("success", is(true));

        return response.extract().response().path("accessToken");
    }

    @Step("Register duplicate user")
    public static void expectErrorOnDuplicateRegistration(UserData userData) {
        given()
                .header("Content-type", "application/json")
                .body(userData)
                .when()
                .post("/api/auth/register")
                .then()
                .statusCode(403)
                .body("success", is(false))
                .body("message", is("User already exists"));
    }

    @Step("Register invalid user data")
    public static void expectErrorOnRegistrationUserWithInvalidData(UserData userData) {
        given()
                .header("Content-type", "application/json")
                .body(userData)
                .when()
                .post("/api/auth/register")
                .then()
                .statusCode(403)
                .body("success", is(false))
                .body("message", is("Email, password and name are required fields"));
    }

    @Step("Successfully auth user")
    public static String authenticateAndExpectSuccess(UserCredentials userCredentials) {
        ValidatableResponse response = given()
                .header("Content-type", "application/json")
                .body(userCredentials)
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(200)
                .body("success", is(true))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue());

        return response.extract().response().path("accessToken");
    }

    @Step("Auth user with wrong credentials")
    public static void expectErrorOnAuthenticationWithWrongCredentials(UserCredentials userCredentials) {
        given()
                .header("Content-type", "application/json")
                .body(userCredentials)
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(401)
                .body("success", is(false))
                .body("message", is("email or password are incorrect"));
    }

    @Step("Modify user successfully")
    public static void modifyUserDataAndExpectSuccess(UserData userData, String token) {
        ValidatableResponse response = given()
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .body(userData)
                .when()
                .patch("/api/auth/user")
                .then()
                .statusCode(200)
                .body("success", is(true));

        if (userData.getEmail() != null) {
            response.body("user.email", is(userData.getEmail()));
        }

        if (userData.getName() != null) {
            response.body("user.name", is(userData.getName()));
        }
    }

    @Step("Modify not authorized user")
    public static void expectErrorOnNotAuthorizedUserModification(UserData userData) {
        given()
                .header("Content-type", "application/json")
                .body(userData)
                .when()
                .patch("/api/auth/user")
                .then()
                .statusCode(401)
                .body("success", is(false))
                .body("message", is("You should be authorised"));
    }

    @Step("Remove user")
    public static void removeUser(String token) {
        given()
                .header("Authorization", token)
                .when()
                .delete("/api/auth/user")
                .then()
                .statusCode(202)
                .body("success", equalTo(true));
    }
}
