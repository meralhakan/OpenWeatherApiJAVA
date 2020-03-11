import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Main {

    private static DecimalFormat df2 = new DecimalFormat("#.##");

    public static void main(String[] args) throws IOException {
        try {
            call_me();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void call_me() throws Exception {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter your location: ");
        String location = scan.nextLine();

        String apiKey = "YOUR_API_KEY";

        String url = "http://api.openweathermap.org/data/2.5/forecast?q=" + location + "&appid=" + apiKey;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // optional default is GET
        con.setRequestMethod("GET");
        //add request header

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        con.disconnect(); // connection disconnected

        //print in String
        System.out.println(response.toString());
        //Read JSON response and print
        JSONObject myResponse = new JSONObject(response.toString());

        JSONArray arr = myResponse.getJSONArray("list");

        String cityName = myResponse.getJSONObject("city").get("name").toString();

        System.out.println("result after Reading JSON Response");
        System.out.println(cityName);
        String copyData = "";

        for (int i = arr.length()-1; i >= 0; i--) {
            double temp = Double.parseDouble(arr.getJSONObject(i).getJSONObject("main").get("temp").toString())- 272.15; // kelvin to celsius 1 kelvin = =272.15 degrees Celsius
            String dt_txt = arr.getJSONObject(i).getString("dt_txt"); // YYYY-MM-DD HH-MM-SS
            String[] parts = dt_txt.split(" "); // Splitting String where has space

            if (!parts[0].equals(copyData)) {
                copyData = parts[0];
                System.out.print(parts[0] + ": ");
                System.out.println(df2.format(temp) + " C");
            }
        }
    }
}
