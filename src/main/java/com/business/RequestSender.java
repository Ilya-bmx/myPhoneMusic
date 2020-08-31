package com.business;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestSender {

    private final String PHONE_INTERFACE_URL;

    RequestSender(String phoneUrl) {
        this.PHONE_INTERFACE_URL = phoneUrl;
    }

    public void sendGET() throws IOException {
        URL obj = new URL(PHONE_INTERFACE_URL + "list?path=%2F");
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

    public void sendPOST(File musicFile) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(PHONE_INTERFACE_URL + "upload");

        FileBody uploadFilePart = new FileBody(musicFile);
        StringBody stringBody = null;
        try {
            stringBody = new StringBody("/");
        } catch (UnsupportedEncodingException e) {
            System.out.println("Error while making string body for http post");
        }
        MultipartEntity reqEntity = new MultipartEntity();
        reqEntity.addPart("path", stringBody);
        reqEntity.addPart("files[]", uploadFilePart);
        httpPost.setEntity(reqEntity);

        HttpResponse response = null;
        try {
            response = httpclient.execute(httpPost);
        } catch (IOException e) {
            System.out.println("Error while executing hhtpPost " + e.toString());
        }

        System.out.println("Response with status: " + (response != null ? response.getStatusLine() : null));
    }
}
