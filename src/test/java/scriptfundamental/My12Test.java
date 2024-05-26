package scriptfundamental;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class My12Test extends Simulation {

    HttpProtocolBuilder httpProtocol =
            http.baseUrl("https://videogamedb.uk/api/")
                    .header("Content-Type", "application/json");

    FeederBuilder.FileBased<Object> feeder = jsonFile("datafeeder/gamedata_json.json").circular();

    ChainBuilder get_a_video_game =
            feed(feeder)
                    .exec(http("Get a video game")
                            .get("videogame/#{id}")
                            .check(status().is(200))
                            .check(jsonPath("$.name").isEL("#{name}"))
                            .check(jsonPath("$").saveAs("response"))
                    )
                    .exec(session -> {
                        String response = session.getString("response");
                        System.out.println("moj:" + response);
                        return session;
                    });


    ScenarioBuilder myFirstScenario = scenario("My First Scenario")
            .repeat(5).on(exec(get_a_video_game));

    {
        setUp(
                myFirstScenario.injectOpen(atOnceUsers(1))
        ).protocols(httpProtocol);
    }
}