package scriptfundamental;

import io.gatling.javaapi.core.*;

import static io.gatling.javaapi.core.CoreDsl.*;

// required for Gatling HTTP DSL
import io.gatling.javaapi.http.*;

import java.time.Duration;

import static io.gatling.javaapi.http.HttpDsl.*;

public class My2Test extends Simulation {

    // Http protocol
    HttpProtocolBuilder httpProtocol = http.baseUrl("https://videogamedb.uk/api");

    //
    ScenarioBuilder scn = scenario("Video game")
            .exec(
                    http("Get all video games")
                            .get("/videogame")
                            .check(
                                    status().is(200),
                                    jsonPath("$[?(@.id==1)].name").is("Resident Evil 4")
                            ))
            .pause(10)


            .exec(
                    http("Get only 1st video game")
                            .get("/videogame/1")
                            .check(status().in(200,201,202)))
            .pause(5, 10)


            .exec(
                    http("Get 2nd video game")
                            .get("/videogame/2")
                            .check(
                                    status().is(200),
                                    status().not(404)

                            ))
            .pause(Duration.ofMillis(100));

    {
        setUp(scn.injectOpen(atOnceUsers(1)).protocols(httpProtocol));
    }

}
