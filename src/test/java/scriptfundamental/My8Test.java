package scriptfundamental;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class My8Test extends Simulation {

    HttpProtocolBuilder httpProtocol = http.baseUrl("https://videogamedb.uk/api");

    ChainBuilder get_only_single_game =
            exec(http("Get only single game")
                    .get(session -> "/videogame/" + (session.getInt("count") + 1))
                    .check(status().is(200))
            );


    ScenarioBuilder myFirstScenario = scenario("My First Scenario")
            .repeat(5, "count").on(
                    exec(get_only_single_game)
                            .pause(5)
            );


    {
        setUp(
                myFirstScenario.injectOpen(atOnceUsers(1))
        ).protocols(httpProtocol);
    }

}