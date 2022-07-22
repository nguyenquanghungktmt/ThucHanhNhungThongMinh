import java.io.*;
import java.net.URL;
import java.net.HttpURLConnection;

public class BTH1a{
    private static final String DOMAIN = "https://api.thingspeak.com/";
    private static final String API_KEY = "T7H40F0X82VGW7L5";

    public static void main(String[] args) {
        System.out.println("Bai thuc hanh 1a");

        String tailUrl = "update?api_key=%s&field1=%d&field2=%d";
        int field1 = 20;
        int field2 = 33;
        
        /* Create url */
        String url = String.format(DOMAIN + tailUrl, API_KEY, field1, field2);
        getDataFromThinkSpeaker(url);
    }
    
    public static void getDataFromThinkSpeaker(String urlString){
        URL url;
        String line;
        int responseCode;
        BufferedReader reader;
        HttpURLConnection connect = null;
        StringBuilder responseBody = new StringBuilder();

        try {
            url = new URL(urlString);
            connect = (HttpURLConnection) url.openConnection();
            connect.setRequestMethod("GET");

            /* Get response code from connection */
            responseCode = connect.getResponseCode();
            System.out.println("GET Response Code : " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connect.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseBody.append(line);
                }
                System.out.println(responseBody.toString());
                reader.close();
            }
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
    }
}