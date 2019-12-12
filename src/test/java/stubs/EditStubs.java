package stubs;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.RestAssured;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.UUID;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static java.util.UUID.fromString;

public class EditStubs {

    @Test
    public void changing_temperature_test() {
        stubFor(get(urlEqualTo("/hello"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withStatus(200)
                        .withBody("Temperatura: 20.0 *C")));

        Assert.assertEquals(RestAssured.get("/hello").asString(), "Temperatura: 20.0 *C");

        UUID id = getMappingID();

        editStub(get(urlEqualTo("/hello"))
            .withId(id)
            .willReturn(aResponse()
                    .withStatus(201)
                    .withHeader("Content-Type", "text/plain")
                    .withBody("Temperatura: 21.0 *C")));

        Assert.assertEquals(RestAssured.get("/hello").asString(), "Temperatura: 21.0 *C");

        editStub(get(urlEqualTo("/hello"))
                .withId(id)
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "text/plain")
                        .withBody("Temperatura: 22.0 *C")));

        Assert.assertEquals(RestAssured.get("/hello").asString(), "Temperatura: 22.0 *C");
        //Assert.assertEquals(RestAssured.get("/hello").asString(), "Temperatura: 20.0 *C");
    }

    @BeforeTest
    public void beforeTest() {
        WireMockServer wireMockServer = new WireMockServer();
        wireMockServer.start();
        System.out.println("WireMock Server is running on port: " + wireMockServer.getOptions().portNumber());
    }

    private UUID getMappingID() {
        JSONObject jsonObject = new JSONObject(RestAssured.get("/__admin/mappings").asString());
        JSONArray jsonArray = jsonObject.getJSONArray("mappings");
        JSONObject mapping = jsonArray.getJSONObject(0);
        return fromString(mapping.getString("id"));
    }
}
