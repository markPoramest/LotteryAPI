package com.example.demo.Models;

import lombok.Data;

import java.util.List;

@Data
public class Lotteries {
    private String date;
    private List<Reward> rewards;

    @Override
    public String toString() {
        return "Lotteries{" +
                "date='" + date + '\'' +
                ", rewards=" + rewards +
                '}';
    }
}
