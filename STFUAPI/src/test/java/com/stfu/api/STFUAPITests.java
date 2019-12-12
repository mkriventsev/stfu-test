package com.stfu.api;

import io.restassured.response.Response;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;

import java.util.Random;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class STFUAPITests {

    @Order(1)
    @Test
    @DisplayName("get /leaderboard and verify status code and respond's JSON Schema")
    void verifyGetLeaderboardJSONSchemaAndStatusCode() {

        System.out.println("1. [REQUEST] get /leaderboard");

        Response leaderBoard = given().urlEncodingEnabled(true)
                .spec(RequestHelper.setBases)
                .log().all()
                .when()
                .get(Constants.LEDEARBOARD);

        System.out.println("1. [RESPONSE] get /leaderboard");

        leaderBoard
                .then()
                .log().body()
                .assertThat().statusCode(200)
                .body(matchesJsonSchemaInClasspath("getLeaderBoardJSONSchema.json"));
    }

    @Order(2)
    @Test
    @DisplayName("post /klik and verify status code and respond JSON Schema")
    void verifyPostClickJSONSchemaAndStatusCode() {

        System.out.println("2. [REQUEST] post /klik");

        String clickSession = UUID.randomUUID().toString();
        JSONObject requestParams = new JSONObject();
        requestParams.put("team", "TeamName");
        requestParams.put("session", clickSession);
//                .body(requestParams.toJSONString())
//        System.out.println("3. [REQUEST] post 1st /klik for team +" + selectedNameTeam + " :");
//        Response postClick = given().urlEncodingEnabled(true)
//                .spec(RequestHelper.setBases)
//                .body(requestParams.toJSONString())


        Response click = given().urlEncodingEnabled(true)
                .spec(RequestHelper.setBases)
                .body(requestParams.toJSONString())
//                .param("team", "TeamName")
//                .param("session", clickSession)
                .log().all()
                .when()
                .post(Constants.CLICK);

        System.out.println("2. [RESPONSE] post /klik");


        click
                .then()
                .log().body()
                .assertThat().statusCode(200)
                .body(matchesJsonSchemaInClasspath("postClickResponseJSONSchema.json"));
    }

    @Order(3)
    @Test
    @DisplayName("check the team score is greater than before")
    public void checkNoOfClicksIsGreaterAfterClick() throws JSONException {

        System.out.println("3. [REQUEST] get /leaderboard");

        Response getLeaderboard = given().urlEncodingEnabled(true)
                .spec(RequestHelper.setBases)
                .log().all()
                .when()
                .get(Constants.LEDEARBOARD);

        System.out.println("3. [RESPONSE] get /leaderboard :");

        getLeaderboard
                .then()
                .log().body()
                .extract().response();

        int jsonResponsesize = getLeaderboard.jsonPath().getList("$").size();
        int randOrder = new Random().ints(1, jsonResponsesize).findFirst().getAsInt();

        int selectedTeamOrder = getLeaderboard.jsonPath().getInt("[" + randOrder + "].order");
        String selectedNameTeam = getLeaderboard.jsonPath().getString("[" + randOrder + "].team");
        int selectedTeamClicks = getLeaderboard.jsonPath().getInt("[" + randOrder + "].clicks");
        System.out.println("Selected team\nname: " + selectedNameTeam + "\norder:" +
                selectedTeamOrder + "\nclicks: " + selectedTeamClicks);

        String clickSession = UUID.randomUUID().toString();

        JSONObject requestParams = new JSONObject();
        requestParams.put("team", selectedNameTeam);
        requestParams.put("session", clickSession);
//                .body(requestParams.toJSONString())
        System.out.println("3. [REQUEST] post 1st /klik for team +" + selectedNameTeam + " :");
        Response postClick = given().urlEncodingEnabled(true)
                .spec(RequestHelper.setBases)
                .body(requestParams.toJSONString())

//                .body("{\n" +
//                        "  \"team\" : \"" + selectedNameTeam + "\",\n" +
//                        "  \"session\" : \"" + clickSession + "\"\n" +
//                        "}")
                .log().all()
                .when()
                .post(Constants.CLICK);
        System.out.println("3. [RESPONSE] post 1st /klik for team +" + selectedNameTeam + " :");
        postClick
                .then()
                .log().body()
                .extract()
                .response();

        assertThat(postClick.path("team_clicks"), greaterThan(selectedTeamClicks));


        int teamClicksPrev = postClick.path("team_clicks");
        int yourClicksPrev = postClick.path("your_clicks");

        System.out.println("3. [REQUEST] post 2nd /klik for team +" + selectedNameTeam + " :");
        Response secondPostClick = given().urlEncodingEnabled(true)
                .spec(RequestHelper.setBases)
                .body(requestParams.toJSONString())
                .log().all()
                .when()
                .post(Constants.CLICK);
        System.out.println("3. [RESPONSE] post 2nd /klik for team +" + selectedNameTeam + ":");
        secondPostClick
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("your_clicks", equalTo(yourClicksPrev + 1))
                .body("team_clicks", greaterThan(teamClicksPrev));
    }
}
