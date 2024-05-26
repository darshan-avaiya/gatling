package scriptfundamental;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class My9Test extends Simulation {

    HttpProtocolBuilder httpProtocol =
            http.baseUrl("https://videogamedb.uk/api")
                    .contentTypeHeader("application/json");

    ChainBuilder generate_token =
            exec(http("Authenticate api")
                    .post("/authenticate")
                    .body(StringBody("{\n" +
                            "  \"password\": \"admin\",\n" +
                            "  \"username\": \"admin\"\n" +
                            "}"))
                    .check(jsonPath("$.token").saveAs("token"))
            );

    ChainBuilder create_video_game =
            exec(http("Post video game")
                    .post("/videogame")
                    .header("Authorization","Bearer #{token}")
                    .body(StringBody("" +
                            "{\n" +
                            "  \"category\": \"Platform\",\n" +
                            "  \"name\": \"Mario\",\n" +
                            "  \"rating\": \"Mature\",\n" +
                            "  \"releaseDate\": \"2012-05-04\",\n" +
                            "  \"reviewScore\": 85\n" +
                            "}"))
                    .check(status().is(200))
            );


    ScenarioBuilder myFirstScenario = scenario("Video game test")
            .exec(generate_token)
            .exec(create_video_game);


    {
        setUp(
                myFirstScenario.injectOpen(atOnceUsers(1))
        ).protocols(httpProtocol);
    }
}