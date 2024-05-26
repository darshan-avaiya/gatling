package scriptfundamental;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;


public class My16Test extends Simulation {

    // 1. Http configuration
    HttpProtocolBuilder httpProtocol = http
            .baseUrl("https://videogamedb.uk/api")
            .acceptHeader("application/json");


    // 2. Scenario definition
    ScenarioBuilder scn = scenario("My First Test Scenario")
            .exec(http("Get All Video Games").get("/videogame"));


    // 3. Load simulation - load simulation with fixed time
    {
        setUp(
                scn.injectOpen(
                        constantUsersPerSec(50).during(Duration.ofMinutes(5))
                )
        ).protocols(httpProtocol)
                .maxDuration(Duration.ofSeconds(60));
    }
}
