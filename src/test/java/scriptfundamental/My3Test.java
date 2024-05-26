package scriptfundamental;

import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;

// required for Gatling HTTP DSL
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.http.HttpDsl.*;

public class My3Test extends Simulation {

    // Http protocol
    HttpProtocolBuilder httpProtocol = http.baseUrl("https://videogamedb.uk/api");

    // Scenario
    ScenarioBuilder scn = scenario("Video game")
            .exec(
                    http("Get all video games")
                            .get("/videogame")
                            .check(
                                    status().is(200),
                                    jsonPath("$[?(@.id==1)].name").is("Resident Evil 14")
                            ));


    // Load simulation
    {
        setUp(scn.injectOpen(atOnceUsers(1)).protocols(httpProtocol));
    }

}
