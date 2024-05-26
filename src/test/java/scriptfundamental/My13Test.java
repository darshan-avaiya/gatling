package scriptfundamental;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class My13Test extends Simulation {

    HttpProtocolBuilder httpProtocol =
            http.baseUrl("https://videogamedb.uk/api/")
                    .header("Content-Type", "application/json");


    Iterator<Map<String, Object>> customFeeder =
            Stream.generate((Supplier<Map<String, Object>>) () -> {
                        Random rand = new Random();
                        int low = 1;
                        int high = 10;
                        int number = rand.nextInt(high - low) + low;
                        return Map.of("id", number);
                    }
            ).iterator();

    ChainBuilder get_a_video_game =
            feed(customFeeder)
                    .exec(http("Get a video game")
                            .get("videogame/#{id}")
                            .check(status().is(200))
                            .check(bodyString().saveAs("response"))
                    )
                    .exec(session -> {
                        String response = session.getString("response");
                        System.out.println("moj:" + response);
                        return session;
                    });


    ScenarioBuilder myFirstScenario = scenario("My First Scenario")
            .repeat(1).on(exec(get_a_video_game));

    {
        setUp(
                myFirstScenario.injectOpen(atOnceUsers(1))
        ).protocols(httpProtocol);
    }
}