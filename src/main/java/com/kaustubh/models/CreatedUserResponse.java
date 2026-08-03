package com.kaustubh.models;

/**
 * POJO for deserializing the POST /users (create user) response, which
 * echoes back the submitted fields plus a generated id and createdAt timestamp.
 */
public class CreatedUserResponse {

    private String name;
    private String job;
    private String id;
    private String createdAt;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
