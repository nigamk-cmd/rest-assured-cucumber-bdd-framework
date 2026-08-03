Feature: User API
  As a QA engineer
  I want to verify the User API behaves correctly
  So that I can be confident the API is reliable for consumers

  Background:
    Given the User API is available

  @smoke @get
  Scenario: Fetch a single existing user
    When I request the user with id 2
    Then the response status code should be 200
    And the response should contain the correct user id

  @get @deserialization
  Scenario: Deserialize single user response into a typed object
    When I request the user with id 2
    Then the response status code should be 200
    And the deserialized user should have a valid email

  @get @schema
  Scenario: Single user response matches the expected schema
    When I request the user with id 2
    Then the response status code should be 200
    And the response should match the "single-user-schema.json" schema

  @get @pagination
  Scenario: Fetch a paginated list of users
    When I request page 2 of the user list
    Then the response status code should be 200
    And the response should contain more than 0 users

  @negative @get
  Scenario: Requesting a non-existent user returns 404
    When I request the user with id 9999
    Then the response status code should be 404

  @post @serialization
  Scenario: Create a new user
    When I create a user named "Kaustubh Nigam" with job "QA Automation Engineer"
    Then the response status code should be 201
    And the created user's name should be "Kaustubh Nigam"
    And the created user's job should be "QA Automation Engineer"

  @put
  Scenario: Update an existing user's job title
    When I update user 2 with job title "Senior QA Automation Engineer"
    Then the response status code should be 200
    And the updated user's job should be "Senior QA Automation Engineer"

  @delete
  Scenario: Delete an existing user
    When I delete user 2
    Then the response status code should be 204

  @chained
  Scenario: Create a user then validate the extracted name matches
    When I create a user named "Priya Sharma" with job "SDET"
    Then the created user's name should be "Priya Sharma"
    And the created user response should contain a generated id
    
