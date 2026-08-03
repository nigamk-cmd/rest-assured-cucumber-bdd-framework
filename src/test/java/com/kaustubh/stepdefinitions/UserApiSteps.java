package com.kaustubh.stepdefinitions;

import com.kaustubh.models.CreateUserRequest;
import com.kaustubh.models.CreatedUserResponse;
import com.kaustubh.models.UserResponse;
import com.kaustubh.services.UserService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.Assert;

/**
 * Step definitions — the "glue" layer connecting Gherkin steps in the
 * feature file to actual Java/REST Assured code. Notice these methods stay
 * thin: they call UserService for the actual HTTP work and just handle
 * request setup + assertions, keeping the mapping between plain-English
 * steps and code easy to follow.
 */
public class UserApiSteps {

    private final UserService userService = new UserService();
    private Response response;

    @Given("the User API is available")
    public void theUserApiIsAvailable() {
        // No-op health check placeholder — reqres.in is a public sandbox with no
        // dedicated health endpoint. In a real project this might ping a /health route.
    }

    @When("I request the user with id {int}")
    public void iRequestTheUserWithId(int userId) {
        response = userService.getUser(userId);
    }

    @When("I request page {int} of the user list")
    public void iRequestPageOfTheUserList(int page) {
        response = userService.getUserList(page);
    }

    @When("I create a user named {string} with job {string}")
    public void iCreateAUserNamedWithJob(String name, String job) {
        CreateUserRequest newUser = new CreateUserRequest(name, job);
        response = userService.createUser(newUser);
    }

    @When("I update user {int} with job title {string}")
    public void iUpdateUserWithJobTitle(int userId, String job) {
        CreateUserRequest updatedUser = new CreateUserRequest(null, job);
        response = userService.updateUser(userId, updatedUser);
    }

    @When("I delete user {int}")
    public void iDeleteUser(int userId) {
        response = userService.deleteUser(userId);
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {
        Assert.assertEquals(response.getStatusCode(), expectedStatusCode,
                "Unexpected status code");
    }

    @Then("the response should contain the correct user id")
    public void theResponseShouldContainTheCorrectUserId() {
        int id = response.jsonPath().getInt("data.id");
        Assert.assertEquals(id, 2, "Response user id did not match requested id");
    }

    @Then("the deserialized user should have a valid email")
    public void theDeserializedUserShouldHaveAValidEmail() {
        UserResponse userResponse = response.as(UserResponse.class);
        Assert.assertTrue(userResponse.getData().getEmail().contains("@"),
                "Deserialized email does not look valid");
        Assert.assertFalse(userResponse.getData().getFirst_name().isEmpty(),
                "Deserialized first_name should not be empty");
    }

    @Then("the response should match the {string} schema")
    public void theResponseShouldMatchTheSchema(String schemaFileName) {
        response.then().assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/" + schemaFileName));
    }

    @Then("the response should contain more than {int} users")
    public void theResponseShouldContainMoreThanUsers(int minCount) {
        int dataSize = response.jsonPath().getList("data").size();
        Assert.assertTrue(dataSize > minCount, "Expected more than " + minCount + " users in response");
    }

    @Then("the created user's name should be {string}")
    public void theCreatedUsersNameShouldBe(String expectedName) {
        CreatedUserResponse createdUser = response.as(CreatedUserResponse.class);
        Assert.assertEquals(createdUser.getName(), expectedName, "Created user's name did not match");
    }

    @Then("the created user's job should be {string}")
    public void theCreatedUsersJobShouldBe(String expectedJob) {
        CreatedUserResponse createdUser = response.as(CreatedUserResponse.class);
        Assert.assertEquals(createdUser.getJob(), expectedJob, "Created user's job did not match");
    }

    @Then("the updated user's job should be {string}")
    public void theUpdatedUsersJobShouldBe(String expectedJob) {
        String job = response.jsonPath().getString("job");
        Assert.assertEquals(job, expectedJob, "Updated user's job did not match");
    }

    @Then("the created user response should contain a generated id")
    public void theCreatedUserResponseShouldContainAGeneratedId() {
        CreatedUserResponse createdUser = response.as(CreatedUserResponse.class);
        Assert.assertNotNull(createdUser.getId(), "Expected a generated id in the create-user response");
    }
}
