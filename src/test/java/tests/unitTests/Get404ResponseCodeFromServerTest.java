package tests.unitTests;

import Rates.CalculateRates;
import WireMock.WMServer;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class Get404ResponseCodeFromServerTest {

    WMServer wmServer = new WMServer();
    CalculateRates calculateRates = new CalculateRates();

    String stubUrl = "http://localhost:8080/rates";

    @Test
    public void test() throws IOException {
        Assert.assertTrue(calculateRates.getRatesFromServer(stubUrl).equals("GET NOT WORKING 404"), "Method not working");
    }

    @AfterTest
    public void afterTest() {
        wmServer.stopServer();
    }

    @BeforeTest
    public void beforeTest() {
        wmServer.runServer();
        stubFor(get("/rates")
                .willReturn(aResponse()
                        .withStatus(404)
                        .withStatusMessage("Page Not Found")));
    }
}
