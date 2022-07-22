import org.json.JSONObject;
import java.io.*;
import java.net.URL;
import java.net.HttpURLConnection;

public class BTH1b {
    /* Define Key API and URL of Thingspeak cloud */
    private static final String keyApi = "T7H40F0X82VGW7L5";
    private static final String thinkSpeakUrl = "https://api.thingspeak.com";

    /* Send field1 and field2 to cloud using JSON */
    public static void updateDataUsingJSON(String urlStr, JSONObject data) {
        URL url;
        String line;
        int responseCode;
        BufferedReader reader;
        HttpURLConnection conn = null;
        StringBuilder responseBody = new StringBuilder();

        try {
            url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setDoOutput(true);

            /* Put JSON into request body */
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(data.toString());
            writer.flush();
            writer.close();
            os.close();
            conn.connect();

            /* Get response code from connection */
            responseCode = conn.getResponseCode();
            System.out.println("GET Response Code : " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new
                InputStreamReader(conn.getInputStream()));

                while ((line = reader.readLine()) != null) {
                    responseBody.append(line);
                }
                System.out.println("Last entry ID: " +
                responseBody.toString());
                reader.close();
            }

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[])  {
        //TODO: Tạo đối tượng json chứa dữ liệu (field1, field2) muốn gửi lên server
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("field1", 20);
            jsonObject.put("field2", 33);

            System.out.println(jsonObject.toString());

            String url = thinkSpeakUrl + "/update?api_key=" + keyApi;
            updateDataUsingJSON(url, jsonObject);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}