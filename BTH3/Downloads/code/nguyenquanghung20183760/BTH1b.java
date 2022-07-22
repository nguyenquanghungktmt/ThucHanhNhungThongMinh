import java.io.*;
import java.net.URL;
import java.net.HttpURLConnection;
import org.json.JSONObject;

public class BTH1b {
    private static final String DOMAIN = "https://api.thingspeak.com/";
    private static final String API_KEY = "T7H40F0X82VGW7L5";

    public static void main(String[] args) {
        System.out.println("Bai thuc hanh 1b");

        String tailUrl = "update?api_key=%s";
        int field1 = 20;
        int field2 = 33;

        JSONObject data = new JSONObject();
        data.put("field1", field1);
        data.put("field2", field2);
        System.out.println("Request body data: " + data.toString());
        
        /* Create url */
        String url = String.format(DOMAIN + tailUrl, API_KEY);
        getDataFromThinkSpeaker(url, data);
    }
    
    public static void getDataFromThinkSpeaker(String urlString, JSONObject data){
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
            connect.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            connect.setDoOutput(true);

            /* Put JSON into request body */
            OutputStream os = connect.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(data.toString());
            writer.flush();
            writer.close();
            os.close();
            connect.connect();

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