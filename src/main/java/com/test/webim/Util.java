package com.test.webim;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Util {

     final static String USER_AGENT = "Mozilla/5.0";

     final static String authURL = "https://oauth.vk.com/authorize?client_id=6853219&display=popup&redirect_uri=https://vkauthtest.herokuapp.com/callback/vk&scope=friends&response_type=code&v=5.92";

     final static String accessURL = "https://oauth.vk.com/access_token?client_id=6853219" +
            "&client_secret=2KumGIn4tq40s6NgVTS1&redirect_uri=https://vkauthtest.herokuapp.com/callback/vk&code=%s";

     final static String GET_FRIENDS = "https://api.vk.com/method/friends.get?user_id=%s&fields=photo_100&count=5&access_token=%s&v=5.92";

     final static String GET_PROFILE_INFO = "https://api.vk.com/method/users.get?user_ids=%s&access_token=%s&v=5.92";

    public static JSONObject serialized(String ResponseCode) throws Exception {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(ResponseCode);
        return (JSONObject) obj;
    }

    public static String send(String url) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // optional default is GET
        con.setRequestMethod("GET");
        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        HttpURLConnection httpConn = (HttpURLConnection) con;
        InputStream is;
        if (httpConn.getResponseCode() >= 400) {
            is = httpConn.getErrorStream();
        } else {
            is = httpConn.getInputStream();
        }

        StringBuffer response = null;
        try {
            System.out.println(new InputStreamReader(is).getEncoding());
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            String inputLine;
            response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (Exception ex) {
            System.out.println();
        }
        return response.toString();
    }
}
