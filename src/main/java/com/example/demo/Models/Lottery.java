package com.example.demo.Models;

import lombok.Data;

import java.util.Arrays;

@Data
public class Lottery {
 private String code;
 private String drawdate;
 private Object[] result;

    @Override
    public String toString() {
        return "Lottery{" +
                "code='" + code + '\'' +
                ", drawdate='" + drawdate + '\'' +
                ", result='" + Arrays.toString(result) + '\'' +
                '}';
    }
}
