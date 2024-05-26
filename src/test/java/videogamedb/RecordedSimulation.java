package videogamedb;

import java.time.Duration;
import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class RecordedSimulation extends Simulation {

  private HttpProtocolBuilder httpProtocol = http
    .baseUrl("https://videogamedb.uk")
    .inferHtmlResources(AllowList(), DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*\\.svg", ".*detectportal\\.firefox\\.com.*"))
    .acceptHeader("application/json")
    .acceptEncodingHeader("gzip, deflate, br")
    .acceptLanguageHeader("en-US,en;q=0.9")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36");
  
  private Map<CharSequence, String> headers_0 = Map.ofEntries(
    Map.entry("Content-Type", "application/json"),
    Map.entry("Origin", "https://videogamedb.uk"),
    Map.entry("Sec-Fetch-Dest", "empty"),
    Map.entry("Sec-Fetch-Mode", "cors"),
    Map.entry("Sec-Fetch-Site", "same-origin"),
    Map.entry("sec-ch-ua", "Chromium\";v=\"124\", \"Google Chrome\";v=\"124\", \"Not-A.Brand\";v=\"99"),
    Map.entry("sec-ch-ua-mobile", "?0"),
    Map.entry("sec-ch-ua-platform", "Windows")
  );
  
  private Map<CharSequence, String> headers_1 = Map.ofEntries(
    Map.entry("Content-Type", "application/json"),
    Map.entry("Origin", "https://videogamedb.uk"),
    Map.entry("Sec-Fetch-Dest", "empty"),
    Map.entry("Sec-Fetch-Mode", "cors"),
    Map.entry("Sec-Fetch-Site", "same-origin"),
    Map.entry("authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcxNjM5OTk1MCwiZXhwIjoxNzE2NDAzNTUwfQ.zhkp6rGZC0HeHAmjI7CUn10WOLlqdU6uhbwDMH_L2SU"),
    Map.entry("sec-ch-ua", "Chromium\";v=\"124\", \"Google Chrome\";v=\"124\", \"Not-A.Brand\";v=\"99"),
    Map.entry("sec-ch-ua-mobile", "?0"),
    Map.entry("sec-ch-ua-platform", "Windows")
  );
  
  private Map<CharSequence, String> headers_2 = Map.ofEntries(
    Map.entry("Sec-Fetch-Dest", "empty"),
    Map.entry("Sec-Fetch-Mode", "cors"),
    Map.entry("Sec-Fetch-Site", "same-origin"),
    Map.entry("sec-ch-ua", "Chromium\";v=\"124\", \"Google Chrome\";v=\"124\", \"Not-A.Brand\";v=\"99"),
    Map.entry("sec-ch-ua-mobile", "?0"),
    Map.entry("sec-ch-ua-platform", "Windows")
  );
  
  private Map<CharSequence, String> headers_3 = Map.ofEntries(
    Map.entry("Origin", "https://videogamedb.uk"),
    Map.entry("Sec-Fetch-Dest", "empty"),
    Map.entry("Sec-Fetch-Mode", "cors"),
    Map.entry("Sec-Fetch-Site", "same-origin"),
    Map.entry("authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcxNjM5OTk1MCwiZXhwIjoxNzE2NDAzNTUwfQ.zhkp6rGZC0HeHAmjI7CUn10WOLlqdU6uhbwDMH_L2SU"),
    Map.entry("sec-ch-ua", "Chromium\";v=\"124\", \"Google Chrome\";v=\"124\", \"Not-A.Brand\";v=\"99"),
    Map.entry("sec-ch-ua-mobile", "?0"),
    Map.entry("sec-ch-ua-platform", "Windows")
  );


  private ScenarioBuilder scn = scenario("RecordedSimulation")
    .exec(
      http("request_0")
        .post("/api/authenticate")
        .headers(headers_0)
        .body(RawFileBody("videogamedb/recordedsimulation/0000_request.json")),
      pause(31),
      http("request_1")
        .post("/api/videogame")
        .headers(headers_1)
        .body(RawFileBody("videogamedb/recordedsimulation/0001_request.json")),
      pause(17),
      http("request_2")
        .get("/api/videogame/1")
        .headers(headers_2),
      pause(13),
      http("request_3")
        .delete("/api/videogame/1")
        .headers(headers_3)
    );

  {
	  setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
  }
}
