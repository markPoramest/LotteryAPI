package com.example.demo.Models;

import lombok.Data;

import java.util.List;

@Data
public class Reward {
    private String name;
    private int reward;
    private List<String> numbers;

    @Override
    public String toString() {
        return "Lottery{" +
                "name='" + name + '\'' +
                ", reward=" + reward +
                ", numbers=" + numbers +
                '}';
    }
}
