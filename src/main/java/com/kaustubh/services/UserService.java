package com.kaustubh.services;

import com.kaustubh.models.CreateUserRequest;
import com.kaustubh.utils.RequestSpecFactory;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * Encapsulates all API calls for the "users" resource.
 *
 * This plays the same role for API testing that a Page Object plays for UI
 * testing: step definitions never build raw requests directly — they call
 * a method here. If an endpoint path or request shape changes, it's updated
 * in exactly one place instead of in every step definition that touches it.
 */
public class UserService {

    public Response getUser(int userId) {
        return given()
                .spec(RequestSpecFactory.getRequestSpec())
                .when()
                .get("/users/" + userId);
    }

    public Response getUserList(int page) {
        return given()
                .spec(RequestSpecFactory.getRequestSpec())
                .when()
                .get("/users?page=" + page);
    }

    public Response createUser(CreateUserRequest newUser) {
        return given()
                .spec(RequestSpecFactory.getRequestSpec())
                .body(newUser)
                .when()
                .post("/users");
    }

    public Response updateUser(int userId, CreateUserRequest updatedUser) {
        return given()
                .spec(RequestSpecFactory.getRequestSpec())
                .body(updatedUser)
                .when()
                .put("/users/" + userId);
    }

    public Response deleteUser(int userId) {
        return given()
                .spec(RequestSpecFactory.getRequestSpec())
                .when()
                .delete("/users/" + userId);
    }
}
