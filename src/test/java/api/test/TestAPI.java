package api.test;

import api.pojo.UserData;
import api.pojo.Users;
import api.resources.Specifications;
import com.google.gson.Gson;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.util.*;


import static api.pojo.UserData.payload;
import static api.pojo.Users.*;
import static io.restassured.RestAssured.given;
import static java.util.Collections.*;
import static org.hamcrest.Matchers.equalTo;

public class TestAPI {

    public static final String URL = "https://jsonplaceholder.typicode.com";
    SoftAssert softAssert = new SoftAssert();
    Gson gson = new Gson();
    private static final String body = "test body";
    private static final String title = "test title";
    private static final Integer userId = 1;

    @Test
    public void testCase_1(){
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        List<UserData> users = given()
                .when()
                .get("/posts")
                .then()
                .extract().body().jsonPath().getList("", UserData.class);
        List<Integer> ids = users.stream().map(UserData::getId).toList();
        List<Integer> sortedIds = ids.stream().sorted().toList();
        Assert.assertEquals(sortedIds, ids);
    }

    @Test
    public void testCase_2(){
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        String title = given()
                .when()
                .get("/posts/99")
                .then()
                .extract().body().jsonPath().get("title").toString();
        String body = given()
                .when()
                .get("/posts/99")
                .then()
                .extract().body().jsonPath().get("body").toString();
        softAssert.assertNotNull(title);
        softAssert.assertNotNull(body);
        softAssert.assertAll();
    }

    @Test
    public void testCase_3(){
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecError404());
        given()
                .when()
                .get("/posts/150")
                .then()
                .assertThat().body("", equalTo(emptyMap()));
    }

    @Test
    public void testCase_4() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK201());
        UserData user = given()
                .body(payload)
                .when()
                .post("/posts")
                .then()
                .extract().body().as(UserData.class);
        softAssert.assertEquals(user.getUserId(), userId);
        softAssert.assertEquals(user.getTitle(), title);
        softAssert.assertEquals(user.getBody(), body);
        softAssert.assertNotNull(user.getId());
        softAssert.assertAll();
    }

    @Test
    public void testCase_5(){
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        List<Users> users = given()
                .when()
                .get("/users")
                .then()
                .extract().body().jsonPath().getList("", Users.class);
        Users user5 = users.stream().filter(u -> u.getId().equals(5)).findFirst().orElse(null);
        String actualUser5 = gson.toJson(user5);
        Assert.assertEquals(actualUser5, expectedUser5);
    }

    @Test
    public void testCase_6(){
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        Users user = given()
                .when()
                .get("/users/5")
                .then()
                .extract().body().as(Users.class);
        String actualUser = gson.toJson(user);
        Assert.assertEquals(actualUser, expectedUser5);
    }
}
