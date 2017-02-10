package com.mahmz.android.Classes;
import android.app.Activity;

import com.mahmz.android.Settings.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Mah on 3/23/2016.
 */
public class HttpPost {
    // http post method with json
    public static String httpPost(String requestURL, JSONObject postDataParams) {
        URL url;
        StringBuffer response = new StringBuffer();
        try {
            url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(postDataParams.toString());
            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
            } else {
                return "Http Post Request Not OK";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.toString();
    }

    // convert string to json and get value
    public static String getJSONValue(String key, String response) {
        JSONObject jObj = null;
        String value = "";
        try {
            jObj = new JSONObject(response);
            value = jObj.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return value;
        }
        return value;
    }

    // post json values , return json
    public static JSONObject getJsonByPost(String postUrl, JSONObject values) {
        String res = "";
        JSONObject result = null;
        res = HttpPost.httpPost(postUrl, values);
        try {
            result = new JSONObject(res);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
