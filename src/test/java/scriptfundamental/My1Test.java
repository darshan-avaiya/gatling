package scriptfundamental;

// required for Gatling core structure DSL

import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;

// required for Gatling HTTP DSL
import io.gatling.javaapi.http.*;
import static io.gatling.javaapi.http.HttpDsl.*;


public class My1Test extends Simulation {

    // 1. Http configuration
    HttpProtocolBuilder httpProtocol = http
            .baseUrl("https://videogamedb.uk/api")
            .acceptHeader("application/json");


    // 2. Scenario definition
    ScenarioBuilder scn = scenario("My First Test Scenario")
            .exec(http("Get All Video Games").get("/videogame"));


    // 3. Load simulation

    {
        setUp(
                scn.injectOpen(atOnceUsers(1))
        ).protocols(httpProtocol);
    }
}
