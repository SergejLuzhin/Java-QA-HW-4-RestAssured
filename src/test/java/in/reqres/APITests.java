package in.reqres;

import data.*;
import helpers.DataProviders;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.testng.Assert;
import org.testng.annotations.Test;
import specifications.Specification;

import java.util.*;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static specifications.Specification.*;

public class APITests {
    @Feature("Проверка эндпоинта /api/users на reqres.in")
    @Story("Проврека уникальносить имен на заданной странице")
    @Test(dataProvider = "providerForTestUniqueNames", dataProviderClass = DataProviders.class)
    public void testUniqueNamesOnPage(int pageNumber) {
        System.out.println("Sending spec? -> " + Specification.requestSpecification());
        UserPagesDTO userPages = given()
                .spec(requestSpecification())
                .when()
                .get("/users?page=" + pageNumber)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .body().as(UserPagesDTO.class);

        List<String> fullNames = userPages.getData().stream()
                .map(user -> user.getFirstName() + " " + user.getLastName())
                .collect(Collectors.toList());

        List<String> notUniqueFullNames = fullNames.stream()
                .collect(Collectors.groupingBy(fullName -> fullName, Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        Assert.assertTrue(
                notUniqueFullNames.isEmpty(),
                "В ответе есть повторяющиеся имена пользователей: " + notUniqueFullNames
        );
    }

    @Feature("Проверка эндпоинта /api/login на reqres.in")
    @Story("Проврека успешного логина")
    @Test(dataProvider = "providerForTestSuccessfulLogin", dataProviderClass = DataProviders.class)
    public void testSuccessfulLogin(Map<String, String> requestData) {
        System.out.println("Sending spec? -> " + Specification.requestSpecification());
        LoginMessageDTO loginMessage = given()
                .spec(requestSpecification())
                .body(requestData)
                .when()
                .post("/login")
                .then()
                .log().all()
                .statusCode(200)
                .extract().body()
                .as(LoginMessageDTO.class);

        Assert.assertNotNull(
                loginMessage.getToken(),
                "Ошибка авторизации (при этом статус код = 200), получен пустой токен. Ошибка: " + loginMessage.getError()
                        + " Текст ошибки: " + loginMessage.getMessage()
        );
    }

    @Feature("Проверка эндпоинта /api/login на reqres.in")
    @Story("Проврека неуспешного логина")
    @Test(dataProvider = "providerForTestUnsuccessfulLogin", dataProviderClass = DataProviders.class)
    public void testUnsuccessfulLogin(Map<String, String> requestData) {
        System.out.println("Sending spec? -> " + Specification.requestSpecification());
        LoginMessageDTO loginMessage = given()
                .spec(requestSpecification())
                .body(requestData)
                .when()
                .post("/login")
                .then()
                .log().all()
                .statusCode(400)
                .extract().body()
                .as(LoginMessageDTO.class);

        Assert.assertNotNull(
                loginMessage.getError(),
                "Ошибка неверной авторизации (при этом статус код = 400), не было получено сообщение об ошибке, хотя не был введен пароль"
        );
    }

    @Feature("Проверка эндпоинта /api/unknown на reqres.in")
    @Story("Проврека сортировки полученных данных по годам")
    @Test
    public void testListResource() {
        System.out.println("Sending spec? -> " + Specification.requestSpecification());
        ResourceDTO resource = given()
                .spec(requestSpecification())
                .when()
                .get("/unknown")
                .then()
                .log().all()
                .statusCode(200)
                .extract().body()
                .as(ResourceDTO.class);

        List<Integer> yearsList = resource.getData().stream()
                .map(ColorDTO::getYear)
                .collect(Collectors.toList());

        boolean isSorted = yearsList.stream()
                .sorted()
                .collect(Collectors.toList())
                .equals(yearsList);

        Assert.assertTrue(isSorted, "Данные не отсортированы по годам! Полученные года: " + yearsList);
    }

    @Feature("Проверка https://gateway.autodns.com/")
    @Story("Проврека количества тегов в полученном ответе")
    @Test(dataProvider = "providerForTestTagsCount", dataProviderClass = DataProviders.class)
    public void testTagsCount(int desiredTagsCount) {
        System.out.println("Sending spec? -> " + Specification.requestSpecification());
        String xml = given()
                .when()
                .get("https://gateway.autodns.com/")
                .then()
                .log().all()
                .extract()
                .asString();

        Document doc = Jsoup.parse(xml, "", Parser.xmlParser());

        List<String> tagNames = doc.getAllElements()
                .stream()
                .map(Element::tagName)
                .filter(name -> !name.equals("#root"))
                .collect(Collectors.toList());

        Assert.assertEquals(
                tagNames.size(),
                desiredTagsCount,
                "Количество тегов в полученном ответе не равно " + desiredTagsCount + ". Вместо этого было найдено "
                        + tagNames.size() + " тегов. Список полученных тегов: " + tagNames
        );
    }
}
