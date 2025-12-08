package specifications;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static helpers.Properties.testProperties;

public class Specification {

    public static RequestSpecification requestSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri("https://reqres.in/api")
                .setContentType("application/json")
                .addHeader("x-api-key", testProperties.apiKey())
                .build();
    }
}
