package com.kaustubh.models;

/**
 * POJO representing the request body for creating/updating a user.
 * Used directly as a REST Assured request body — REST Assured serializes
 * this object into JSON automatically, so tests never build raw JSON strings.
 */
public class CreateUserRequest {

    private String name;
    private String job;

    public CreateUserRequest() {
    }

    public CreateUserRequest(String name, String job) {
        this.name = name;
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
