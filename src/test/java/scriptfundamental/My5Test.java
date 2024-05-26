package scriptfundamental;

// required for Gatling core structure DSL

import io.gatling.javaapi.core.*;

import static io.gatling.javaapi.core.CoreDsl.*;

// required for Gatling HTTP DSL
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.http.HttpDsl.*;

public class My5Test extends Simulation {

    HttpProtocolBuilder httpProtocol = http.baseUrl("https://videogamedb.uk/api");

    ScenarioBuilder scn = scenario("Scenario")
            .exec(http("Get single video game")
                    .get("/videogame/1")
                    .check(status().is(201))
                    .check(jsonPath("$.name").saveAs("name"))
                    .check(bodyString().saveAs("responseBody"))
            )

            .exec(session -> {
                System.out.println("moj1 : " + session.getString("name"));
                System.out.println("moj2 : " + session.getString("responseBody"));
                return session;
            });


    {
        setUp(scn.injectOpen(atOnceUsers(1)).protocols(httpProtocol));
    }

}
