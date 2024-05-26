package scriptfundamental;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class My7Test extends Simulation {

    HttpProtocolBuilder httpProtocol = http.baseUrl("https://videogamedb.uk/api");

    ScenarioBuilder myFirstScenario = scenario("My First Scenario")
            .repeat(5, "count")
            .on(
                    exec(http("Get only single game")
                            .get("/videogame/#{count}")
                            .check(status().is(200))
                    ).pause(5)
            );


    {
        setUp(
                myFirstScenario.injectOpen(atOnceUsers(1))
        ).protocols(httpProtocol);
    }

}