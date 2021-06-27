package com.example.demo.Services;

import com.example.demo.Models.Lotteries;
import com.example.demo.Models.Lottery;
import com.example.demo.Models.Reward;
import com.google.gson.Gson;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

  public Lotteries getLotteries() {
    int day = 1;
    int month = 1;
    int year = 3000;
    Lotteries lotteries = new Lotteries();
    lotteries.setDate(day +"/"+ month +"/"+ year);
    List<Reward> rewards = new ArrayList<>();
    rewards.add(getPrize("รางวัลที่ 1",6_000_000,1, 6));
    rewards.add(getPrize("รางวัลที่ 2",200_000,5, 6));
    rewards.add(getPrize("รางวัลที่ 3",80_000,10, 6));
    rewards.add(getPrize("รางวัลที่ 4",40_000,50, 6));
    rewards.add(getPrize("รางวัลที่ 5",20_000,100, 6));
    rewards.add(sideFirstPrize(rewards.get(0)));
    rewards.add(getPrize("รางวัลเลขหน้า 3 ตัว",4_000,2, 3));
    rewards.add(getPrize("รางวัลเลขท้าย 3 ตัว",4_000,2, 3));
    rewards.add(getPrize("รางวัลเลขท้าย 2 ตัว",2_000,1, 2));
    lotteries.setRewards(rewards);
    return lotteries;
  }

  private Reward sideFirstPrize(Reward firstPrize) {
    Reward sideFirstPrize = new Reward();
    sideFirstPrize.setName("รางวัลข้างเคียงรางวัลที่ 1");
    sideFirstPrize.setReward(100_000);
    String number = firstPrize.getNumbers().get(0);
    int numberInt  = Integer.parseInt(number);
    String side1 = String.valueOf(numberInt-1);
    String side2 = String.valueOf(numberInt+1);
    if(side1.length()!=6){
      side1 =  ("000000" + side1).substring(side1.length());
      side2 =  ("000000" + side2).substring(side2.length());
    }
    List<String> reward = new ArrayList<>();
    reward.add(side1);
    reward.add(side2);
    sideFirstPrize.setNumbers(reward);
    return  sideFirstPrize;
  }

  private Reward getPrize(String name, int reward, int round , int digit) {
    Reward firstPrice = new Reward();
    firstPrice.setName(name);
    firstPrice.setReward(reward);
    firstPrice.setNumbers(randomGenerateNumber(round, digit));
    return  firstPrice;
  }

  private List<String> randomGenerateNumber(int round, int digit) {
    List<String> allPrize = new ArrayList<>();
    for (int j = 0; j < round; j++) {
      Random random = new Random();
      String number = "";
      for (int i = 0; i <digit; i++) {
        int digits = random.nextInt(10);
        number += String.valueOf(digits);
      }
      allPrize.add(number);
    }
    return allPrize;
  }
}
