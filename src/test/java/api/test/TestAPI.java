package api.test;

import api.POJO.UserData;
import api.POJO.Users;
import api.resources.Specifications;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.*;
import java.util.*;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TestAPI {

    public static final String URL = "https://jsonplaceholder.typicode.com";
    public static final String pathToExpectedResult = "src/test/java/api/resources/ExpectedResultUser5.json";
    Gson gson = new Gson();

    public static String expectedUser5;
    static {
        try {
            expectedUser5 = FileUtils.readFileToString(new File(pathToExpectedResult));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCase_1(){
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        List<UserData> users = given()
                .when()
                .get("/posts")
                .then().log().all()
                .extract().body().jsonPath().getList("", UserData.class);
        List<Integer> ids = users.stream().map(UserData::getId).toList();
        List<Integer> sortedIds = ids.stream().sorted().toList();
        Assert.assertEquals(ids, sortedIds);
    }

    @Test
    public void testCase_2(){
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        String title = given()
                .when()
                .get("/posts/99")
                .then().log().body()
                .extract().body().jsonPath().get("title").toString();
        String body = given()
                .when()
                .get("/posts/99")
                .then()
                .extract().body().jsonPath().get("body").toString();
        Assert.assertNotNull(title);
        Assert.assertNotNull(body);
    }

    @Test
    public void testCase_3(){
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecError404());
        given()
                .when()
                .get("/posts/150")
                .then().log().all()
                .assertThat().body("", equalTo(Collections.emptyMap()));
    }

    @Test
    public void testCase_4() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK201());
        String body = "test body";
        String title = "test title";
        int userId = 1;

        JSONObject payload = new JSONObject()
                .put("body", body)
                .put("title", title)
                .put("userId", userId);

        UserData user = given()
                .contentType(ContentType.JSON)
                .body(payload.toString())
                .when()
                .post("/posts")
                .then().log().all()
                .extract().body().as(UserData.class);

        Assert.assertEquals(user.getUserId(), userId);
        Assert.assertEquals(user.getTitle(), title);
        Assert.assertEquals(user.getBody(), body);
        Assert.assertNotNull(user.getId());
    }

    @Test
    public void testCase_5(){
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        List<Users> users = given()
                .when()
                .get("/users")
                .then()
                .extract().body().jsonPath().getList("", Users.class);

        String actualUser5 = gson.toJson(users.get(4));

        System.out.println(actualUser5);
        System.out.println(expectedUser5);
        Assert.assertEquals(actualUser5, expectedUser5);
    }

    @Test
    public void testCase_6(){
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        String user = given()
                .when()
                .get("/users/5")
                .then()
                .extract().response().asString();

        Assert.assertEquals(user, expectedUser5);
    }
}
