package tests.nbp;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static io.restassured.RestAssured.*;

public class NbpApiTest {

    boolean stub = true;

    String uri = "http://api.nbp.pl";
    String stubUri = "http://localhost";

    @Test
    public void check_gold_price_endpoint() {
        basePath = "/api/cenyzlota";

        Response response = given().when().get();
        response.then().statusCode(200);

        Assert.assertTrue(response.asString().contains("data"));
        Assert.assertTrue(response.asString().contains("cena"));
    }

    @Test
    public void check_chf_currency() {
        basePath = "/api/exchangerates/rates/a/chf";

        Response response = given().when().get();
        response.then().statusCode(200);

        Assert.assertTrue(response.asString().contains("\"table\":\"A\""));
        Assert.assertTrue(response.asString().contains("\"currency\":\"frank szwajcarski\""));
        Assert.assertTrue(response.asString().contains("\"code\":\"CHF\""));
    }

    @BeforeTest
    public void beforeTest() {
        baseURI = uri;
        if (stub) {
            baseURI = stubUri;
            WireMockServer wireMockServer = new WireMockServer();
            wireMockServer.start();
            stub();
        }
    }

    private void stub() {
        stubFor(get(urlEqualTo("/api/cenyzlota"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[\n" +
                                "    {\n" +
                                "        \"data\":\"2019-12-06\",\n" +
                                "        \"cena\":183.00\n" +
                                "    }\n" +
                                "]")));

        stubFor(get(urlEqualTo("/api/exchangerates/rates/a/chf"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n" +
                                "    \"table\":\"A\",\n" +
                                "    \"currency\":\"frank szwajcarski\",\n" +
                                "    \"code\":\"CHF\",\n" +
                                "    \"rates\":[\n" +
                                "        {\n" +
                                "            \"no\":\"236/A/NBP/2019\",\n" +
                                "            \"effectiveDate\":\"2019-12-06\",\n" +
                                "            \"mid\":3.8963\n" +
                                "        }\n" +
                                "    ]\n" +
                                "}")));
    }
}
