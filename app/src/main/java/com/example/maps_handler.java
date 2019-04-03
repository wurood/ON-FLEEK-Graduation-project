package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by wurood on 11/7/2017.
 */

public class maps_handler {

    public maps_handler(){


    }

    public String getHttpdata (String req){
        URL url;
        String resp="";

        try{
            url= new URL(req);
            HttpURLConnection conn =(HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type","application/x-www-from-urlencoded");

            int respcode=conn.getResponseCode();
            if(respcode== HttpURLConnection.HTTP_OK){
                String line;
                BufferedReader red=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while((line=red.readLine() )!=null){
                  resp+=line;
                }
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  resp;
    }
}
