package WireMock;

import com.github.tomakehurst.wiremock.WireMockServer;

public class WMServer {
    WireMockServer wireMockServer = new WireMockServer();

    public static void main(String args[]) {
        new WMServer().runServer();
    }

    public void runServer() {
        wireMockServer.start();
        System.out.println("WireMock Server is running on port: " + wireMockServer.getOptions().portNumber());
    }

    public void stopServer() {
        wireMockServer.stop();
    }
}
