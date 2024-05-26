package scriptfundamental;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;


public class My15Test extends Simulation {

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
                scn.injectOpen(
                        atOnceUsers(1000),
                        rampUsers(100).during(10), // 3
                        constantUsersPerSec(2).during(15), // 4
                        constantUsersPerSec(2).during(15).randomized(), // 5
                        rampUsersPerSec(10).to(20).during(10), // 6
                        rampUsersPerSec(10).to(20).during(10).randomized(), // 7
                        stressPeakUsers(1000).during(20) // 8
                )
        ).protocols(httpProtocol);

    }
}
