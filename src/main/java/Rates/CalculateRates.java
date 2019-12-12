package Rates;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class CalculateRates {

    private static String url = "http://api.nbp.pl/api/exchangerates/rates/A/USD/2019-12-01/2019-12-07/";

    public static void main(String args[]) throws IOException {
        new CalculateRates().getRatesFromServer(url);
    }


    public String getRatesFromServer(String url) throws IOException {
        URL urlForGetRequest = new URL(url);
        String readLine = null;
        HttpURLConnection connection = (HttpURLConnection) urlForGetRequest.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((readLine = in .readLine()) != null) {
                response.append(readLine);
            }
            in.close();
            System.out.println(response.toString());
            return response.toString();

        } else {
            return "GET NOT WORKING " + responseCode;
        }
    }
}
