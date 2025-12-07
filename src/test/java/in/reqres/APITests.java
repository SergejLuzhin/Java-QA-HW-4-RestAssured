package in.reqres;

import groovy.json.JsonOutput;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class APITests {

    @Test
    public void testUniqueNames() {
        List<String> firstNames =
                given()
                        .header("x-api-key", "reqres_9a5b3ef3548d452894fa0fe899501528")
                        .when()
                        .get("https://reqres.in/api/users?page=2")
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
        requestData
    }
}
