package specifications;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class Specification {

    public static RequestSpecification requestSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri("https://reqres.in/api")
                .setContentType("application/json")
                .addHeader("x-api-key", "reqres_9a5b3ef3548d452894fa0fe899501528")
                .build();
    }
}
