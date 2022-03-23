package api;


import io.restassured.http.ContentType;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TestAPI {

    public static final String URL = "https://jsonplaceholder.typicode.com";

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
    public void testCase_5() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        List<Users> users = given()
                .when()
                .get("/users")
                .then()
                .extract().body().jsonPath().getList("", Users.class);

        JSONObject address = new JSONObject()
                .put("street", "Skiles Walks")
                .put("suite", "Suite 351")
                .put("city", "Roscoeview")
                .put("zipcode", "33263");

        JSONObject geo = new JSONObject()
                .put("lat", "-31.8129")
                .put("lng", "62.5342");

        JSONObject company = new JSONObject()
                .put("name", "Keebler LLC,")
                .put("catchPhrase", "User-centric fault-tolerant solution")
                .put("bs", "revolutionize end-to-end systems");

        JSONObject user5 = new JSONObject()
                .put("id", 5)
                .put("name", "Chelsey Dietrich")
                .put("username", "Kamren")
                .put("email", "Lucio_Hettinger@annie.ca")
                .put("address", address)
                .put("geo", geo)
                .put("phone", "(254)954-1289")
                .put("website", "demarco.info")
                .put("company", company);

        Users userWithId5 = users.get(4);
        JSONObject userId5 = new JSONObject(userWithId5);
        Assert.assertEquals(userId5, user5);


        int a = 1;
        //System.out.println(Arrays.toString(users.toArray()));

        //List<Integer> sortedIds = ids.stream().sorted().toList();
        //Assert.assertEquals(ids, sortedIds);
    }
  /*      UnSuccessRegister unSuccessRegister = given()
                .body(user)
                .when()
                .post("/posts")
                .then().log().all()
                .extract().as(UnSuccessRegister.class);

        Assert.assertEquals("Missing password", unSuccessRegister.getError());
    }

    @Test
    public void sortedYearsTest(){
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        List<Colors> colors = given()
                .when()
                .get("api/unknown")
                .then().log().all()
                .extract().body().jsonPath().getList("data", Colors.class);

        List<Integer> years = colors.stream().map(Colors::getYear).toList();
        List<Integer> sortedYears = years.stream().sorted().toList();

        Assert.assertEquals(years, sortedYears);
        System.out.println(years);
        System.out.println(sortedYears);
    }

    @Test
    public void deleteUserTest(){
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUnique(204));
        given()
                .when()
                .delete("api/users/2")
                .then().log().all();
    }

    @Test
    public void userTimeTest(){
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        UserTime user = new UserTime("morpheus", "zion resident");
        UserTimeResponse response = given()
                .body(user)
                .when()
                .put("api/users/2")
                .then().log().all()
                .extract().as(UserTimeResponse.class);
        String regex = "(.{5})$";
        String currentTime = Clock.systemUTC().instant().toString().replaceAll("(.{8})$", "");
        System.out.println(Clock.systemUTC().instant().toString());
        System.out.println(currentTime);
        System.out.println(response.getUpdatedAt().replaceAll(regex, ""));
        Assert.assertEquals(currentTime, response.getUpdatedAt().replaceAll(regex, ""));
    }
*/


}
