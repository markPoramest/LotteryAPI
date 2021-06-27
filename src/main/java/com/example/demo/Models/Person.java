package com.example.demo.Models;

import lombok.Data;

import java.util.List;

@Data
public class Person {
    private String name;
    private int pay;
    private int money;
    private List<String> number;
    private List<String> won;
    public Person() {
        this.name = "ปรีชา ใคร่หวย";
        this.pay = 96000;
    }

}
