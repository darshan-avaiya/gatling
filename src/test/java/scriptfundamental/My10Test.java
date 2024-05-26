package scriptfundamental;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class My10Test extends Simulation {

    HttpProtocolBuilder httpProtocol =
            http.baseUrl("https://videogamedb.uk/api/")
                    .contentTypeHeader("application/json");

    ChainBuilder authenticate_api =
            exec(http("Authenticate api")
                    .post("/authenticate")
                    .body(RawFileBody("requestBody/authenticate_request.json"))
                    .check(jsonPath("$.token").saveAs("token"))
            );

    ChainBuilder create_a_game =
            exec(
              http("Create a game")
                      .post("/videogame")
                      .header("Authorization","Bearer #{token}")
                      .body(RawFileBody("requestBody/create_videogame.json"))
            );

    ScenarioBuilder myFirstScenario = scenario("My First Scenario")
            .exec(authenticate_api)
            .exec(create_a_game);


    {
        setUp(
                myFirstScenario.injectOpen(atOnceUsers(1))
        ).protocols(httpProtocol);
    }
}