import java.io.*;
import java.net.URL;
import java.net.HttpURLConnection;
import org.json.JSONObject;
import org.json.JSONArray;

public class BTH2 {
    private static final String DOMAIN = "https://api.thingspeak.com/";

    public static void main(String[] args) {
        System.out.println("Bai thuc hanh 2 \n");

        /* Specify Parameters */
        String tailUrl = "channels/%d/feeds.json?results=%d";
        int numChannel = 1529099; /* Channel ID */
        int numRecord = 0; /* Number of record want to get */

        String url = String.format(DOMAIN + tailUrl, numChannel, numRecord);
        getDataFromChannel(url);
    }
    
    public static void getDataFromChannel(String urlString){
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
            connect.setRequestProperty("Accept", "application/json");
            connect.setDoOutput(true);

            /* Get response code from connection */
            responseCode = connect.getResponseCode();
            System.out.println("GET Response Code : " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connect.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseBody.append(line);
                }

                String data = responseBody.toString();
                System.out.println("Raw data: " + data);
                
                data = null;
                parseData(data);
                reader.close();
            }
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
    }

    public static void parseData(String data) {
        if (data == null) {
            System.out.println("\nNo data Temparature and Huminity");
            return;
        }

        System.out.println("\nTemparature and Huminity:");
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONObject channel = jsonObject.getJSONObject("channel");
            
            //TODO: Parsing dữ liệu json nhận được để lấy Temparature và Humidity của mỗi bản ghi

            String nameField1 = channel.getString("field1");
            String nameField2 = channel.getString("field2");

            JSONArray feeds = jsonObject.getJSONArray("feeds");
            for (int i = 0; i < feeds.length(); ++i) {
                JSONObject record = feeds.getJSONObject(i);
                int temparature = record.getInt("field1");
                int humidity = record.getInt("field2");

                System.out.printf("Record %d: \n", i);
                System.out.printf("Temparature: %d - Humidity: %d \n\n", temparature, humidity);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}