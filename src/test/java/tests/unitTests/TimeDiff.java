package tests.unitTests;

import Rates.CalculateRates;
import WireMock.WMServer;
import org.apache.commons.io.IOUtils;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.charset.Charset;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class TimeDiff {

    WMServer wmServer = new WMServer();
    CalculateRates calculateRates = new CalculateRates();

    String stubUrl = "http://localhost:8080/rates";
    private static String url = "http://api.nbp.pl/api/exchangerates/rates/A/USD/2019-12-01/2019-12-07/";

    @Test
    public void test() throws IOException {
        long timeStartReal = System.currentTimeMillis();
        calculateRates.getRatesFromServer(url);
        long timeStopReal = System.currentTimeMillis();
        calculateRates.getRatesFromServer(stubUrl);
        long timeStopStub = System.currentTimeMillis();
        System.out.println("Real API execution time: " + (timeStopReal-timeStartReal) +
                "\nStub API execution time: " + (timeStopStub - timeStopReal) +
                "\nDiff: " + (timeStopStub-timeStopReal-(timeStopReal-timeStartReal)));
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
