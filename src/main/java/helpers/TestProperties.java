package helpers;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "file:src/main/resources/test.properties"
})
public interface TestProperties extends Config {

    @Config.Key("x.api.key")
    String apiKey();

}
