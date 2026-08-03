package com.kaustubh.utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

/**
 * Centralizes RequestSpecification setup — base URI, content type, and logging —
 * so step definitions and services never repeat this config, and switching
 * environments (dev/staging/prod) is a one-line change.
 */
public class RequestSpecFactory {

    private static final String BASE_URI = "https://reqres.in/api";

    public static RequestSpecification getRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setContentType(ContentType.JSON)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();
    }
}
