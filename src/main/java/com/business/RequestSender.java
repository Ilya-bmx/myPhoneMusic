package com.business;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class RequestSender {

    public void sendGET() throws IOException {
        URL obj = new URL("http://192.168.1.64/list?path=%2F");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36");
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        // success case
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("GET request not worked");
        }

    }

    public void sendPOST(byte[] musicFile) throws IOException {
        HttpClient httpClient = new HttpClient();

        PostMethod postMethod = new PostMethod("http://192.168.1.64/upload");

        postMethod.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36");
        postMethod.setRequestHeader("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundaryS7EQTjL7Bn1gnqhG");
        postMethod.setRequestHeader("Accept", "application/json, text/javascript, */*; q=0.01");
        postMethod.setRequestHeader("Accept-Encoding","gzip, deflate");


        NameValuePair nameValuePair1 = new NameValuePair("path", "/");
        NameValuePair nameValuePair2 = new NameValuePair("files[]", Arrays.toString(musicFile));

        NameValuePair[] parametersBody = {nameValuePair1, nameValuePair2};
        postMethod.setRequestBody(parametersBody);

        int statusCode = httpClient.executeMethod(postMethod);

        if (statusCode != HttpStatus.SC_OK) {
            System.err.println("Method failed: " + postMethod.getStatusLine());
            System.out.println(postMethod.getResponseContentLength());
        }

        byte[] responseBody = new byte[100000];
        postMethod.getResponseBodyAsStream().read(responseBody);
        System.out.println(Arrays.toString(responseBody));

    }

    private String createPostBody(byte[] file) {
        Gson gson = new Gson();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("path", "/");
        jsonObject.addProperty("files[]", Arrays.toString(file));

        return gson.toJson(jsonObject);
    }

}
