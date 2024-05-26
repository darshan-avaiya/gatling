package scriptfundamental;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import java.time.Duration;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;


public class My17Test extends Simulation {

    // mvn gatling:test -DUSERS=100 -DRAMP_DURATION=5 -DMAX_TEST_DURATION=60
    int USERS_COUNT = Integer.parseInt(System.getProperty("USERS","10"));
    int RAMP_DURATION = Integer.parseInt(System.getProperty("RAMP_DURATION","5"));
    int MAX_TEST_DURATION = Integer.parseInt(System.getProperty("MAX_TEST_DURATION","10"));

    @Override
    public void before() {
        System.out.println("Simulation is about to start!");
    }

    @Override
    public void after() {
        System.out.println("Simulation is finished!");
    }

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
                        constantUsersPerSec(USERS_COUNT).during(Duration.ofSeconds(RAMP_DURATION))
                )
        ).protocols(httpProtocol)
                .maxDuration(Duration.ofSeconds(MAX_TEST_DURATION));
    }
}
