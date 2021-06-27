package com.example.demo.Models;

import lombok.Data;

import java.util.List;

@Data
public class PersonAndLottery {
    Person person;
    List<Lotteries> lotteries;
}
