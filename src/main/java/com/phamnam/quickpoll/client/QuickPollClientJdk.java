package com.phamnam.quickpoll.client;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class QuickPollClientJdk {
    public void readPoll(){
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try{
            URL restAPIUrl = new URL("http://localhost:8080/v1/polls");
            connection = (HttpURLConnection) restAPIUrl.openConnection();
            connection.setRequestMethod("GET");
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder jsonData = new StringBuilder();
            String line;
            while ((line= reader.readLine())!=null){
                jsonData.append(line);
            }
            System.out.println(jsonData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            // Clean up
            IOUtils.closeQuietly(reader);
            if(connection != null)
                connection.disconnect();
        }
    }
    public static void main(String[] args) throws Exception {
        QuickPollClientJdk client = new QuickPollClientJdk();
        client.readPoll();
    }
}
