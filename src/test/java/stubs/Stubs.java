package stubs;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.apache.commons.io.IOUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.IOException;
import java.nio.charset.Charset;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.*;

public class Stubs {

    @Test
    public void get_hello_returns_200() {
        when()
                .get("/hello").
        then()
                .statusCode(200).assertThat().body("title", is("Hello world!"));
    }

    @Test
    public void post_hello_returns_201() {
        when()
                .post("/hello").
        then()
                .statusCode(201).body("title", equalTo("Hello world created!"));
    }

    @Test
    public void delete_hello_returns_202() {
        when()
                .delete("/hello").
        then()
                .statusCode(202).body("title", equalTo("Hello world deleted!"));
    }

    @BeforeTest
    public void beforeTest() throws IOException {
        WireMockServer wireMockServer = new WireMockServer();
        wireMockServer.start();
        System.out.println("WireMock Server is running on port: " + wireMockServer.getOptions().portNumber());

        stubFor(get(urlEqualTo("/hello"))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody(getJSONBody("/responses/01_getHelloWorld.json"))));

        stubFor(post(urlEqualTo("/hello"))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n" +
                                "    \"id\": 2,\n" +
                                "    \"title\": \"Hello world created!\"\n" +
                                "}")));

        stubFor(delete(urlEqualTo("/hello"))
                .willReturn(aResponse()
                        .withStatus(202)
                        .withHeader("Content-Type", "application/json")
                        .withBody(getJSONBody("/responses/03_deleteHelloWorld.json"))));
    }

    private String getJSONBody(String path) throws IOException {
        return IOUtils.toString(this.getClass().getResourceAsStream(path), String.valueOf(Charset.forName("UTF-8")));
    }
}
