package scriptfundamental;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import java.time.Duration;

public class My11Test extends Simulation {

    HttpProtocolBuilder httpProtocol =
            http.baseUrl("https://videogamedb.uk/api/")
                    .header("Content-Type", "application/json")
                    ;

    FeederBuilder.FileBased<String> feeder = csv("datafeeder/gamedata_csv.csv").circular();

    ChainBuilder get_a_video_game =
            feed(feeder)
                    .exec(http("Get a video game")
                            .get("videogame/#{gameId}")
                            .check(status().is(200))
                            .check(jsonPath("$.name").isEL("#{gameName}"))
                    );


    ScenarioBuilder myFirstScenario = scenario("My First Scenario")
            .repeat(5).on(exec(get_a_video_game));

    {
        setUp(
                myFirstScenario.injectOpen(stressPeakUsers(10).during(Duration.ofSeconds(60)))
        ).protocols(httpProtocol);
    }
}