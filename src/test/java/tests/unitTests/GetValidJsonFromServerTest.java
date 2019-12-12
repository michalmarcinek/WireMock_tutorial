package tests.unitTests;

import Rates.CalculateRates;
import WireMock.WMServer;
import org.apache.commons.io.IOUtils;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.IOException;
import java.nio.charset.Charset;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class GetValidJsonFromServerTest {

    WMServer wmServer = new WMServer();
    CalculateRates calculateRates = new CalculateRates();

    String stubUrl = "http://localhost:8080/rates";

    @Test
    public void test() throws IOException {
        Assert.assertTrue(calculateRates.getRatesFromServer(stubUrl).equals(getJSONBody()), "Method not working");
    }

    @AfterTest
    public void afterTest() {
        wmServer.stopServer();
    }

    @BeforeTest
    public void beforeTest() throws IOException {
        wmServer.runServer();
        stubFor(get("/rates")
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(getJSONBody())));
    }

    private String getJSONBody() throws IOException {
        return IOUtils.toString(this.getClass().getResourceAsStream("/validRates.json"), String.valueOf(Charset.forName("UTF-8")));
    }
}
