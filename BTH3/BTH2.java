import org.json.*;
import java.io.*;
import java.net.URL;
import java.net.HttpURLConnection;

public class BTH2 {
    /* Define Key API and URL of Thingspeak cloud */
    private static final String keyApi = "T7H40F0X82VGW7L5";
    private static final String thinkSpeakUrl = "https://api.thingspeak.com";


    /* Send field1 and field2 to cloud using JSON */
    public static void getDataFromChannel(String urlStr) {
        URL url;
        String line;
        int responseCode;
        BufferedReader reader;
        HttpURLConnection conn = null;
        StringBuilder responseBody = new StringBuilder();

        try {
            url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            /* Get response code from connection */
            responseCode = conn.getResponseCode();
            System.out.println("GET Response Code : " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new
                InputStreamReader(conn.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseBody.append(line);
                }
                System.out.println(responseBody.toString());

                String data = responseBody.toString();
                parseData(data);
                reader.close();
            }

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void parseData(String data) {
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
                System.out.printf("Temparature: %d - Humidity: %d \n", temparature, humidity);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) throws Exception  {
        int numChannel = 1529099; /* Channel ID */
        int numRecord = 2; /* Number of record want to get */
        String urlStr = "https://api.thingspeak.com/channels/" + numChannel + 
                        "/feeds.json?results=" + numRecord;
        getDataFromChannel(urlStr); 
    }
}