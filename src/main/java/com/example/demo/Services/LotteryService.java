package com.example.demo.Services;

import com.example.demo.Models.Lottery;
import com.google.gson.Gson;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class LotteryService {
    public Lottery getResultLottery() throws IOException {
        String query = "https://api.krupreecha.com/01062563";
        URL url = new URL(query);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setRequestProperty("x-api-key", "cc6be5e7202da486b28945c4c82b4e82");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod("GET");
        InputStreamReader in = new InputStreamReader(conn.getInputStream());
        Gson gson = new Gson();
        Lottery objOfYourClass = gson.fromJson(in, Lottery.class);
        System.out.println(objOfYourClass.toString());
        conn.disconnect();
        return objOfYourClass;
    }
}
