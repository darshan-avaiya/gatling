package scriptfundamental;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class My4Test extends Simulation {

    // Http protocol
    HttpProtocolBuilder httpProtocol = http.baseUrl("https://videogamedb.uk/api");

    // Scenario
    ScenarioBuilder scn = scenario("Video game")
            .exec(
                    http("Get all video games")
                            .get("/videogame")
                            .check(
                                    status().is(200),
                                    jsonPath("$[0].id").saveAs("id")
                            ))

            .exec(
                    http("Get only single video game")
                            .get("/videogame/#{id}")
                            .check(
                                    status().is(200),
                                    jsonPath("$.name").is("Resident Evil 4")
                            ));

    // Load simulation
    {
        setUp(scn.injectOpen(atOnceUsers(1)).protocols(httpProtocol));
    }

}
