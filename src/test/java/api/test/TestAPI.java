package api.test;

import api.POJO.UserData;
import api.POJO.Users;
import api.resources.Specifications;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.restassured.http.ContentType;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TestAPI {

    public static final String URL = "https://jsonplaceholder.typicode.com";
    public static final String user5FilePath = "src/test/java/api/resources/user5.json";
    Gson gson = new Gson();



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
    public void testCase_5() throws FileNotFoundException {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        List<Users> users = given()
                .when()
                .get("/users")
                .then()
                .extract().body().jsonPath().getList("", Users.class);


        String json = gson.toJson(users.get(4));

        JsonObject user5json =  JsonParser.parseString(json).getAsJsonObject();
        Object obj = gson.fromJson(new FileReader(user5FilePath), Users.class);
        String testString = obj.toString();
        System.out.println(user5json);
        //System.out.println(user5FromFile + "from json file");
        System.out.println(json);
        Assert.assertEquals(json, testString);
    }
}
