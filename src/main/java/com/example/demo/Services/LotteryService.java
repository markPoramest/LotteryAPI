package com.example.demo.Services;

import com.example.demo.Models.*;
import com.google.gson.Gson;
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

  public Lotteries getLotteries(int i) {
    int day = 0;
    int j = i / 2;
    int month = 0;
    if (i % 2 != 0) {
      day = 1;
      month = (j + 1) % 12;
    } else {
      day = 16;
      month = (j) % 12;
    }
    if (month == 0) {
      month = 12;
    }
    int year = 3000 + ((j / 12) + 1);
    Lotteries lotteries = new Lotteries();
    lotteries.setDate(day + "/" + month + "/" + year);
    List<Reward> rewards = new ArrayList<>();
    rewards.add(getPrize("รางวัลที่ 1", 6_000_000, 1, 6));
    rewards.add(getPrize("รางวัลที่ 2", 200_000, 5, 6));
    rewards.add(getPrize("รางวัลที่ 3", 80_000, 10, 6));
    rewards.add(getPrize("รางวัลที่ 4", 40_000, 50, 6));
    rewards.add(getPrize("รางวัลที่ 5", 20_000, 100, 6));
    rewards.add(sideFirstPrize(rewards.get(0)));
    rewards.add(getPrize("รางวัลเลขหน้า 3 ตัว", 4_000, 2, 3));
    rewards.add(getPrize("รางวัลเลขท้าย 3 ตัว", 4_000, 2, 3));
    rewards.add(getPrize("รางวัลเลขท้าย 2 ตัว", 2_000, 1, 2));
    lotteries.setRewards(rewards);
    return lotteries;
  }

  public Person generatePerson() {
    Person person = new Person();
    person.setNumber(randomGenerateNumber(960, 6));
    return person;
  }

  public PersonAndLottery generatePersonAndLottery() {
    PersonAndLottery personAndLottery = new PersonAndLottery();
    personAndLottery.setPerson(generatePerson());
    List<Lotteries> lotteries = new ArrayList<>();
    for (int i = 0; i < 960; i++) {
      lotteries.add(getLotteries(i + 1));
    }
    personAndLottery.setLotteries(lotteries);
    int amount = 0;
    String won = "";
    List<String> win = new ArrayList<>();
    for (int i = 0; i < 960; i++) {
      String number = personAndLottery.getPerson().getNumber().get(i);
      Lotteries lotteries1 = personAndLottery.getLotteries().get(i);
      amount += Integer.parseInt(checkAllLottery(number, lotteries1)[0].toString());
      won=(checkAllLottery(number, lotteries1)[1].toString());
      if(!won.isEmpty()){
        win.add(won);
      }
    }
    personAndLottery.getPerson().setMoney(amount);
    personAndLottery.getPerson().setWon(win);
    return personAndLottery;
  }

  private Object[] checkAllLottery(String number, Lotteries lotteries) {
    Object[] objects = new Object[2];
    int amount = 0;
    int money = 0;
    String won = "";
    money = checkLottery(number, lotteries.getRewards().get(0), 6_000_000);
    won += won(money, number, 0, lotteries.getDate());
    amount += money;
    money= checkLottery(number, lotteries.getRewards().get(1), 200_000);
    won += won(money, number, 1, lotteries.getDate());
    amount += money;
    money= checkLottery(number, lotteries.getRewards().get(2), 80_000);
    won += won(money, number, 2, lotteries.getDate());
    amount += money;
    money= checkLottery(number, lotteries.getRewards().get(3), 40_000);
    won += won(money, number, 3, lotteries.getDate());
    amount += money;
    money= checkLottery(number, lotteries.getRewards().get(4), 20_000);
    won += won(money, number, 4, lotteries.getDate());
    amount += money;
    money= checkLottery(number, lotteries.getRewards().get(5), 100_000);
    won += won(money, number, 5, lotteries.getDate());
    amount += money;
    money= checkLotteryPreffix(number, lotteries.getRewards().get(6), 4_000);
    won += won(money, number, 6, lotteries.getDate());
    amount += money;
    money= checkLotterySuffix(number, lotteries.getRewards().get(7), 4_000);
    won += won(money, number, 7, lotteries.getDate());
    amount += money;
    money= checkLotterySuffix(number, lotteries.getRewards().get(8), 2_000);
    won += won(money, number, 8, lotteries.getDate());
    amount += money;
    objects[0]  = amount;
    objects[1] = won;
    return objects;
  }

  private String won(int amount, String number, int prize, String date) {
    String won = "";
    if (amount != 0) {
      if (prize == 0) {
        won += "ถูกรางวัลที่ 1 หมายเลขที่ถูก " + number ;
      } else if (prize == 1) {
        won += "ถูกรางวัลที่ 2  หมายเลขที่ถูก " + number ;
      } else if (prize == 2) {
        won += "ถูกรางวัลที่ 3  หมายเลขที่ถูก " + number ;
      } else if (prize == 3) {
        won += "ถูกรางวัลที่ 4  หมายเลขที่ถูก " + number ;
      } else if (prize == 4) {
        won += "ถูกรางวัลที่ 5 หมายเลขที่ถูก " +  number ;
      } else if (prize == 5) {
        won += "ถูกรางวัลข้างเคียงรางวัลที่ 1 หมายเลขที่ถูก " + number ;
      } else if (prize == 6) {
        won += "ถูกรางวัลเลขหน้า 3 ตัว  หมายเลขที่ถูก " + number ;
      } else if (prize == 7) {
        won += "ถูกรางวัลเลขท้าย 3 ตัว หมายเลขที่ถูก " + number;
      } else if (prize == 8) {
        won += "ถูกรางวัลเลขท้าย 2 ตัว หมายเลขที่ถูก " + number ;
      }
      won+= " งวดวันที่ "+date;
    }
    return won;
  }

  private int checkLottery(String number, Reward reward, int prize) {
    int amount = 0;
    for (String num : reward.getNumbers()) {
      if (num.equals(number)) {
        amount += prize;
      }
    }
    return amount;
  }

  private int checkLotteryPreffix(String number, Reward reward, int prize) {
    int amount = 0;
    for (String num : reward.getNumbers()) {
      if (number.substring(0, 3).equals(num)) {
        amount += prize;
      }
    }
    return amount;
  }

  private int checkLotterySuffix(String number, Reward reward, int prize) {
    int amount = 0;
    for (String num : reward.getNumbers()) {
      if (prize == 4_000) {
        if (number.substring(3, 6).equals(num)) {
          amount += prize;
        }
      } else {
        if (number.substring(4, 6).equals(num)) {
          amount += prize;
        }
      }
    }
    return amount;
  }

  private Reward sideFirstPrize(Reward firstPrize) {
    Reward sideFirstPrize = new Reward();
    sideFirstPrize.setName("รางวัลข้างเคียงรางวัลที่ 1");
    sideFirstPrize.setReward(100_000);
    String number = firstPrize.getNumbers().get(0);
    int numberInt = Integer.parseInt(number);
    String side1 = String.valueOf(numberInt - 1);
    String side2 = String.valueOf(numberInt + 1);
    if (side1.length() != 6) {
      side1 = ("000000" + side1).substring(side1.length());
      side2 = ("000000" + side2).substring(side2.length());
    }
    List<String> reward = new ArrayList<>();
    reward.add(side1);
    reward.add(side2);
    sideFirstPrize.setNumbers(reward);
    return sideFirstPrize;
  }

  private Reward getPrize(String name, int reward, int round, int digit) {
    Reward firstPrice = new Reward();
    firstPrice.setName(name);
    firstPrice.setReward(reward);
    firstPrice.setNumbers(randomGenerateNumber(round, digit));
    return firstPrice;
  }

  private List<String> randomGenerateNumber(int round, int digit) {
    List<String> allPrize = new ArrayList<>();
    for (int j = 0; j < round; j++) {
      Random random = new Random();
      String number = "";
      for (int i = 0; i < digit; i++) {
        int digits = random.nextInt(10);
        number += String.valueOf(digits);
      }
      allPrize.add(number);
    }
    return allPrize;
  }
}
