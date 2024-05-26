package scriptfundamental;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.*;
import java.util.stream.*;

public class My14Test extends Simulation {

    HttpProtocolBuilder httpProtocol =
            http.baseUrl("https://videogamedb.uk/api/")
                    .header("Content-Type", "application/json");


    public static String generateRandomDate(int startYear, int endYear) {
        // Generate a random date between startYear-01-01 and endYear-12-31
        long startEpochDay = LocalDate.of(startYear, 1, 1).toEpochDay();
        long endEpochDay = LocalDate.of(endYear, 12, 31).toEpochDay();
        long randomEpochDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay + 1);

        LocalDate randomDate = LocalDate.ofEpochDay(randomEpochDay);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return randomDate.format(formatter);
    }

    Iterator<Map<String, Object>> feeder =
            Stream.generate((Supplier<Map<String, Object>>) () -> {
                        String category = RandomStringUtils.randomAlphabetic(10);
                        String name = RandomStringUtils.randomAlphabetic(10);
                        String rating = RandomStringUtils.randomAlphabetic(10);

                        Random rand = new Random();
                        int reviewScore = rand.nextInt(100);

                        String releaseDate = generateRandomDate(1990, 2024);
                        HashMap<String, Object> map = new HashMap<>();

                        map.put("category", category);
                        map.put("name", name);
                        map.put("rating", rating);
                        map.put("reviewScore", reviewScore);
                        map.put("releaseDate", releaseDate);

                        return map;
                    }
            ).iterator();

    ChainBuilder authenticate_api =
            exec(http("Generate token")
                    .post("authenticate")
                    .body(RawFileBody("requestBody/authenticate_request.json"))
                    .check(status().is(200))
                    .check(jsonPath("$.token").saveAs("token"))
            );

    ChainBuilder create_a_video_game =
            feed(feeder)
                    .exec(http("create a video game")
                            .post("/videogame")
                            .header("Authorization", "Bearer #{token}")
                            .body(ElFileBody("requestBody/create_videogame_el.json"))
                            .check(status().is(200))
                            .check(bodyString().saveAs("response"))
                    )
                    .exec(session -> {
                        String response = session.getString("response");
                        System.out.println("moj:" + response);
                        return session;
                    });


    ScenarioBuilder myFirstScenario = scenario("My First Scenario")
            .exec(authenticate_api)
            .exec(create_a_video_game);

    {
        setUp(
                myFirstScenario.injectOpen(atOnceUsers(5))
        ).protocols(httpProtocol);
    }
}