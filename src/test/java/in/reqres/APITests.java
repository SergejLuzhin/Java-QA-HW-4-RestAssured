package in.reqres;

import groovy.json.JsonOutput;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;
import static org.hamcrest.Matchers.*;
import static specifications.Specification.requestSpecification;

public class APITests {

    @Test
    public void testUniqueNames() {
        List<String> firstNames =
                given()
                        .spec(requestSpecification())
                        .when()
                        .get("/users?page=2")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .body()
                        .jsonPath()
                        .getList("data.first_name", String.class);

        Set<String> uniqueNames = new HashSet<>(firstNames);

        System.out.println("\n---------------------\n");
        System.out.println(firstNames);

        Assert.assertEquals(
                firstNames.size(),
                uniqueNames.size(),
                "В ответе есть повторяющиеся first_name: " + firstNames
        );
    }

    @Test
    public void testSuccessfulLogin() {
        Map<String, String> requestData = new HashMap<>();
        requestData.put( "email", "eve.holt@reqres.in");
        requestData.put("password" , "cityslicka");

        String token = given()
                .spec(requestSpecification())
                .body(requestData)
                .when()
                .post("/login")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath()
                .getString("token");

        System.out.println(token);

        Assert.assertNotNull(
                token,
                "Ошибка авторизации, получен пустой токен"
                );
    }

    @Test
    public void testUnsuccessfulLogin() {
        Map<String, String> requestData = new HashMap<>();
        requestData.put( "email", "eve.holt@reqres.in");

        String error = given()
                .spec(requestSpecification())
                .body(requestData)
                .when()
                .post("/login")
                .then()
                .log().all()
                .statusCode(400)
                .extract()
                .body()
                .jsonPath()
                .getString("error");

        System.out.println(error);

        Assert.assertNotNull(
                error,
                "Ошибка неверной авторизации, не было получено сообщение об ошибке, хотя не был введен пароль"
        );
    }
}
