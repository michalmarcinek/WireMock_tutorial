package stubs;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.stubbing.Scenario.STARTED;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

public class Scenarios {

    @Test
    public void changing_temperature_test(){
        when().get("/temperature").
                then().statusCode(200).body("temperature", equalTo("20.0 *C"));
        when().get("/temperature").
                then().statusCode(200).body("temperature", equalTo("25.0 *C"));
        when().get("/temperature").
                then().statusCode(200).body("temperature", equalTo("30.0 *C"));
        when().get("/temperature").
                then().statusCode(200).body("temperature", equalTo("35.0 *C"));
    }

    @BeforeTest
    public void beforeTest() {
        WireMockServer wireMockServer = new WireMockServer();
        wireMockServer.start();
        System.out.println("WireMock Server is running on port: " + wireMockServer.getOptions().portNumber());
        stub();
    }

    public void stub() {
        stubFor(get(urlEqualTo("/temperature")).inScenario("Temperature")
                .whenScenarioStateIs(STARTED)
                .willReturn(aResponse().withHeader("Content-Type", "application/json")
                        .withBody("{\"temperature\": \"20.0 *C\"\n}"))
                .willSetStateTo("Temperature1"));

        stubFor(get(urlEqualTo("/temperature")).inScenario("Temperature")
                .whenScenarioStateIs("Temperature1")
                .willReturn(aResponse().withHeader("Content-Type", "application/json")
                        .withBody("{\"temperature\": \"25.0 *C\"\n}"))
                .willSetStateTo("Temperature2"));

        stubFor(get(urlEqualTo("/temperature")).inScenario("Temperature")
                .whenScenarioStateIs("Temperature2")
                .willReturn(aResponse().withHeader("Content-Type", "application/json")
                        .withBody("{\"temperature\": \"30.0 *C\"\n}"))
                .willSetStateTo("Temperature3"));

        stubFor(get(urlEqualTo("/temperature")).inScenario("Temperature")
                .whenScenarioStateIs("Temperature3")
                .willReturn(aResponse().withHeader("Content-Type", "application/json")
                        .withBody("{\"temperature\": \"35.0 *C\"\n}"))
                .willSetStateTo(STARTED));
    }
}
