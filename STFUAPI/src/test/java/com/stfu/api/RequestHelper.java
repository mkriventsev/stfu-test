package com.stfu.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;


class RequestHelper {

    static RequestSpecification setBases = new RequestSpecBuilder()
            .setBaseUri(Constants.BASE_URI)
            .setBasePath(Constants.BASE_PATH_API)
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", ContentType.JSON.getAcceptHeader())
            .build();

}