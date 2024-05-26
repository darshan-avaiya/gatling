package scriptfundamental;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class My6Test extends Simulation {

    HttpProtocolBuilder httpProtocol = http.baseUrl("https://videogamedb.uk/api");


    ChainBuilder get_all_video_game =
            exec(http("Get all video game")
                    .get("/videogame")
                    .check(status().is(200))
                    .check(jsonPath("$[0].id").saveAs("id"))
            );

    ChainBuilder get_only_single_game =
            exec(http("Get only single game")
                    .get("/videogame/#{id}")
                    .check(status().is(200))
            );

    ScenarioBuilder myFirstScenario = scenario("My First Scenario")
            .exec(get_all_video_game)
            .pause(5,10)
            .exec(get_only_single_game)
            .pause(10);


    {
        setUp(
                myFirstScenario.injectOpen(atOnceUsers(1))
        ).protocols(httpProtocol);
    }

}