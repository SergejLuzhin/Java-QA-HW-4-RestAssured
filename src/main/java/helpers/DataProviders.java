package helpers;

import org.testng.annotations.DataProvider;

import java.util.HashMap;
import java.util.Map;

public class DataProviders {

    @DataProvider(name = "providerForTestUniqueNames")
    public static Object[][] providerForTestUniqueNames() {
        return new Object[][] {
                {2}             //int pageNumber
        };
    }

    @DataProvider(name = "providerForTestSuccessfulLogin")
    public static Object[][] providerForTestSuccessfulLogin() {
        Map<String, String> requestData = new HashMap<>();
        requestData.put( "email", "eve.holt@reqres.in");
        requestData.put("password" , "cityslicka");

        return new Object[][] {
                {requestData}
        };
    }

    @DataProvider(name = "providerForTestUnsuccessfulLogin")
    public static Object[][] providerForTestUnsuccessfulLogin() {
        Map<String, String> requestData = new HashMap<>();
        requestData.put( "email", "eve.holt@reqres.in");

        return new Object[][] {
                {requestData}
        };
    }

    @DataProvider(name = "providerForTestTagsCount")
    public static Object[][] providerForTestTagsCount() {
        return new Object[][] {
                {15}
        };
    }


}
